package com.epam.hostel.dao.pool;

import java.util.ResourceBundle;

public final class DBResourceManager {
	private static final String BUNDLE_PATH = "resources/hostel_db";
	private final static DBResourceManager instance = new DBResourceManager();
	private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PATH);

	public static DBResourceManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}
}
