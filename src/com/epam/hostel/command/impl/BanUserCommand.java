package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;


public class BanUserCommand  extends ExtendedСommand {
	private static final Logger log = LogManager.getLogger(BanUserCommand.class);
	private static final String INFO_USER_BANNED_MESSAGE = "info.user_banned";
	private static final String INFO_USER_UNBANNED_MESSAGE = "info.user_unbanned";
	private static final String ERROR_ID_EMPTY_MESSAGE = "error.id_empty";
	private static final String UNKNOWN_ERROR = "error.unknown_error";

	public BanUserCommand() {
		setAdminPage(true);
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(ADMIN_PAGE);			
		String userId = request.getParameter(ID);
		String userBan  = request.getParameter(BAN);
		AdminService adminService = ServiceFactory.getAdminService();

		if (userId != null && !userId.isEmpty() && userBan != null && !userBan.isEmpty()) {
			int id = Integer.parseInt(userId);
			int ban = Integer.parseInt(userBan);
			
			try {
				if (ban == 0){
				boolean result = adminService.banUser(id, false);
				if (!result) {					
					request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, UNKNOWN_ERROR);
				}
				request.getSession().setAttribute(ADMIN_INFO_ATTRIBUTE,
						INFO_USER_UNBANNED_MESSAGE);
				} else if (ban == 1){
					boolean result = adminService.banUser(id, true);
					if (!result) {					
						request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, UNKNOWN_ERROR);
					}
					request.getSession().setAttribute(ADMIN_INFO_ATTRIBUTE,
							INFO_USER_BANNED_MESSAGE);
				}
			} catch (ServiceException e) {
				log.error(e);
				request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
				page = ConfigurationManager.getProperty(ERROR_PAGE);
			}
		} else {
			String errorMessage = ERROR_ID_EMPTY_MESSAGE;
			log.error(errorMessage);
			request.getSession().setAttribute(ADMIN_ERROR_ATTRIBUTE, errorMessage);
		}

		return page;
	}
}
