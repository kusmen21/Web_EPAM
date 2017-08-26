package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;

public class LogoutCommand  extends ExtendedСommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {		
		Object localeObject = request.getSession().getAttribute(LOCALE);		
		request.getSession().invalidate();
		request.getSession().setAttribute(LOCALE, localeObject);
		
		return ConfigurationManager.getProperty(INDEX_PAGE);
	}

}
