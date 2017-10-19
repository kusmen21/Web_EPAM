package test.com.epam.hostel;

import org.junit.Assert;
import org.junit.Test;

import com.epam.hostel.bean.User;
import com.epam.hostel.criterion.UserCriterion;
import com.epam.hostel.service.AdminService;
import com.epam.hostel.service.LogInService;
import com.epam.hostel.service.RequestService;
import com.epam.hostel.service.ServiceFactory;
import com.epam.hostel.service.exeption.ServiceException;

public class AdminServiceTest {
	private static final String EXISTING_USER_ID = "1";
	private static final String EXISTING_USER_EMAIL = "kusmen21kda@gmail.com";
	private static final String EXISTING_USER_PHONE = "80256234841";

	private static final String USER_EMAIL = "test@test.test";
	private static final String USER_PASSWORD = "Qwerty999";
	private static final String USER_PHONE = "12345678900000";
	private static final String USER_FNAME = "fname";
	private static final String USER_LNAME = "lname";
	private static final String USER_DATE = "2017-10-10";
	
	@Test
	public void deleteUserTest() throws ServiceException {
		AdminService service = ServiceFactory.getAdminService();
		LogInService logInService = ServiceFactory.getLogInService();
		User user = new User();

		logInService.register(USER_EMAIL, USER_PASSWORD, USER_PHONE, USER_FNAME, USER_LNAME, USER_DATE);
		user = logInService.getUser(USER_EMAIL, USER_PASSWORD);
		service.deleteUser(user.getId());
		user = logInService.getUser(USER_EMAIL, USER_PASSWORD);
		Assert.assertNull(user);
		int existingId = Integer.parseInt(EXISTING_USER_ID);
		Assert.assertFalse(service.deleteUser(existingId));
	}

	@Test
	public void findUserTest() throws ServiceException {
		AdminService service = ServiceFactory.getAdminService();

		User user = service.findUser(UserCriterion.ID, EXISTING_USER_ID);
		Assert.assertNotNull(user);
		user = null;

		user = service.findUser(UserCriterion.EMAIL, EXISTING_USER_EMAIL);
		Assert.assertNotNull(user);
		user = null;

		user = service.findUser(UserCriterion.PHONE, EXISTING_USER_PHONE);
		Assert.assertNotNull(user);
	}

	@Test
	public void banUserTest() throws ServiceException {
		AdminService service = ServiceFactory.getAdminService();
		LogInService logInService = ServiceFactory.getLogInService();
		User user = new User();

		try {
			logInService.register(USER_EMAIL, USER_PASSWORD, USER_PHONE, USER_FNAME, USER_LNAME, USER_DATE);
			user = logInService.getUser(USER_EMAIL, USER_PASSWORD);
			Assert.assertTrue(service.banUser(user.getId(), true));
		} finally {
			service.deleteUser(user.getId());
		}
	}
	
	@Test
	public void updateUserTest() throws ServiceException {
		AdminService service = ServiceFactory.getAdminService();
		LogInService logInService = ServiceFactory.getLogInService();
		User user = new User();

		try {
			logInService.register(USER_EMAIL, USER_PASSWORD, USER_PHONE, USER_FNAME, USER_LNAME, USER_DATE);
			user = logInService.getUser(USER_EMAIL, USER_PASSWORD);
			service.updateUser(String.valueOf(user.getId()), null, null, null, null, USER_FNAME, null, null);
			User updatedUser = logInService.getUser(USER_EMAIL, USER_PASSWORD);
			boolean result = updatedUser.getLastName().equals(USER_FNAME);
			Assert.assertTrue(result);
		} finally {
			service.deleteUser(user.getId());
		}
	}
}
