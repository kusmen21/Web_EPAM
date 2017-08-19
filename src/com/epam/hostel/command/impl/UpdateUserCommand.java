package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.User;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.resource.MessageManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public class UpdateUserCommand  extends ExtendedСommand {
	private static Logger log = LogManager.getLogger(UpdateUserCommand.class);	
	private static final String ERROR_DATA_INCORRECT = "error.update_data_incorrect";
	private static final String ERROR_SET_ONE_FIELD = "error.update_set_one_field";
	private static final String INFO_USER_UPDATED = "info.user_updated";
	private static final String NO_ID = "error.id_empty";
	private static final String UNKNOWN_ERROR = "error.unknown_error";

	public UpdateUserCommand() {
		setAdminPage(true);
		setRedirect(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(SHOW_USER_PAGE);
		String userId = request.getParameter(ID);
		AdminService adminService = ServiceFactory.getAdminService();
		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		String phone = request.getParameter(PHONE);
		String fname = request.getParameter(FNAME);
		String lname = request.getParameter(LNAME);
		String stringDate = request.getParameter(BIRTH_DATE);
		String discount = request.getParameter(DISCOUNT);

		try {
			ValidationResult result = adminService.updateUser(userId, email, password, phone, fname, lname, stringDate, discount);
			switch (result) {
			case ALL_RIGHT:
				User updatedUser = adminService.findUser(UserCriterion.ID, String.valueOf(userId));
				request.getSession().setAttribute(FOUND_USER_ATTRIBUTE, updatedUser);
				request.getSession().setAttribute(ADMIN_INFO_ATTRIBUTE, MessageManager.getProperty(INFO_USER_UPDATED));
				break;
			case NO_ID:
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, MessageManager.getProperty(NO_ID));
				page = ConfigurationManager.getProperty(UPDATE_USER_PAGE);
				break;
			case NO_FIELDS:
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, MessageManager.getProperty(ERROR_SET_ONE_FIELD));
				page = ConfigurationManager.getProperty(UPDATE_USER_PAGE);
				break;
			case DATA_INCORRECT:
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, MessageManager.getProperty(ERROR_DATA_INCORRECT));
				page = ConfigurationManager.getProperty(UPDATE_USER_PAGE);
				break;
			default:
				request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, MessageManager.getProperty(UNKNOWN_ERROR));
				page = ConfigurationManager.getProperty(UPDATE_USER_PAGE);
				break;
			}			
		} catch (ServiceException e) {
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);
		}
		
		return page;
	}
}
