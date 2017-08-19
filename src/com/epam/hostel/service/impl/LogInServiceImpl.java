package com.epam.hostel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.AdministratorCriterion;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.dao.AdministratorDAO;
import com.epam.hostel.dao.DAOFactory;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.service.LogInService;
import com.epam.hostel.service.encrypt.MD5;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;
import com.epam.hostel.validation.Validator;

public class LogInServiceImpl implements LogInService {
	private static final String REGEXP_EMAIL = ".+@.+\\..*";
	private static final String REGEXP_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
	private static final String REGEXP_PHONE = "^[0-9]{7,20}$";
	private static final String REGEXP_NAME = "^[A-Za-zА-Яа-я]{3,40}$";
	private static final String REGEXP_DATE = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	@Override
	public User getUser(String email, String password) throws ServiceException {
		User user = null;

		if (password != null && !password.isEmpty() && Validator.validate(password, REGEXP_PASSWORD)) {
			if (email != null && !email.isEmpty() && Validator.validate(email, REGEXP_EMAIL)) {
				UserDAO dao = DAOFactory.getUserDAO();
				Map<UserCriterion, Object> query = new HashMap<>();
				String md5password = MD5.encrypt(password);
				query.put(UserCriterion.EMAIL, email);
				query.put(UserCriterion.PASSWORD, md5password);
				try {
					List<User> users = dao.find(query);
					if (users.size() == 1) {
						user = users.get(0);
					}					
				} catch (DAOException e) {
					throw new ServiceException(e);
				}
			}
		}
		return user;
	}

	@Override
	public Administrator getAdministrator(String password) throws ServiceException {
		Administrator administrator = null;

		if (password != null && !password.isEmpty() && Validator.validate(password, REGEXP_PASSWORD)) {
			AdministratorDAO dao = DAOFactory.getAdministratorDAO();
			Map<AdministratorCriterion, Object> query = new HashMap<>();
			String md5password = MD5.encrypt(password);
			query.put(AdministratorCriterion.PASSWORD, md5password);
			try {
				List<Administrator> administrators = dao.find(query);
				if (administrators.size() == 1) {
					administrator = administrators.get(0);
				}
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return administrator;
	}

	@Override
	public ValidationResult register(String email, String password, String phone, String fname, String lname,
			String stringDate) throws ServiceException {
		ValidationResult result;

		if (email == null || email.isEmpty() || password == null || password.isEmpty() || phone == null
				|| phone.isEmpty() || fname == null || fname.isEmpty() || lname == null || lname.isEmpty()) {
			result = ValidationResult.NOT_ALL_FIELDS;
		} else if (!Validator.validate(email, REGEXP_EMAIL)) {
			result = ValidationResult.EMAIL_INCORRECT;
		} else if (!Validator.validate(password, REGEXP_PASSWORD)) {
			result = ValidationResult.PASS_INCORRECT;
		} else if (!Validator.validate(String.valueOf(phone), REGEXP_PHONE)) {
			result = ValidationResult.PHONE_INCORRECT;
		} else if (!Validator.validate(fname, REGEXP_NAME)) {
			result = ValidationResult.FNAME_INCORRECT;
		} else if (!Validator.validate(lname, REGEXP_NAME)) {
			result = ValidationResult.LNAME_INCORRECT;
		} else if (!stringDate.isEmpty() && !Validator.validate(stringDate, REGEXP_DATE)) {
			result = ValidationResult.DATE_INCORRECT;
		} else {
			try {
				UserDAO dao = DAOFactory.getUserDAO();
				if (dao.isEmailFree(email)) {
					if (dao.isPhoneFree(String.valueOf(phone))) {

						User user = new User();
						user.setEmail(email);
						user.setPhone(phone);
						String md5password = MD5.encrypt(password);
						user.setPassword(md5password);
						user.setFirstName(fname);
						user.setLastName(lname);
						user.setBanned(false);
						user.setDiscount(0);
						user.setRegistrationDate(new Date());
						if (stringDate != null && !stringDate.isEmpty()) {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
							Date birthDate = simpleDateFormat.parse(stringDate);
							user.setBirthDate(birthDate);
						}

						boolean registered = DAOFactory.getUserDAO().create(user);
						result = registered ? ValidationResult.ALL_RIGHT : ValidationResult.UNKNOWN_ERROR;
					} else {
						result = ValidationResult.PHONE_NOT_UNIQUE;
					}
				} else {
					result = ValidationResult.EMAIL_NOT_UNIQUE;
				}
			} catch (DAOException | ParseException e) {
				throw new ServiceException(e);
			}
		}
		return result;
	}

}
