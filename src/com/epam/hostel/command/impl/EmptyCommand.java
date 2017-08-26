package com.epam.hostel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.ConfigurationManager;

public class EmptyCommand  extends ExtendedСommand {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {		
		String page = ConfigurationManager.getProperty(INDEX_PAGE);
        return page;
	}

}
