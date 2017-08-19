package com.epam.hostel.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.bean.Bed;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.resource.MessageManager;
import com.epam.hostel.service.RequestService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

/**
 * Command provides checking not free beds; return: json response with not free beds
 */
public class CheckBedsCommand  extends ExtendedСommand {
	private static Logger log = LogManager.getLogger(CheckBedsCommand.class);
	private final static String DATES_EMPTY_MESSAGE = "error.dates_empty";
	private final static String LEFT_BRACKET = "[";
	private final static String RIGHT_BRACKET = "]";
	private final static String COMMA = ",";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String dateFrom = request.getParameter(DATE_FROM);
		String dateTo = request.getParameter(DATE_TO);
		List<Bed> notFreeBeds = null;
		StringBuilder sb = new StringBuilder();

		try {
			RequestService requestService = ServiceFactory.getRequestService();
			notFreeBeds = requestService.getNotFreeBeds(dateFrom, dateTo);
		} catch (ServiceException e) {
			log.error(e);
		}
		if (notFreeBeds != null) {
			sb.append(LEFT_BRACKET);
			for (Bed bed : notFreeBeds) {
				sb.append(bed.getId()).append(COMMA);
			}
			int comma = sb.lastIndexOf(COMMA);
			if (comma != -1) {
				sb.deleteCharAt(comma);
			}
			sb.append(RIGHT_BRACKET);
		} else {
			log.error(MessageManager.getProperty(DATES_EMPTY_MESSAGE));
		}

		return sb.toString();
	}

}
