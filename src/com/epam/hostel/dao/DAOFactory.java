package com.epam.hostel.dao;

import com.epam.hostel.dao.impl.AdministratorDAOImpl;
import com.epam.hostel.dao.impl.BedDAOImpl;
import com.epam.hostel.dao.impl.RequestDAOImpl;
import com.epam.hostel.dao.impl.UserDAOImpl;

public final class DAOFactory {
	private static final DAOFactory instance = new DAOFactory();
	private static final UserDAO USER_DAO = new UserDAOImpl();
	private static final AdministratorDAO ADMINISTRATOR_DAO = new AdministratorDAOImpl();
	private static final RequestDAO REQUEST_DAO = new RequestDAOImpl();
	private static final BedDAO BED_DAO = new BedDAOImpl();
	
	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return instance;
	}
	
	public static UserDAO getUserDAO()	{
		return USER_DAO;
	}

	public static AdministratorDAO getAdministratorDAO() {
		return ADMINISTRATOR_DAO;
	}

	public static RequestDAO getRequestDAO() {
		return REQUEST_DAO;
	}
	
	public static BedDAO getBedDAO(){
		return BED_DAO;
	}
}
