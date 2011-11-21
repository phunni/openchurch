package uk.co.monkeypower.openchurch.core.users.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

public class UserManagerTest {

	private static EJBContainer ejbContainer;
	private static Context ctx;

	@BeforeClass
	public static void setUp() {
		ejbContainer = EJBContainer.createEJBContainer();
		ctx = ejbContainer.getContext();
	}

	@AfterClass
	public static void tearDown() {
		ejbContainer.close();
	}
	
	@Test
	public void setUser() {
		UserManager manager = null;
		try {
			manager = (UserManager) ctx.lookup("java:global/classes/UserManagerBean!uk.co.monkeypower.openchurch.core.users.beans.UserManager");
		} catch (NamingException e) {
			System.out.println("Failed to lookup the gosh darned bean!");
		}
		assertNotNull(manager);
		User user = new User();
		user.setUsername("test-user");
		manager.setUser(user);
		assertNotNull(user);
		assertTrue(manager.getUser().getUsername().equals("test-user"));
	}

	@Test
	public void findOutIfAdmin() {
		UserManager manager = null;
		try {
			manager = (UserManager) ctx.lookup("java:global/classes/UserManagerBean!uk.co.monkeypower.openchurch.core.users.beans.UserManager");
		} catch (NamingException e) {
			System.out.println("Failed to lookup the gosh darned bean!");
		}
		assertNotNull(manager);
		User user = new User();
		user.setUsername("test-user");
		List<Role> roles = new ArrayList<Role>();
		user.setRoles(roles);
		manager.setUser(user);
		assertFalse(manager.isAdmin());
		Role role = new Role();
		role.setTitle("admin");
		roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		assertTrue(manager.isAdmin());
	}

}
