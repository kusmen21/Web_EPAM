package com.epam.hostel.service;

import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.bean.User;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public interface RequestService {
	/**
	 * Creates a new request
	 * @param bedIds bed ids associated with this request
	 * @param user user who created
	 * @param dateFrom request start date in format yyyy-mm-dd
	 * @param dateTo request finish date in format yyyy-mm-dd
	 * @return ValidationResult.ALL_RIGHT if the request was created, ValidationResult with a specific cause if not valid,
     * ValidationResult.UNKNOWN_ERROR otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	ValidationResult createRequest(List<Integer> bedIds, User user, String dateFrom, String dateTo) throws ServiceException;
	
	/**
	 * Retrieves a list with not free beds in the specified time period
	 * @param from time period start date in format yyyy-mm-dd
	 * @param to time period finish date in format yyyy-mm-dd
	 * @return a list with not free beds in the specified time period
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	List<Bed> getNotFreeBeds(String from, String to) throws ServiceException;
	
	/**
	 * Retrieves user requests
	 * @param userId user id
	 * @return list with user requests
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	Map<Request, List<Bed>> getUserRequests(int userId) throws ServiceException;
	
	/**
	 * Canсels request - user cancels his own request
	 * @param requestId request id
	 * @return true if request was canceled, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean cancelRequest(int requestId) throws ServiceException;
	
	/**
	 * Retrieves actual requests from map with requests
	 * actual requests - not-read, approved, paid with request start date > today date
	 * @param requests requests to search
	 * @return map with actual requests
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	Map<Request, List<Bed>> getActualRequests(Map<Request, List<Bed>> requests) throws ServiceException;
	
	/**
	 * Deletes request
	 * @param requestId request id
	 * @return true if request was deleted, false otherwise
	 * @throws ServiceException if any exceptions occurred on the DAO or SQL layer
	 */
	boolean deleteRequest(int requestId) throws ServiceException;
}
