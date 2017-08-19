package com.epam.hostel.command.impl;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.bean.User;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.resource.MessageManager;
import com.epam.hostel.service.RequestService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class ShowUserRequestsCommand extends ExtendedСommand {
	private static Logger log = LogManager.getLogger(ShowUserRequestsCommand.class);
	private static final String USER_NOT_LOGGED = "error.user_not_logged";	
	private static final String UNKNOWN_ERROR = "error.unknown_error";

	public ShowUserRequestsCommand() {
		setUserPage(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(USER_REQUESTS_PAGE);
		Object userAttribute = request.getSession().getAttribute(USER_ATTRIBUTE);
		RequestService requestService = ServiceFactory.getRequestService();
		Map<Request, List<Bed>> requests = null;

		if (userAttribute != null && userAttribute instanceof User) {
			User user = (User) userAttribute;
			try {
				requests = requestService.getUserRequests(user.getId());
				if (requests == null){
					request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, MessageManager.getProperty(UNKNOWN_ERROR));
				} else {	
					Map<Request, List<Bed>> actualRequests = requestService.getActualRequests(requests);
					Map<Request, List<Bed>> oldRequests = requests;
					oldRequests.keySet().removeAll(actualRequests.keySet());					
					request.getSession().setAttribute(USER_REQUESTS_ATTRIBUTE, oldRequests);
					request.getSession().setAttribute(USER_ACTUAL_REQUESTS_ATTRIBUTE, actualRequests);
				}
			} catch (ServiceException e) {
				log.error(e);
				request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
				page = ConfigurationManager.getProperty(ERROR_PAGE);
			}
		} else {
			String errorMessage = MessageManager.getProperty(USER_NOT_LOGGED);
			log.error(errorMessage);
			request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, errorMessage);
			page = ConfigurationManager.getProperty(INDEX_PAGE);
		}

		return page;
	}
}
