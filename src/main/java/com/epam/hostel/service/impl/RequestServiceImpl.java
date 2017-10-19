package com.epam.hostel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.bean.Status;
import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.RequestCriterion;
import com.epam.hostel.dao.BedDAO;
import com.epam.hostel.dao.DAOFactory;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.service.RequestService;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;
import com.epam.hostel.validation.Validator;

public class RequestServiceImpl implements RequestService {
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String REGEXP_DATE = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
	private static final int MAIN_ADMINISTRATOR_ID = 0;

	@Override
	public ValidationResult createRequest(List<Integer> bedIds, User user, String dateFrom, String dateTo)
			throws ServiceException {
		Date to = null;
		Date from = null;
		long price = 0;

		// check user
		if (user == null) {
			return ValidationResult.NO_USER;
		}
		// check dates
		if (dateFrom == null || dateFrom.isEmpty() || dateTo == null || dateTo.isEmpty()) {
			return ValidationResult.NO_DATES;
		}
		// check beds availability
		if (bedIds == null || bedIds.isEmpty()) {
			return ValidationResult.NO_BEDS;
		}
		// check date format
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		try {
			to = simpleDateFormat.parse(dateTo);
			from = simpleDateFormat.parse(dateFrom);
		} catch (ParseException e) {
			return ValidationResult.DATE_INCORRECT;
		}
		// check free beds
		boolean isFreeBeds = true;
		List<Bed> notFreeBeds = getNotFreeBeds(dateFrom, dateTo);
		for (Bed bed : notFreeBeds) {
			if (bedIds.contains(bed.getId())) {
				isFreeBeds = false;
				break;
			}
		}
		if (!isFreeBeds) {
			return ValidationResult.BEDS_NOT_FREE;
		}
		// Getting request price
		BedDAO bedDAO = DAOFactory.getBedDAO();
		List<Bed> beds = null;
		try {
			beds = bedDAO.getBedsById(bedIds);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		for (Bed bed : beds) {
			price += bed.getPrice();
		}
		long difference = to.getTime() - from.getTime();
	    long totalDays = (TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)) + 1;
		price = price * totalDays;
		// Discount
		long totalDiscount = (price * user.getDiscount()) / 100;
		price = price - totalDiscount;
		// Creating request
		Request request = new Request();
		request.setDateFrom(from);
		request.setDateTo(to);
		request.setSeatCount(bedIds.size());
		request.setUserId(user.getId());
		request.setRegistrationDate(new Date());
		request.setAdministratorId(MAIN_ADMINISTRATOR_ID);
		request.setStatusId(Status.UNREAD.ordinal());
		request.setPrice(price);
		RequestDAO requestDAO = DAOFactory.getRequestDAO();
		try {
			requestDAO.create(request, bedIds);
			return ValidationResult.ALL_RIGHT;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Bed> getNotFreeBeds(String from, String to) throws ServiceException {
		if (from == null || from.isEmpty() || to == null || to.isEmpty()){
			return null;
		}
		if (!Validator.validate(from, REGEXP_DATE) || (!Validator.validate(to, REGEXP_DATE))){
			return null;
		}
		List<Bed> notFreeBeds = null;
		BedDAO bedDAO = DAOFactory.getBedDAO();

		if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
			try {
				notFreeBeds = bedDAO.getNotFreeBeds(from, to);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}

		return notFreeBeds;
	}

	@Override
	public Map<Request, List<Bed>> getUserRequests(int userId) throws ServiceException {
		if (userId == 0) {
			return null;
		}

		RequestDAO requestDAO = DAOFactory.getRequestDAO();
		Map<Request, List<Bed>> requests = null;

		try {
			requests = requestDAO.getUserRequests(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return requests;
	}

	@Override
	public boolean cancelRequest(int requestId) throws ServiceException {
		if (requestId == 0) {
			return false;
		}

		RequestDAO dao = DAOFactory.getRequestDAO();
		try {
			Map<RequestCriterion, Object> values = new HashMap<>();
			values.put(RequestCriterion.STATUS_ID, Status.CANCELED.ordinal());
			values.put(RequestCriterion.LAST_MODIFIED_DATE, new Date());
			dao.update(values, requestId);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<Request, List<Bed>> getActualRequests(Map<Request, List<Bed>> requests) throws ServiceException {
		Map<Request, List<Bed>> actualRequests = new HashMap<>();
		if (requests == null || requests.isEmpty()){
			return actualRequests;
		}
		
		try {
			for (Map.Entry<Request, List<Bed>> entry : requests.entrySet()) {
				int statusId = entry.getKey().getStatusId();
				Date dateTo = entry.getKey().getDateTo();
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
				Date today = new Date();
				Date todayWithZeroTime = formatter.parse(formatter.format(today));
				// if status == approved or status == unread and request time has not expired
				if ((statusId == Status.APPROVED.ordinal() || statusId == Status.UNREAD.ordinal() || statusId == Status.PAID.ordinal())
						&& !(todayWithZeroTime.getTime() > dateTo.getTime())) {
					actualRequests.put(entry.getKey(), entry.getValue());
				}
			}
		} catch (ParseException e) {
			throw new ServiceException(e);
		}
		return actualRequests;
	}

	@Override
	public boolean deleteRequest(int requestId) throws ServiceException {
		if (requestId == 0) {
			return false;
		}

		RequestDAO dao = DAOFactory.getRequestDAO();
		try {			
			dao.delete(requestId);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
	}
}
