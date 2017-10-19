package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class SetPaidRequestCommand extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(SetPaidRequestCommand.class);
	private static final String UNKNOWN_ERROR = "error.unknown_error";
	private static final String ERROR_ID_EMPTY = "error.id_empty";
	private static final String INFO_REQUEST_PAID = "info.request_paid";
	private static final String ERROR_ADMIN_LOGIN = "error.admin_login";

	public SetPaidRequestCommand() {
		setAdminPage(true);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(INDEX_PAGE);
		// do last controllers`s action - reload request list
		Object previousPageObject = request.getSession().getAttribute(LAST_COMMAND_ATTRIBUTE);
		if (previousPageObject != null && previousPageObject instanceof String) {
			String previousPage = (String) previousPageObject;
			page = previousPage;
		}
		AdminService adminService = ServiceFactory.getAdminService();
		String requestId = request.getParameter(ID);

		try {
			if (requestId != null && !requestId.isEmpty()) {
				Object administrator = request.getSession().getAttribute(ADMIN_ATTRIBUTE);
				if (administrator != null) {
					int adminId = ((Administrator) administrator).getId();
					int id = Integer.parseInt(requestId);
					boolean result = adminService.setPaidRequest(id, adminId);
					if (!result) {
						request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, UNKNOWN_ERROR);
					}
					request.getSession().setAttribute(ADMIN_INFO_ATTRIBUTE, INFO_REQUEST_PAID);
				} else {
					request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, ERROR_ADMIN_LOGIN);
				}
			} else {
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, ERROR_ID_EMPTY);
			}
		} catch (ServiceException e) {
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);
		}

		return page;
	}
}
