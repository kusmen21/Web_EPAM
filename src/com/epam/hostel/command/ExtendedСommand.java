package com.epam.hostel.command;

public abstract class ExtendedСommand implements Command{
	private boolean redirect;
	private boolean userPage;
	private boolean adminPage;
	
	/**
	 * @param redirect - set sendRedirect to this command for F5 prevention
	 */
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
	/**
	 * @param userPage - set this command to user-only
	 */
	public void setUserPage(boolean userPage) {
		this.userPage = userPage;
	}
	
	/**
	 * @param adminPage  - set this command to administrator-only
	 */
	public void setAdminPage(boolean adminPage) {
		this.adminPage = adminPage;
	}
	
	public boolean isRedirect() {
		return redirect;
	}
	
	public boolean isUserPage() {
		return userPage;
	}
	
	public boolean isAdminPage() {
		return adminPage;
	}	
}
