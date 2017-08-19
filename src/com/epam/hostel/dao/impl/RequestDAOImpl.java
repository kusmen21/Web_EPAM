package com.epam.hostel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.command.impl.UpdateUserCommand;
import com.epam.hostel.criterion.BedCriterion;
import com.epam.hostel.criterion.RequestCriterion;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.dao.DAOUtil;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.pool.ConnectionPool;

public class RequestDAOImpl implements RequestDAO {
	private static Logger log = LogManager.getLogger(RequestDAOImpl.class);	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	private static final String SQL_FIND = "SELECT id, user_id, administrator_id, status_id, date_from, date_to, seat_count, comment, registration_date, last_modified_date, price FROM requests WHERE ";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, user_id, administrator_id, status_id, date_from, date_to, seat_count, comment, registration_date, last_modified_date, price FROM requests ORDER BY id";
	private static final String SQL_CREATE = "INSERT INTO requests (user_id, administrator_id, status_id, date_from, date_to, seat_count, comment, registration_date, last_modified_date, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_CREATE_BEDS_HAS_REQUESTS = "INSERT INTO beds_has_requests (beds_id, requests_id) VALUES ";	
	private static final String SQL_DELETE = "DELETE FROM requests WHERE id = ?";
	private static final String SQL_DELETE_BEDS_HAS_REQUESTS = "DELETE FROM beds_has_requests WHERE requests_id = ?";	
	private static final String SQL_GET_REQUESTS_WITH_BEDS_BY_USER_ID = "SELECT r.id, r.user_id, r.administrator_id, r.status_id, r.date_from, r.date_to, r.seat_count, r.comment, r.registration_date, r.last_modified_date, r.price, b.id AS bedId, b.price FROM requests r JOIN beds_has_requests br ON r.id = br.requests_id JOIN beds b ON br.beds_id = b.id AND r.user_id = ?;";
	private static final String SQL_GET_TODAY_REQUESTS_WITH_BEDS = "SELECT r.id, r.user_id, r.administrator_id, r.status_id, r.date_from, r.date_to, r.seat_count, r.comment, r.registration_date, r.last_modified_date, r.price, b.id AS bedId, b.price FROM requests r JOIN beds_has_requests br ON r.id = br.requests_id JOIN beds b ON br.beds_id = b.id AND r.date_from = ?;";
	private static final String SQL_SET_USER_REQUESTS_DENIED = "UPDATE requests SET status_id = 2 WHERE user_id = ?;";
	private static final String SQL_BED_ID = "bedId";
	private static final String SQL_UPDATE = "UPDATE requests SET ";
	private static final String SQL_AND = " AND ";
	private static final String SQL_QUESTION = " = ?";	
	private static final String SQL_COMMA = " , ";
	private static final String SQL_TWO_QUESTIONS = "(?, ?)";
	private static final String SQL_WHERE_ID = " WHERE id";

	private static Request map(ResultSet resultSet) throws SQLException {
		Request request = new Request();
		request.setId(resultSet.getInt(RequestCriterion.ID.getSQLFieldName()));
		request.setUserId(resultSet.getInt(RequestCriterion.USER_ID.getSQLFieldName()));
		request.setAdministratorId(resultSet.getInt(RequestCriterion.ADMINISTRATOR_ID.getSQLFieldName()));
		request.setStatusId(resultSet.getInt(RequestCriterion.STATUS_ID.getSQLFieldName()));		
		request.setDateFrom(resultSet.getDate(RequestCriterion.DATE_FROM.getSQLFieldName()));
		request.setDateTo(resultSet.getDate(RequestCriterion.DATE_TO.getSQLFieldName()));
		request.setSeatCount(resultSet.getInt(RequestCriterion.SEAT_COUNT.getSQLFieldName()));		
		request.setComment(resultSet.getString(RequestCriterion.COMMENT.getSQLFieldName()));
		Timestamp registrationDate = resultSet.getTimestamp(RequestCriterion.REGISTRATION_DATE.getSQLFieldName());
		request.setRegistrationDate(registrationDate);
		request.setLastModifiedDate(resultSet.getDate(RequestCriterion.LAST_MODIFIED_DATE.getSQLFieldName()));
		request.setPrice(resultSet.getLong(RequestCriterion.PRICE.getSQLFieldName()));
		return request;
	}
	
	private static Map<Request, List<Bed>> mapppingRequestsAndBeds(ResultSet resultSet) throws SQLException {
		Map<Request, List<Bed>> requestsAndBeds = new HashMap<>();
		Request request = null;
		int currentRequestId = 0;
		List<Bed> currentBeds = new ArrayList<>();
		
		while (resultSet.next()) {			
			Bed bed = new Bed();
			bed.setId(resultSet.getInt(SQL_BED_ID));
			bed.setPrice(resultSet.getLong(BedCriterion.PRICE.getSQLFieldName()));			
			if (currentRequestId != resultSet.getInt(RequestCriterion.ID.getSQLFieldName())){
				if (request != null){
					requestsAndBeds.put(request, currentBeds);
				}	
				request = map(resultSet);				
				currentRequestId = request.getId();
				currentBeds = new ArrayList<>();
			} 
			currentBeds.add(bed);	
			if (resultSet.isLast()){
				requestsAndBeds.put(request, currentBeds);
			}
		}
		return requestsAndBeds;
	}

