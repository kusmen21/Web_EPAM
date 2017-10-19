package com.epam.hostel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.criterion.BedCriterion;
import com.epam.hostel.dao.BedDAO;
import com.epam.hostel.dao.DAOUtil;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.pool.ConnectionPool;

public class BedDAOImpl implements BedDAO{
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	private static final String SQL_FIND_FREE_BEDS = "SELECT b.id, b.price, r.id FROM beds b JOIN beds_has_requests br ON b.id = br.beds_id JOIN requests r ON r.id = br.requests_id AND (!(STR_TO_DATE(?, '%Y-%m-%d') < date_from) AND !(STR_TO_DATE(?, '%Y-%m-%d') > date_to)) AND (r.status_id = 0 OR r.status_id = 1 OR r.status_id = 3);";	
	private static final String SQL_FIND = "SELECT id, price FROM beds WHERE ";
	private static final String SQL_OR = " OR ";
	private static final String SQL_QUESTION = " = ?";
	private static final String EXCEPTION_SQL = "SQLException in BedDAO";
	
	private static Bed map(ResultSet resultSet) throws SQLException{
		Bed bed = new Bed();
		bed.setId(resultSet.getInt(1));
		bed.setPrice(resultSet.getInt(2));
		return bed;
	}
	
	@Override
	public List<Bed> getNotFreeBeds(String from, String to) throws DAOException {
		List<Bed> freeBeds = new ArrayList<>();
		Object[] values = {to, from};
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_FIND_FREE_BEDS, false, values);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				freeBeds.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return freeBeds;
	}

	@Override
	public List<Bed> getBedsById(List<Integer> bedIds) throws DAOException {
		StringBuilder searchString = new StringBuilder(SQL_FIND);

		boolean isFirstCriterion = true;
		for (int bedId : bedIds) {
			if (isFirstCriterion) {
				isFirstCriterion = false;
			} else {
				searchString.append(SQL_OR);
			}
			searchString.append(BedCriterion.ID.getSQLFieldName()).append(SQL_QUESTION);
		}

		List<Bed> beds = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, searchString.toString(), false, bedIds.toArray());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Bed bed = map(resultSet);
				beds.add(bed);
			}
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return beds;
	}
	
}
