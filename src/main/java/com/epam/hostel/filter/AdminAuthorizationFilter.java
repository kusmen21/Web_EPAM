package com.epam.hostel.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.hostel.resource.ConfigurationManager;

/**
 * Filter to prevent access for users who are not administrators
 */
@WebFilter(urlPatterns = {"/adminMenu/*"},
initParams = {@WebInitParam(name = "adminFilter", value = "UTF-8", description = "Admin filter")})
public class AdminAuthorizationFilter implements Filter {
	private static final String ADMIN_ATTRIBUTE = "admin";
	private static final String LOGIN_ERROR = "loginError";
	private static final String USER_NOT_LOGGED = "error.admin_login";
	private static final String INDEX_PAGE = "page.index";

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		if (request.getSession().getAttribute(ADMIN_ATTRIBUTE) != null){
			chain.doFilter(arg0, arg1);
		} else {
			request.getSession().setAttribute(LOGIN_ERROR, USER_NOT_LOGGED);
			response.sendRedirect(request.getContextPath() + ConfigurationManager.getProperty(INDEX_PAGE));
		}
	}
}