	@Override
	public void update(Map<RequestCriterion, Object> values, int requestId) throws DAOException {
		StringBuilder updateString = new StringBuilder(SQL_UPDATE);

		boolean isFirstField = true;
		for (Map.Entry<RequestCriterion, Object> criterions : values.entrySet()) {
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
		allValues[allValues.length - 1] = String.valueOf(requestId);

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, updateString.toString(), false, allValues);
			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DAOException("Updating request failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement);
		}
	}
	
	@Override
	public List<Request> find(Map<RequestCriterion, Object> values) throws DAOException {
		StringBuilder searchString = new StringBuilder(SQL_FIND);

		boolean isFirstCriterion = true;
		for (Map.Entry<RequestCriterion, Object> criterions : values.entrySet()) {
			if (isFirstCriterion) {
				isFirstCriterion = false;
			} else {
				searchString.append(SQL_AND);
			}
			searchString.append(criterions.getKey().getSQLFieldName()).append(SQL_QUESTION);
		}

		List<Request> requests = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, searchString.toString(), false, values.values().toArray());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Request request = map(resultSet);
				requests.add(request);
			}
		} catch (SQLException e) {
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return requests;
	}

	@Override
	public void create(Request request, List<Integer> beds) throws DAOException {
		Object[] values = { 
				request.getUserId(),
				request.getAdministratorId(),
				request.getStatusId(),
				request.getDateFrom(),
				request.getDateTo(),
				request.getSeatCount(),				
				request.getComment(),
				request.getRegistrationDate(),
				request.getLastModifiedDate(),	
				request.getPrice()
				};

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Savepoint savepoint = null;
		try {
			connection = POOL.takeConnection();
			connection.setAutoCommit(false);
			savepoint = connection.setSavepoint("BeforeCreate");
						
			// insert into REQUESTS
			statement = DAOUtil.prepareStatement(connection, SQL_CREATE, true, values);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				connection.rollback(savepoint);
				throw new DAOException("Creating request failed, no rows affected.");
			}
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				request.setId(resultSet.getInt(1));
			} else {
				connection.rollback(savepoint);
				throw new DAOException("Creating request failed, no generated key obtained.");
			}
			
			//insert into BEDS_HAS_REQUESTS
			List<String> bedsAndRequestsValues = new ArrayList<>();
			int requestId = request.getId();
			StringBuilder createString = new StringBuilder(SQL_CREATE_BEDS_HAS_REQUESTS);
			boolean isFirstBed = true;
			for (int bed : beds){	
				if (isFirstBed){
					isFirstBed = false;
				} else{
					createString.append(SQL_COMMA);
				}
				createString.append(SQL_TWO_QUESTIONS);
				bedsAndRequestsValues.add(String.valueOf(bed));
				bedsAndRequestsValues.add(String.valueOf(requestId));
			}
			statement = DAOUtil.prepareStatement(connection, createString.toString(), false, bedsAndRequestsValues.toArray());
			affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				connection.rollback(savepoint);
				throw new DAOException("Creating request failed, no rows affected.");
			}
			
			connection.commit();
		} catch (SQLException e) {	
			try {
				connection.rollback(savepoint);
			} catch (SQLException e1) {
				log.error(e1);
			}			
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public List<Request> list() throws DAOException {
		List<Request> requests = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_LIST_ORDER_BY_ID, false);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				requests.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return requests;
	}

	@Override
	public void delete(int requestId) throws DAOException {
		Object[] values = { requestId };

		Connection connection = null;
		PreparedStatement statement = null;
		Savepoint savepoint = null;
		try {
			connection = POOL.takeConnection();
			connection.setAutoCommit(false);
			savepoint = connection.setSavepoint("BeforeDelete");
			
			// delete from BEDS_HAS_REQUESTS
			statement = DAOUtil.prepareStatement(connection, SQL_DELETE_BEDS_HAS_REQUESTS, false, values);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				connection.rollback(savepoint);
				throw new DAOException("Deleting request failed, no rows affected.");
			}
			
			// delete from REQUESTS
			statement = DAOUtil.prepareStatement(connection, SQL_DELETE, false, values);
			affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				connection.rollback(savepoint);
				throw new DAOException("Deleting request failed, no rows affected.");
			}
			
			connection.commit();			
		} catch (SQLException e) {
			try {
				connection.rollback(savepoint);
			} catch (SQLException e1) {
				log.error(e1);
			}
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement);
		}
	}

	@Override
	public void setUserRequestsDenied(int userId) throws DAOException {
		Object[] values = { userId };	
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_SET_USER_REQUESTS_DENIED, false, values);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public Map<Request, List<Bed>> getUserRequests(int userId) throws DAOException {
		Object[] values = { userId };
		Map<Request, List<Bed>> requestsAndBeds = new HashMap<>();		
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_GET_REQUESTS_WITH_BEDS_BY_USER_ID, false, values);
			resultSet = statement.executeQuery();

			requestsAndBeds = mapppingRequestsAndBeds(resultSet);
		} catch (SQLException e) {
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return requestsAndBeds;		
	}

	@Override
	public Map<Request, List<Bed>> getTodayRequests(Date today) throws DAOException {		
		today = DAOUtil.toSqlDate(today);	
		Object[] values = { today };
		Map<Request, List<Bed>> requestsAndBeds = new HashMap<>();		
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = POOL.takeConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_GET_TODAY_REQUESTS_WITH_BEDS, false, values);
			resultSet = statement.executeQuery();

			requestsAndBeds = mapppingRequestsAndBeds(resultSet);
		} catch (SQLException e) {
			throw new DAOException("SQLException in RequestDAO", e);
		} finally {
			POOL.closeConnection(connection, statement, resultSet);
		}
		return requestsAndBeds;		
	}

}









