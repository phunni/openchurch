package uk.co.monkeypower.openchurch.core.users.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

public class UserManagerTest {

	@Test
	public void setUser() {
		UserManagerBean manager = new UserManagerBean();
		assertNotNull(manager);
		User user = new User();
		user.setUsername("test-user");
		manager.setUser(user);
		assertNotNull(user);
		assertTrue(manager.getUser().getUsername().equals("test-user"));
	}

	@Test
	public void findOutIfAdmin() {
		UserManagerBean manager = new UserManagerBean();
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
