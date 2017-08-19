package com.epam.hostel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.criterion.AdministratorCriterion;
import com.epam.hostel.dao.AdministratorDAO;
import com.epam.hostel.dao.DAOUtil;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.pool.ConnectionPool;


public class AdministratorDAOImpl implements AdministratorDAO
{
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	private static final String SQL_FIND = "SELECT id, phone, password, first_name, last_name, birth_date FROM administrators WHERE " ;	
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, phone, password, first_name, last_name, birth_date FROM administrators ORDER BY id";
	private static final String SQL_CREATE = "INSERT INTO administrators (phone, password, first_name, last_name, birth_date) VALUES (?, ?, ?, ?, ?)";	
	private static final String SQL_DELETE = "DELETE FROM administrators WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE administrators SET ";
	private static final String SQL_AND = " AND ";
	private static final String SQL_QUESTION = " = ?";
	private static final String SQL_WHERE_ID = " WHERE id";
	private static final String SQL_COMMA = " , ";
	
	
	private static Administrator map(ResultSet resultSet) throws SQLException {
		Administrator administrator = new Administrator();
		administrator.setId(resultSet.getInt(AdministratorCriterion.ID.getSQLFieldName()));		
		administrator.setPhone(resultSet.getString(AdministratorCriterion.PHONE.getSQLFieldName()));
		administrator.setPassword(resultSet.getString(AdministratorCriterion.PASSWORD.getSQLFieldName()));
		administrator.setFirstName(resultSet.getString(AdministratorCriterion.FIRST_NAME.getSQLFieldName()));
		administrator.setLastName(resultSet.getString(AdministratorCriterion.LAST_NAME.getSQLFieldName()));	
		administrator.setBirthDate(resultSet.getDate(AdministratorCriterion.BIRTH_DATE.getSQLFieldName()));
		return administrator;
	}
	
	@Override
	public List<Administrator> find(Map<AdministratorCriterion, Object> values) throws DAOException {
		StringBuilder searchString = new StringBuilder(SQL_FIND);
		
		boolean isFirstCriterion = true;
		for (Map.Entry<AdministratorCriterion, Object> criterions : values.entrySet()) {
			if (isFirstCriterion) {
				isFirstCriterion = false;
			} else {
				searchString.append(SQL_AND);
			}
			searchString.append(criterions.getKey().getSQLFieldName()).append(SQL_QUESTION);
		}

		List<Administrator> administrators = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, searchString.toString(), false, values.values().toArray());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Administrator administrator = map(resultSet);
				administrators.add(administrator);
			}
		} catch (SQLException e) {
			throw new DAOException("SQLException in AdministratorDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return administrators;
	}

	@Override	
	public void create(Administrator administrator) throws DAOException {
		Object[] values = { administrator.getPhone(), administrator.getPassword(), administrator.getFirstName(),
				administrator.getLastName(), DAOUtil.toSqlDate(administrator.getBirthDate()) };

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_CREATE, true, values);
			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DAOException("Creating administrator failed, no rows affected.");
			}

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				administrator.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Creating administrator failed, no generated key obtained.");
			}

		} catch (SQLException e) {
			throw new DAOException("SQLException in AdministratorDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
	}	

	@Override	
	public List<Administrator> list() throws DAOException {
		List<Administrator> administrators = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_LIST_ORDER_BY_ID, false);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				administrators.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException("SQLException in AdministratorDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return administrators;
	}
	
	@Override
	public void update(Administrator administrator, Map<AdministratorCriterion, Object> values) throws DAOException {
		StringBuilder updateString = new StringBuilder(SQL_UPDATE);
		
		boolean isFirstField = true;
		for (Map.Entry<AdministratorCriterion, Object> criterions : values.entrySet()) {
			if (isFirstField) {
				isFirstField = false;
			} else {
				updateString.append(SQL_COMMA);
			}
			updateString.append(criterions.getKey().getSQLFieldName()).append(SQL_QUESTION);
		}
		updateString.append(SQL_WHERE_ID).append(SQL_QUESTION);
		
		Object[] updateValues = values.values().toArray();
		Object[] allValues = new Object[updateValues.length + 1];
		System.arraycopy(updateValues, 0, allValues, 0, updateValues.length);
		allValues[allValues.length - 1] = String.valueOf(administrator.getId());
		
		Connection connection = null;
		PreparedStatement statement = null;		
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, updateString.toString(), false, allValues);
			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DAOException("Updating administrator failed, no rows affected.");
			}			
		} catch (SQLException e) {
			throw new DAOException("SQLException in AdministratorDAO", e);
		} finally {
			POOL.closeConnection(connection, statement);
		}
	}
}
