package com.epam.hostel.dao.exception;

public class ConnectionPoolException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}
}
