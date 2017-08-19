package com.epam.hostel.dao;

import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.criterion.AdministratorCriterion;
import com.epam.hostel.dao.exception.DAOException;


public interface AdministratorDAO {
	/**
	 * Creates a new administrator
	 * @param administrator create and put administrator into database
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
    void create(Administrator administrator) throws DAOException;
	
    /**
     * Updates the administrator with the specified values
     * @param administrator update properties for this administrator
     * @param values fields and values for change
     * @throws DAOException if any exceptions occurred on the SQL layer
     */
	void update(Administrator administrator, Map<AdministratorCriterion, Object> values) throws DAOException;
	
	/**
	 * Retrieves an administrator with the specified values
	 * @param values fields and values as criterion for search
	 * @return list with found administrators
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	List<Administrator> find(Map<AdministratorCriterion, Object> values) throws DAOException;	
	
	/**
	 * Retrieves all administrators
	 * @return list with all administrators
	 * @throws DAOException
	 */
	List<Administrator> list() throws DAOException;
}
