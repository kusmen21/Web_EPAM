package com.epam.hostel.resource;

import java.util.ResourceBundle;

public final class MessageManager {
	private static final String BUNDLE_PATH = "resources.pagecontent";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_PATH);

    private MessageManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
