package com.epam.hostel.dao;

import java.util.List;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.dao.exception.DAOException;

public interface BedDAO {
	/**
	 * Retrieves not free beds in this time period
	 * @param from request start date in format yyyy-mm-dd
	 * @param to request finish date in format yyyy-mm-dd
	 * @return list with not free beds in this time period
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	List<Bed> getNotFreeBeds(String from, String to) throws DAOException;
	
	/**
	 * Retrieves beds with specific ids
	 * @param bedIds - list with beds ids
	 * @return list with beds
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	List<Bed> getBedsById(List<Integer> bedIds) throws DAOException;
}
