package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.User;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class FindUserCommand extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(FindUserCommand.class);
	private static final String ERROR_SET_ONE_FIELD = "error.set_one_field";
	private static final String ERROR_USER_NOT_FOUND = "error.user_not_found";

	public FindUserCommand() {
		setAdminPage(true);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(SHOW_USER_PAGE);
		User user = null;
		String id = request.getParameter(FIND_USER_ID);
		String email = request.getParameter(FIND_USER_EMAIL);
		String phone = request.getParameter(FIND_USER_PHONE);
		AdminService adminService = ServiceFactory.getAdminService();

		// check fields
		if ((email == null || email.isEmpty()) && (phone == null || phone.isEmpty()) && (id == null || id.isEmpty())) {
			request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, ERROR_SET_ONE_FIELD);
			page = ConfigurationManager.getProperty(ADMIN_PAGE);
		} else {
			try {
				if (id != null && !id.isEmpty()) {
					user = adminService.findUser(UserCriterion.ID, id);
				} else if (email != null && !email.isEmpty()) {
					user = adminService.findUser(UserCriterion.EMAIL, email);
				} else {
					if (phone != null && !phone.isEmpty()) {
						user = adminService.findUser(UserCriterion.PHONE, phone);
					}
				}
				// check found user
				if (user == null) {
					request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, ERROR_USER_NOT_FOUND);
					page = ConfigurationManager.getProperty(ADMIN_PAGE);
				}
				request.getSession().setAttribute(FOUND_USER_ATTRIBUTE, user);
			} catch (ServiceException e) {
				log.error(e);
				request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
				page = ConfigurationManager.getProperty(ERROR_PAGE);
			}
		}
		return page;
	}

}
