package com.epam.hostel.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.criterion.RequestCriterion;
import com.epam.hostel.dao.exception.DAOException;


public interface RequestDAO {
	/**
	 * Creating a new request
	 * @param request request to create and put into database
	 * @param beds request`s beds
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	void create(Request request, List<Integer> beds) throws DAOException;
	
	/**
	 * Deleting request
	 * @param requestId request id to delete
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	void delete(int requestId) throws DAOException;		
	
	/**
	 * Retrieves requests with the specified values
	 * @param values fields and values as criterion for search
	 * @return list with found requests
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	List<Request> find(Map<RequestCriterion, Object> values) throws DAOException;	
	
	/**
	 * Retrieves all requests
	 * @return list with all requests
	 * @throws DAOException
	 */
	List<Request> list() throws DAOException;
	
	/**
     * Updates the request with the specified values
     * @param requestId request id
     * @param values fields and values for change
     * @throws DAOException if any exceptions occurred on the SQL layer
     */
	void update(Map<RequestCriterion, Object> values, int requestId) throws DAOException;
	
	/**
	 * Set all user`s requests denied
	 * @param userId id of the user
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	void setUserRequestsDenied(int userId) throws DAOException;
	
	/**
	 * Retrieves user requests with associated beds
	 * @param userId if of the user
	 * @return map with user requests with associated beds
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	Map<Request, List<Bed>> getUserRequests(int userId) throws DAOException;
	
	/**
	 * Retrieves requests with associated beds with the starting date today
	 * @param today today date
	 * @return map with user requests with associated beds
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	Map<Request, List<Bed>> getTodayRequests(Date today) throws DAOException;
}
