package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.bean.User;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.LogInService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class LoginCommand extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(LoginCommand.class);
	private static final String ADMINISTRATOR = "admin";
	private static final String LOGIN_INFO_ADMINISTRATOR = "info.login_administrator";
	private static final String LOGIN_ERROR = "error.login";
	private static final String LOGIN_INFO = "info.login";
	private static final String ERROR_USER_BANNED = "error.user_banned";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LogInService logInService = ServiceFactory.getLogInService();
		String page = ConfigurationManager.getProperty(INDEX_PAGE);
		User user = null;
		Administrator administrator = null;
		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);

		// administrator login
		if (email != null && email.equals(ADMINISTRATOR)) {
			try {
				administrator = logInService.getAdministrator(password);
				if (administrator != null) {
					Object localeObject = request.getSession().getAttribute(LOCALE);		
					request.getSession().invalidate();	
					request.getSession().setAttribute(LOCALE, localeObject);					
					request.getSession().setAttribute(ADMIN_ATTRIBUTE, administrator);
					request.getSession().setAttribute(LOGIN_INFO_ATTRIBUTE, LOGIN_INFO_ADMINISTRATOR);
				} else {
					request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, LOGIN_ERROR);
				}
			} catch (ServiceException e) {
				log.error(e);
				request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
				page = ConfigurationManager.getProperty(ERROR_PAGE);
			}
		} else {
			// user login
			try {
				user = logInService.getUser(email, password);
				if (user != null) {
					// check is banned
					if (user.isBanned()) {
						request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, ERROR_USER_BANNED);
					} else {
						Object localeObject = request.getSession().getAttribute(LOCALE);		
						request.getSession().invalidate();
						request.getSession().setAttribute(LOCALE, localeObject);
						request.getSession().setAttribute(USER_ATTRIBUTE, user);
						request.getSession().setAttribute(LOGIN_INFO_ATTRIBUTE, LOGIN_INFO);
					}
				} else {
					request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, LOGIN_ERROR);
				}
			} catch (ServiceException e) {
				log.error(e);
				request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
				page = ConfigurationManager.getProperty(ERROR_PAGE);
			}
		}
		return page;
	}

}