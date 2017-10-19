package com.epam.hostel.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Request;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class GetNotApprovedRequestsCommand  extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(GetNotApprovedRequestsCommand.class);	
	private static final String UNKNOWN_ERROR = "error.unknown_error";
	private static final String INFO_NO_REQUESTS_FOUND = "info.no_requests_found";
	
	public GetNotApprovedRequestsCommand() {
		setAdminPage(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(NOT_APPROVED_PAGE);
		AdminService adminService = ServiceFactory.getAdminService();
		
		try {
			List<Request> requests = adminService.getNotApprovedRequests();
			if (requests == null){
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, UNKNOWN_ERROR);
			} else if (requests.isEmpty()) {
				request.getSession().setAttribute(NOT_APPROVED_REQUESTS_ATTRIBUTE, requests);
				request.getSession().setAttribute(ADMIN_INFO_ATTRIBUTE, INFO_NO_REQUESTS_FOUND);
			} else {
				request.getSession().setAttribute(NOT_APPROVED_REQUESTS_ATTRIBUTE, requests);
			}
		} catch (ServiceException e) {
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);
		}
		
		return page;
	}
}
