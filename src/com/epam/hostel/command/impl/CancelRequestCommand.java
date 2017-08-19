package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.resource.MessageManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.RequestService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class CancelRequestCommand  extends ExtendedСommand {
	private static Logger log = LogManager.getLogger(CancelRequestCommand.class);
	private static final String UNKNOWN_ERROR = "error.unknown_error";
	private static final String ERROR_ID_EMPTY = "error.id_empty";

	public CancelRequestCommand() {
		setUserPage(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(INDEX_PAGE);
		// do last controllers`s action - reload request list
		Object previousPageObject = request.getSession().getAttribute(LAST_COMMAND_ATTRIBUTE);
		if (previousPageObject != null && previousPageObject instanceof String)
		{
			String previousPage = (String) previousPageObject;
			page = previousPage;
		}		
		RequestService requestService = ServiceFactory.getRequestService();
		String requestId = request.getParameter(ID);

		try {
			if (requestId != null && !requestId.isEmpty()) {
				int id = Integer.parseInt(requestId);
				boolean result = requestService.cancelRequest(id);
				if (!result) {
					request.getSession().setAttribute(USER_ERROR_ATTRIBUTE, MessageManager.getProperty(UNKNOWN_ERROR));
				}				
			} else {
				request.getSession().setAttribute(USER_ERROR_ATTRIBUTE, MessageManager.getProperty(ERROR_ID_EMPTY));
			}
		} catch (ServiceException e) {
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);
		}

		return page;
	}
}