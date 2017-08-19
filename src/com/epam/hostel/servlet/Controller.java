package com.epam.hostel.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.command.CommandFactory;
import com.epam.hostel.command.ExtendedСommand;
import com.epam.hostel.dao.pool.ConnectionPool;
import com.epam.hostel.resource.ConfigurationManager;
import com.epam.hostel.resource.MessageManager;

@WebServlet(name = "controller", value = "/controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(Controller.class);
	private static final String USER_ATTRIBUTE = "user";
	private static final String ADMIN_ATTRIBUTE = "admin";
	private static final String INDEX_PAGE = "page.index";
	private static final String LOGIN_ERROR_ATTRIBUTE = "loginError";
	private static final String USER_NOT_LOGGED_ERROR = "error.user_not_logged";
	private static final String ADMIN_NOT_LOGGED_ERROR = "error.admin_login";
	private static final String PAGE_IS_NULL_LOG = "page is null; command = ";
	private static final String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	private static final String DELIMITER = "?";
	/**
	 * method checks the possibility of executing the command for the current user, 
	 * executes the command, 
	 * forwards to the next page	
	 * @param request request from client side
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ExtendedСommand command = (ExtendedСommand) CommandFactory.defineCommand(request);		
		String page = null;		
		// check user login for user commands
		if (command.isUserPage()) {
			Object user = request.getSession().getAttribute(USER_ATTRIBUTE);
			if (user == null) {
				request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, MessageManager.getProperty(USER_NOT_LOGGED_ERROR));
				page = ConfigurationManager.getProperty(INDEX_PAGE);
				response.sendRedirect(request.getContextPath() + page);
			}			
		} else {
			// check admin login for admin commands
			if (command.isAdminPage()) {
				Object admin = request.getSession().getAttribute(ADMIN_ATTRIBUTE);
				if (admin == null) {
					request.getSession().setAttribute(LOGIN_ERROR_ATTRIBUTE, MessageManager.getProperty(ADMIN_NOT_LOGGED_ERROR));
					page = ConfigurationManager.getProperty(INDEX_PAGE);
					response.sendRedirect(request.getContextPath() + page);
				}
			}
		}
		
		if (page == null) {
			page = command.execute(request, response);
			// saving last command
			String lastCommand = request.getServletPath() + DELIMITER + request.getQueryString();
			request.getSession().setAttribute(LAST_COMMAND_ATTRIBUTE, lastCommand);
			if (page != null) {
				if (command.isRedirect()) {
					response.sendRedirect(request.getContextPath() + page);
				} else {					
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
					dispatcher.forward(request, response);
				}
			} else {
				log.warn(PAGE_IS_NULL_LOG + command.getClass().getSimpleName());
				page = ConfigurationManager.getProperty(INDEX_PAGE);
				response.sendRedirect(request.getContextPath() + page);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public void destroy() {
		super.destroy();
		ConnectionPool.getInstance().dispose();
	}
}
