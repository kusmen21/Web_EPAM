package com.epam.hostel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Command {
	// request parameters
	String ID = "id";
	String EMAIL = "email";
	String PASSWORD = "password";
	String PHONE = "phone";
	String FNAME = "fname";
	String LNAME = "lname";
	String BIRTH_DATE = "birth_date";
	String DISCOUNT = "discount";
	String BED_NUMBER = "n";
	String FIND_USER_ID = "findUserId";
	String FIND_USER_EMAIL = "findUserEmail";
	String FIND_USER_PHONE = "findUserPhone";
	String DATE_FROM = "dateFrom";
	String DATE_TO = "dateTo";
	String LOCALE = "locale";
	// redirect pages
	String ADMIN_PAGE = "page.admin_menu";
	String ERROR_PAGE = "page.error";
	String INDEX_PAGE = "page.index";
	String REQUEST_PAGE = "page.request";
	String SHOW_USER_PAGE = "page.show_user";
	String REGISTRATION_PAGE = "page.registration";
	String UPDATE_USER_PAGE = "page.update_user";
	String NOT_APPROVED_PAGE = "page.not_approved";
	//String RELOAD_NOT_APPROVED_REQUESTS_PAGE = "page.reload_not_approved_requests";
	String USER_REQUESTS_PAGE = "page.user_requests";
	//String RELOAD_USER_REQUESTS_PAGE = "page.reload_user_requests";
	String TODAY_REQUESTS_PAGE = "page.today_requests";
	String USER_REQUESTS_ADMIN_PAGE = "page.user_requests_admin";
	// request or session attributes	
	String ADMIN_ERROR_ATTRIBUTE = "adminError";
	String ADMIN_INFO_ATTRIBUTE = "adminInfo";
	String LOGIN_ERROR_ATTRIBUTE = "loginError";
	String LOGIN_INFO_ATTRIBUTE = "loginInfo";
	String REQUEST_INFO_ATTRIBUTE = "requestInfo";
	String REQUEST_ERROR_ATTRIBUTE = "requestError";
	String REGISTER_ERROR_ATTRIBUTE = "registerError";
	String REGISTER_INFO_ATTRIBUTE = "registerInfo";
	String USER_INFO_ATTRIBUTE = "userInfo";
	String USER_ERROR_ATTRIBUTE = "userError";
	String LOCALE_ATTRIBUTE = "locale";
	String USER_ATTRIBUTE = "user";
	String FOUND_USER_ATTRIBUTE = "foundUser";
	String ADMIN_ATTRIBUTE = "admin";
	String CUSTOM_EXCEPTION_ATTRIBUTE = "customException";
	String NOT_APPROVED_REQUESTS_ATTRIBUTE = "notApprovedRequests";
	String USER_REQUESTS_ATTRIBUTE = "userRequests";
	String USER_ACTUAL_REQUESTS_ATTRIBUTE = "userActualRequests";
	String TODAY_REQUESTS_ATTRIBUTE = "todayRequests";	
	String PAGE_ATTRIBUTE = "page";
	String LAST_COMMAND_ATTRIBUTE = "lastCommand";
		
	 /**
     * @param request request from client side
     * @return URI of the page to display on client side
     */
	String execute(HttpServletRequest request, HttpServletResponse response);	
}
