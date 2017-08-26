package com.epam.hostel.command.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.User;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.RequestService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public class ContinueRequestCommand  extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(ContinueRequestCommand.class);	
	private static final String REQUEST_CREATED_MESSAGE = "info.request";
	private static final String ERROR_BEDS_NOT_FREE = "request.beds_not_free";
	private static final String ERROR_DATE_NOT_CORRECT = "request.date_not_correct";
	private static final String ERROR_NO_BEDS = "request.no_beds";
	private static final String ERROR_NO_DATE = "request.no_date";
	private static final String ERROR_NO_USER = "request.no_user";
	private static final String UNKNOWN_ERROR = "error.unknown_error";
	private static final String USER_NOT_LOGGED = "error.user_not_logged";	
	
	public ContinueRequestCommand(){
		setUserPage(true);
		setRedirect(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(REQUEST_PAGE);
		RequestService requestService = ServiceFactory.getRequestService();
		Object userAttribute = request.getSession().getAttribute(USER_ATTRIBUTE);
		User user = null;
		String dateFrom = request.getParameter(DATE_FROM);
		String dateTo = request.getParameter(DATE_TO);
		
		if (userAttribute != null && userAttribute instanceof User) {
			user = (User) userAttribute;
		} else {
			String errorMessage = USER_NOT_LOGGED;
			log.error(errorMessage);
			request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, errorMessage);
			page = ConfigurationManager.getProperty(INDEX_PAGE);
		}

		Enumeration<String> parameterNames = request.getParameterNames();
		List<String> parameters = Collections.list(parameterNames);
		List<Integer> bedIds = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			String parameter = BED_NUMBER + i;
			if (parameters.contains(parameter)) {
				bedIds.add(i);
			}
		}
		try{
			ValidationResult validationResult = requestService.createRequest(bedIds, user, dateFrom, dateTo);
			switch (validationResult) {
			case ALL_RIGHT:
				page = ConfigurationManager.getProperty(INDEX_PAGE);
				request.getSession().setAttribute(REQUEST_INFO_ATTRIBUTE, REQUEST_CREATED_MESSAGE);
				break;
			case DATE_INCORRECT:
				request.getSession().setAttribute(REQUEST_ERROR_ATTRIBUTE, ERROR_DATE_NOT_CORRECT);
				break;
			case NO_USER:
				request.getSession().setAttribute(REQUEST_ERROR_ATTRIBUTE, ERROR_NO_USER);
				break;
			case NO_DATES:
				request.getSession().setAttribute(REQUEST_ERROR_ATTRIBUTE, ERROR_NO_DATE);
				break;
			case NO_BEDS:
				request.getSession().setAttribute(REQUEST_ERROR_ATTRIBUTE, ERROR_NO_BEDS);
				break;
			case BEDS_NOT_FREE:
				request.getSession().setAttribute(REQUEST_ERROR_ATTRIBUTE, ERROR_BEDS_NOT_FREE);
				break;
			default:
				request.getSession().setAttribute(REQUEST_ERROR_ATTRIBUTE, UNKNOWN_ERROR);
			}			
		} catch (ServiceException e) {
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);	
			e.printStackTrace();
		}		

		return page;
	}

}
