package com.epam.hostel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.dao.DAOUtil;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.pool.ConnectionPool;

public class UserDAOImpl implements UserDAO {
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	private static final String SQL_FIND = "SELECT id, email, phone, password, first_name, last_name, is_banned, discount, registration_date, birth_date FROM users WHERE ";	
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, email, phone, password, first_name, last_name, is_banned, discount, registration_date, birth_date FROM users ORDER BY id";
	private static final String SQL_CREATE = "INSERT INTO users (email, phone, password, first_name, last_name, is_banned, discount, registration_date, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";		
	private static final String SQL_UPDATE = "UPDATE users SET ";
	private static final String SQL_DELETE = "DELETE FROM users WHERE id = ?";
	private static final String SQL_AND = " AND ";
	private static final String SQL_QUESTION = " = ?";
	private static final String SQL_WHERE_ID = " WHERE id";
	private static final String SQL_COMMA = " , ";
	private static final String EXCEPTION_SQL = "SQLException in UserDAO";
	private static final String EXCEPTION_NO_ROWS_AFFECTED = "Exception: no rows affected.";
	private static final String EXCEPTION_NO_KEY_OBTAINED = "Exception: no generated key obtained.";

	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt(UserCriterion.ID.getSQLFieldName()));
		user.setEmail(resultSet.getString(UserCriterion.EMAIL.getSQLFieldName()));
		user.setPhone(resultSet.getString(UserCriterion.PHONE.getSQLFieldName()));		
		user.setPassword(resultSet.getString(UserCriterion.PASSWORD.getSQLFieldName()));
		user.setFirstName(resultSet.getString(UserCriterion.FIRST_NAME.getSQLFieldName()));
		user.setLastName(resultSet.getString(UserCriterion.LAST_NAME.getSQLFieldName()));
		user.setBanned(resultSet.getBoolean(UserCriterion.IS_BANNED.getSQLFieldName()));
		user.setDiscount(resultSet.getInt(UserCriterion.DISCOUNT.getSQLFieldName()));
		user.setRegistrationDate(resultSet.getDate(UserCriterion.REGISTRATION_DATE.getSQLFieldName()));
		user.setBirthDate(resultSet.getDate(UserCriterion.BIRTH_DATE.getSQLFieldName()));
		return user;
	}
	
	@Override
	public List<User> find(Map<UserCriterion, Object> values) throws DAOException {
		StringBuilder searchString = new StringBuilder(SQL_FIND);
		
		boolean isFirstCriterion = true;
		for (Map.Entry<UserCriterion, Object> criterions : values.entrySet()) {
			if (isFirstCriterion) {
				isFirstCriterion = false;
			} else {
				searchString.append(SQL_AND);
			}
			searchString.append(criterions.getKey().getSQLFieldName()).append(SQL_QUESTION);
		}

		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, searchString.toString(), false, values.values().toArray());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				User user = map(resultSet);
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return users;
	}

	@Override	
	public boolean create(User user) throws DAOException {
		Object[] values = { user.getEmail(), user.getPhone(), user.getPassword(), user.getFirstName(),
				user.getLastName(), user.isBanned(), user.getDiscount(),
				DAOUtil.toSqlDate(user.getRegistrationDate()), DAOUtil.toSqlDate(user.getBirthDate()) };

		boolean isCreated = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_CREATE, true, values);
			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DAOException(EXCEPTION_NO_ROWS_AFFECTED);
			}

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				user.setId(resultSet.getInt(1));
			} else {
				throw new DAOException(EXCEPTION_NO_KEY_OBTAINED);
			}
			
			isCreated = true;
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return isCreated;
	}	

	@Override	
	public List<User> list() throws DAOException {
		List<User> users = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_LIST_ORDER_BY_ID, false);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				users.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return users;
	}

	@Override
	public void update(int userId, Map<UserCriterion, Object> values) throws DAOException {
		StringBuilder updateString = new StringBuilder(SQL_UPDATE);
		
		boolean isFirstField = true;
		for (Map.Entry<UserCriterion, Object> criterions : values.entrySet()) {
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
		allValues[allValues.length - 1] = String.valueOf(userId);
		
		Connection connection = null;
		PreparedStatement statement = null;		
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, updateString.toString(), false, allValues);
			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DAOException(EXCEPTION_NO_ROWS_AFFECTED);
			}			
		} catch (SQLException e) {			
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean isEmailFree(String email) throws DAOException {
		boolean isFree = false;
		
		Map<UserCriterion, Object> searchWords = new HashMap<>();
		searchWords.put(UserCriterion.EMAIL, email);		
		List<User> users = find(searchWords);
		
		if (users.isEmpty()){
			isFree = true;
		}
		
		return isFree;
	}

	@Override
	public boolean isPhoneFree(String phone) throws DAOException {
		boolean isFree = false;
		
		Map<UserCriterion, Object> searchWords = new HashMap<>();
		searchWords.put(UserCriterion.PHONE, phone);		
		List<User> users = find(searchWords);
		
		if (users.isEmpty()){
			isFree = true;
		}
		
		return isFree;
	}

	@Override
	public void delete(int userId) throws DAOException {
		Object[] values = {userId};
		
		Connection connection = null;
		PreparedStatement statement = null;		
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DAOException(EXCEPTION_NO_ROWS_AFFECTED);
			}			
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_SQL, e);
		} finally {
			POOL.closeConnection(connection, statement);
		}
	}
}
