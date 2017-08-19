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

public class ShowUserRequestsForAdmin extends ExtendedСommand {
	private static Logger log = LogManager.getLogger(ShowUserRequestsForAdmin.class);
	private static final String ERROR_ID_EMPTY = "error.id_empty";
	private static final String UNKNOWN_ERROR = "error.unknown_error";

	public ShowUserRequestsForAdmin() {
		setAdminPage(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(USER_REQUESTS_ADMIN_PAGE);
		String userId = request.getParameter(ID);
		RequestService requestService = ServiceFactory.getRequestService();
		Map<Request, List<Bed>> requests = null;

		if (userId != null && !userId.isEmpty()) {
			int id = Integer.parseInt(userId);
			try {
				requests = requestService.getUserRequests(id);
				if (requests == null){
					request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, MessageManager.getProperty(UNKNOWN_ERROR));
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
			request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, MessageManager.getProperty(ERROR_ID_EMPTY));
		}

		return page;
	}
}