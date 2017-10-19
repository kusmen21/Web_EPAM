package com.epam.hostel.dao;

import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.dao.exception.DAOException;

public interface UserDAO 
{
	/**
	 * Creating new user
	 * @param user user to create and put into database
	 * @return true if the user was created, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	boolean create(User user) throws DAOException;	
	
	/**
	 * Updates the user with the specified values
	 * @param userId user id
	 * @param values fields and values for change
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	void update(int userId, Map<UserCriterion, Object> values) throws DAOException;
	
	/**
	 * Retrieves users with the specified values
	 * @param values fields and values as criterion for search
	 * @return list with found users
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	List<User> find(Map<UserCriterion, Object> values) throws DAOException;	
	
	/**
	 * Retrieves all users
	 * @return list with all users
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	List<User> list() throws DAOException;
	
	/**
	 * Check if the email is free
	 * @param email email to search
	 * @return true if email is free, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	boolean isEmailFree(String email) throws DAOException;
	
	/**
	 * Check if the phone is free
	 * @param phone phone to search
	 * @return true if phone is free, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	boolean isPhoneFree(String phone) throws DAOException;
	
	/**
	 * Deletes user only if he does not have any requests
	 * @param userId user id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	void delete(int userId) throws DAOException;
}
