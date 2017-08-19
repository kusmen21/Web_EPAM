package com.epam.hostel.command.impl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.resource.MessageManager;

/**
 * Command provides change language to English or Russian language 
 */
public class ChangeLanguageCommand extends ExtendedСommand {
	private static Logger log = LogManager.getLogger(ChangeLanguageCommand.class);
	private static final String LOCALE_EMPTY_MESSAGE = "error.locale_empty";
	private static final String RUSSIAN = "ru";
	private static final String ENGLISH = "en";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(INDEX_PAGE);
		// redirect to previous page
		Object previousPageObject = request.getSession().getAttribute(PAGE_ATTRIBUTE);
		if (previousPageObject != null && previousPageObject instanceof String)
		{
			String previousPage = (String) previousPageObject;
			page = ConfigurationManager.getProperty(previousPage);
		}		
		
		String locale = request.getParameter(LOCALE);

		if (locale != null && !locale.isEmpty()) {
			if (locale.equals(RUSSIAN)) {
				request.getSession().setAttribute(LOCALE_ATTRIBUTE, RUSSIAN);
			} else {
				if (locale.equals(ENGLISH)) {
					request.getSession().setAttribute(LOCALE_ATTRIBUTE, ENGLISH);
				}
			}
		} else {
			String errorMessage =  MessageManager.getProperty(LOCALE_EMPTY_MESSAGE);
			log.error(errorMessage);
			request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, errorMessage);
		}
		return page;
	}
}