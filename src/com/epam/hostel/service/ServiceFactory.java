package com.epam.hostel.service;


import com.epam.hostel.service.impl.AdminServiceImpl;
import com.epam.hostel.service.impl.LogInServiceImpl;
import com.epam.hostel.service.impl.RequestServiceImpl;

public final class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private static final LogInService LOG_IN_SERVICE = new LogInServiceImpl();
	private static final AdminService ADMIN_SERVICE = new AdminServiceImpl();
	private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();
	
	private ServiceFactory(){}
	
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	public static LogInService getLogInService() {
		return LOG_IN_SERVICE;
	}
	
	public static AdminService getAdminService(){
		return ADMIN_SERVICE;
	}
	
	public static RequestService getRequestService(){
		return REQUEST_SERVICE;
	}
}
