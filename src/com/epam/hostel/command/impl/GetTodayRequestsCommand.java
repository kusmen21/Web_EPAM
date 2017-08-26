package com.epam.hostel.command.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.bean.Request;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class GetTodayRequestsCommand  extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(GetTodayRequestsCommand.class);
	private static final String UNKNOWN_ERROR = "error.unknown_error";
	private static final String INFO_NO_REQUESTS_FOUND = "info.no_requests_found";

	public GetTodayRequestsCommand() {
		setAdminPage(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(TODAY_REQUESTS_PAGE);
		AdminService adminService = ServiceFactory.getAdminService();
		Map<Request, List<Bed>> requests = null;
		
		try {
			requests = adminService.getTodayRequests();
			if (requests == null) {
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, UNKNOWN_ERROR);
			} else if (requests.isEmpty()) {
				request.getSession().setAttribute(TODAY_REQUESTS_ATTRIBUTE, requests);
				request.getSession().setAttribute(ADMIN_INFO_ATTRIBUTE, INFO_NO_REQUESTS_FOUND);
			} else {
				request.getSession().setAttribute(TODAY_REQUESTS_ATTRIBUTE, requests);
			}
		} catch (ServiceException e) {
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);
		}

		return page;
	}
}
