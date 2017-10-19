package test.com.epam.hostel;

import org.junit.Assert;
import org.junit.Test;

import com.epam.hostel.bean.Administrator;
import com.epam.hostel.bean.User;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.LogInService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;
import com.epam.hostel.validation.ValidationResult;

public class LogInServiceTest {
	 private static final String EXISTING_USER_EMAIL = "kusmen21kda@gmail.com";
	 private static final String EXISTING_USER_PASSWORD = "Qwerty1";
	 
	 private static final String EXISTING_ADMINISTRATOR_PASSWORD = "Qwerty1";
	 
	 private static final String USER_EMAIL = "test@test.test";
	 private static final String USER_PASSWORD = "Qwerty999";
	 private static final String USER_PHONE = "12345678900000";
	 private static final String USER_FNAME = "fname";
	 private static final String USER_LNAME = "lname";
	 private static final String USER_DATE = "2017-10-10";
	 
	 @Test
	 public void getUserTest() throws ServiceException {
		 LogInService logInService = ServiceFactory.getLogInService();
		 
		 User user = logInService.getUser(EXISTING_USER_EMAIL, EXISTING_USER_PASSWORD);
		 Assert.assertNotNull(user);
	 }
	 
	 @Test
	 public void getAdministratorTest() throws ServiceException {
		 LogInService logInService = ServiceFactory.getLogInService();
		 
		 Administrator administrator = logInService.getAdministrator(EXISTING_ADMINISTRATOR_PASSWORD);
		 Assert.assertNotNull(administrator);
	 }
	 
	 @Test
	 public void registerTest() throws ServiceException {
		 LogInService logInService = ServiceFactory.getLogInService();
		 AdminService adminService = ServiceFactory.getAdminService();
		 User user = new User();
		 ValidationResult result;
		 
		 try{
			 result = logInService.register(USER_EMAIL, USER_PASSWORD, USER_PHONE, USER_FNAME, USER_LNAME, USER_DATE);
			 Assert.assertEquals(ValidationResult.ALL_RIGHT, result);
		 } finally {
			user = logInService.getUser(USER_EMAIL, USER_PASSWORD);
			adminService.deleteUser(user.getId());
		 }		 
	 }

}
