package com.epam.hostel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.bean.Status;
import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.RequestCriterion;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.dao.DAOFactory;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.encrypt.MD5;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;
import com.epam.hostel.validation.Validator;

public class AdminServiceImpl implements AdminService {
	private static final String REGEXP_EMAIL = ".+@.+\\..*";
	private static final String REGEXP_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
	private static final String REGEXP_PHONE = "^[0-9]{7,20}$";
	private static final String REGEXP_NAME = "^[A-Za-zА-Яа-я]{3,}$";
	private static final String REGEXP_DATE = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
	private static final String REGEXP_DISCOUNT = "^[1-9][0-9]?$|^100$";
	private static final String DATE_PATTERN = "dd/MM/yyyy";

	@Override
	public List<User> getAllUsers() throws ServiceException {
		List<User> users = new ArrayList<>();
		UserDAO dao = DAOFactory.getUserDAO();
		try {
			users = dao.list();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return users;
	}

	@Override
	public User findUser(UserCriterion criterion, String value) throws ServiceException {
		User user = null;

		// verification
		if (value != null && !value.isEmpty() && criterion != null) {
			Map<UserCriterion, Object> criterions = new HashMap<>();
			criterions.put(criterion, value);
			UserDAO dao = DAOFactory.getUserDAO();
			try {
				List<User> users = dao.find(criterions);
				if (!users.isEmpty()) {
					user = users.get(0);
				}
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return user;
	}

	@Override
	public boolean banUser(int userId) throws ServiceException {		
		if (userId == 0) {
			return false;
		}

		Map<UserCriterion, Object> criterions = new HashMap<>();
		criterions.put(UserCriterion.IS_BANNED, 1);
		UserDAO dao = DAOFactory.getUserDAO();
		RequestDAO requestDAO = DAOFactory.getRequestDAO();
		try {
			dao.update(userId, criterions);
			requestDAO.setUserRequestsDenied(userId);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ValidationResult updateUser(String userId, String email, String password, String phone, String fname,
			String lname, String date, String discount) throws ServiceException {
		Map<UserCriterion, Object> values = new HashMap<>();

		if (userId == null || userId.isEmpty()) {
			return ValidationResult.NO_ID;
		}
		boolean hasError = false;
		if (email != null && !email.isEmpty()) {
			if (Validator.validate(email, REGEXP_EMAIL)) {
				values.put(UserCriterion.EMAIL, email);
			} else {
				hasError = true;
			}
		}
		if (password != null && !password.isEmpty()) {
			if (Validator.validate(password, REGEXP_PASSWORD)) {
				String md5password = MD5.encrypt(password);
				values.put(UserCriterion.PASSWORD, md5password);
			} else {
				hasError = true;
			}
		}
		if (phone != null && !phone.isEmpty()) {
			if (Validator.validate(phone, REGEXP_PHONE)) {
				values.put(UserCriterion.PHONE, phone);
			} else {
				hasError = true;
			}
		}
		if (fname != null && !fname.isEmpty()) {
			if (Validator.validate(fname, REGEXP_NAME)) {
				values.put(UserCriterion.FIRST_NAME, fname);
			} else {
				hasError = true;
			}
		}
		if (lname != null && !lname.isEmpty()) {
			if (Validator.validate(lname, REGEXP_NAME)) {
				values.put(UserCriterion.LAST_NAME, lname);
			} else {
				hasError = true;
			}
		}
		if (date != null && !date.isEmpty()) {
			if (Validator.validate(date, REGEXP_DATE)) {
				values.put(UserCriterion.BIRTH_DATE, date);
			} else {
				hasError = true;
			}
		}
		if (discount != null && !discount.isEmpty()) {
			if (Validator.validate(discount, REGEXP_DISCOUNT)) {
				values.put(UserCriterion.DISCOUNT, discount);
			} else {
				hasError = true;
			}
		}
		if (hasError) {
			return ValidationResult.DATA_INCORRECT;
		}
		if (values.isEmpty()) {
			return ValidationResult.NO_FIELDS;
		}

		UserDAO dao = DAOFactory.getUserDAO();
		try {
			int id = Integer.parseInt(userId);
			dao.update(id, values);
			return ValidationResult.ALL_RIGHT;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Request> getNotApprovedRequests() throws ServiceException {
		RequestDAO requestDAO = DAOFactory.getRequestDAO();
		List<Request> requests = null;
		try {
			Map<RequestCriterion, Object> values = new HashMap<>();
			values.put(RequestCriterion.STATUS_ID, Status.UNREAD.ordinal());
			requests = requestDAO.find(values);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return requests;
	}

	@Override
	public boolean approveRequest(int requestId, int adminId) throws ServiceException {		
		if (requestId == 0) {
			return false;
		}

		RequestDAO dao = DAOFactory.getRequestDAO();
		try {
			Map<RequestCriterion, Object> values = new HashMap<>();
			values.put(RequestCriterion.STATUS_ID, Status.APPROVED.ordinal());
			values.put(RequestCriterion.LAST_MODIFIED_DATE, new Date());
			values.put(RequestCriterion.ADMINISTRATOR_ID, adminId);
			dao.update(values, requestId);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean denyRequest(int requestId, int adminId) throws ServiceException {		
		if (requestId == 0) {
			return false;
		}

		RequestDAO dao = DAOFactory.getRequestDAO();
		try {
			Map<RequestCriterion, Object> values = new HashMap<>();
			values.put(RequestCriterion.STATUS_ID, Status.DENIED.ordinal());
			values.put(RequestCriterion.LAST_MODIFIED_DATE, new Date());
			values.put(RequestCriterion.ADMINISTRATOR_ID, adminId);
			dao.update(values, requestId);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean setPaidRequest(int requestId, int adminId) throws ServiceException {		
		if (requestId == 0) {
			return false;
		}

		RequestDAO dao = DAOFactory.getRequestDAO();
		try {
			Map<RequestCriterion, Object> values = new HashMap<>();
			values.put(RequestCriterion.STATUS_ID, Status.PAID.ordinal());
			values.put(RequestCriterion.LAST_MODIFIED_DATE, new Date());
			values.put(RequestCriterion.ADMINISTRATOR_ID, adminId);
			dao.update(values, requestId);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<Request, List<Bed>> getTodayRequests() throws ServiceException {
		RequestDAO requestDAO = DAOFactory.getRequestDAO();
		Map<Request, List<Bed>> requests = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
			Date today = new Date();
			Date todayWithZeroTime = formatter.parse(formatter.format(today));
			requests = requestDAO.getTodayRequests(todayWithZeroTime);
		} catch (DAOException | ParseException e) {
			throw new ServiceException(e);
		}
		
		return requests;
	}
	
	@Override
	public Map<Request, List<Bed>> getUserRequests(int userId) throws ServiceException {
		if(userId == 0){
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
	public boolean deleteUser(int userId) throws ServiceException {
		if(userId == 0){
			return false;
		}		
		UserDAO dao = DAOFactory.getUserDAO();
		RequestDAO requestDAO = DAOFactory.getRequestDAO();
		
		try {
			Map<RequestCriterion, Object> values = new HashMap<>();
			values.put(RequestCriterion.USER_ID, userId);
			List<Request> requests = requestDAO.find(values);
			if (!requests.isEmpty()){
				return false;
			} else {			
				dao.delete(userId);
			}
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
