package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.User;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.service.LogInService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public class RegisterCommand  extends ExtendedСommand {

	private static final Logger log = LogManager.getLogger(RegisterCommand.class);
	private static final String REGISTER_INFO = "info.register";
	private static final String ERROR_SET_CORRECT_EMAIL = "register.set_correct_email";
	private static final String ERROR_SET_CORRECT_PASSWORD = "register.set_correct_password";
	private static final String ERROR_SET_CORRECT_LNAME = "register.set_correct_lname";
	private static final String ERROR_SET_CORRECT_FNAME = "register.set_correct_fname";
	private static final String ERROR_SET_CORRECT_BIRTH_DATE = "register.set_correct_birth_date";
	private static final String ERROR_SET_CORRECT_PHONE = "register.set_correct_phone";
	private static final String ERROR_EMAIL_NOT_UNIQUE = "register.email_not_unique";
	private static final String ERROR_PHONE_NOT_UNIQUE = "register.phone_not_unique";
	private static final String ERROR_NOT_ALL_FIELDS = "error.not_all_fields";
	private static final String ERROR_UNKNOWN = "error.unknown_error";
	
	public RegisterCommand() {
		setRedirect(true);
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getProperty(REGISTRATION_PAGE);
		LogInService logInService = ServiceFactory.getLogInService();
		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		String phone = request.getParameter(PHONE);
		String fname = request.getParameter(FNAME);
		String lname = request.getParameter(LNAME);
		String stringDate = request.getParameter(BIRTH_DATE);		
		
		try{				
			ValidationResult result = logInService.register(email, password, phone, fname, lname, stringDate);
			switch (result) {
				case ALL_RIGHT:
					User registeredUser = logInService.getUser(email, password);
					request.getSession().setAttribute(USER_ATTRIBUTE, registeredUser);
					page = ConfigurationManager.getProperty(INDEX_PAGE);
					request.getSession().setAttribute(REGISTER_INFO_ATTRIBUTE, REGISTER_INFO);
					break;
				case EMAIL_INCORRECT:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_SET_CORRECT_EMAIL);
					break;					
				case PASS_INCORRECT:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_SET_CORRECT_PASSWORD);
					break;
				case LNAME_INCORRECT:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_SET_CORRECT_LNAME);
					break;
				case FNAME_INCORRECT:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_SET_CORRECT_FNAME);
					break;
				case DATE_INCORRECT:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_SET_CORRECT_BIRTH_DATE);
					break;
				case PHONE_INCORRECT:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_SET_CORRECT_PHONE);
					break;			       
				case PHONE_NOT_UNIQUE:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_PHONE_NOT_UNIQUE);
					break;
				case EMAIL_NOT_UNIQUE:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_EMAIL_NOT_UNIQUE);				
					break;			    
				case NOT_ALL_FIELDS:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_NOT_ALL_FIELDS);
					break;
				case UNKNOWN_ERROR:
					request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE , ERROR_UNKNOWN);
					break;							
			}			
		} catch (ServiceException e){
			log.error(e);
			request.getSession().setAttribute(CUSTOM_EXCEPTION_ATTRIBUTE, e.getMessage());
			page = ConfigurationManager.getProperty(ERROR_PAGE);	
			e.printStackTrace();
		}
		
		return page;
	}

}
