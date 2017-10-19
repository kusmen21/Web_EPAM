package com.epam.hostel.service;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.bean.User;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public interface LogInService {
	/**
	 * Retrieves user to login
	 * @param email user email
	 * @param password user password
	 * @return found user, null if user was not found
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	User getUser(String email, String password) throws ServiceException;
	
	/**
	 * Retrieves administrator to login
	 * @param email administrator email
	 * @param password administrator password
	 * @return found administrator, null if administrator was not found
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	Administrator getAdministrator(String password) throws ServiceException;
	
	/**
	 * Registers new user
	 * @param email user email
	 * @param password user password
	 * @param phone user phone
	 * @param fname user first name
	 * @param lname user last name
	 * @param stringDate birth date in format yyyy-mm-dd
	 * @return ValidationResult.ALL_RIGHT if the user was created, ValidationResult with a specific cause if not valid,
     * ValidationResult.UNKNOWN_ERROR otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	ValidationResult register(String email, String password, String phone, String fname, String lname, String stringDate) throws ServiceException;	
}
