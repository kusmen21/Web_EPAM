package com.epam.hostel.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.impl.CheckBedsCommand;

/**
 * servlet provides json response with not free beds
 */
@WebServlet(name="checkBeds", value = "/user/checkBeds")
public class CheckBeds extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/plain";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		
		Command command = new CheckBedsCommand();		
		String jsonResponse = command.execute(request, response);
		
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println(jsonResponse);
		out.flush();
		out.close();	
	}
}
