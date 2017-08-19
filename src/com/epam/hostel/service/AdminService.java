package com.epam.hostel.service;

import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public interface AdminService 
{
	/**
	 * Retrieves all users
	 * @return list with all users
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	List<User> getAllUsers() throws ServiceException;
	
	/**
	 * Retrieves the user with the specified value
	 * @param criterion criterion to search
	 * @param value value of the search criterion
	 * @return found user, null if user was not found
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	User findUser(UserCriterion criterion, String value) throws ServiceException;
	
	/**
	 * Bans the user with a specific id
	 * Sets all user requests to Denied
	 * @param userId user id
	 * @return true if user was banned, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean banUser(int userId) throws ServiceException;
	
	/**
	 * Updates the user with the specified values
	 * @param userId user id to update
	 * @param email user email
	 * @param password user password
	 * @param phone user phone
	 * @param fname user first name
	 * @param lname user last name
	 * @param date user birth date
	 * @return ValidationResult.ALL_RIGHT if the user was updated, ValidationResult with a specific cause if not valid,
     * ValidationResult.UNKNOWN_ERROR otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	ValidationResult updateUser(String userId, String email, String password, String phone, String fname, String lname, String date, String discount) throws ServiceException;
	
	/**
	 * Retrieves not approved requests
	 * @return list not approved requests
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	List<Request> getNotApprovedRequests() throws ServiceException;
	
	/**
	 * Set request to approved - user can come to the hostel and pay
	 * @param requestId request id
	 * @param adminId id of the admin who approved
	 * @return true if request was approved, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean approveRequest(int requestId, int adminId) throws ServiceException;
	
	/**
	 * Set request to denied - administrator denied request
	 * @param requestId request id
	 * @param adminId id of the admin who denied
	 * @return true if request was denied, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean denyRequest(int requestId, int adminId) throws ServiceException;
	
	/**
	 * Set request to denied - user paid request
	 * @param requestId request id
	 * @param adminId id of the admin who set paid
	 * @return true if request was set paid, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean setPaidRequest(int requestId, int adminId) throws ServiceException;
	
	/**
	 * Retrieves today requests
	 * @return list with today requests
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	Map<Request, List<Bed>> getTodayRequests() throws ServiceException;
		
	/**
	 * Retrieves user requests with associated beds
	 * @param userId user id
	 * @return map with user requests with associated beds
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	Map<Request, List<Bed>> getUserRequests(int userId) throws ServiceException;
	
	/**
	 * Deletes user only if he does not have any requests
	 * @param userId user id
	 * @return true if user was banned, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean deleteUser(int userId) throws ServiceException;
}
