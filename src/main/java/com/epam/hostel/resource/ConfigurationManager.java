package com.epam.hostel.resource;

import java.util.ResourceBundle;

public final class ConfigurationManager {
	private static final String BUNDLE_PATH = "resources.config";
	 private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_PATH);

	 private ConfigurationManager() {
	 }

	 public static String getProperty(String key) {
	      return resourceBundle.getString(key);
     }
}
