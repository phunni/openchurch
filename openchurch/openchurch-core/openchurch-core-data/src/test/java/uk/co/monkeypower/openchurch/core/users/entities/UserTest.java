package uk.co.monkeypower.openchurch.core.users.entities;


import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;



public class UserTest {

	private static EntityManager manager;

    @BeforeClass
    public static void setUpJNDI() {
        Properties jdbcProperties = new Properties();
        try {
            jdbcProperties.load(RoleTest.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            System.out.println("Failed to locate properties file for datasource: " + e);
        }
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("openchurch_users_test", jdbcProperties);
        manager = factory.createEntityManager();

    }
	
	@AfterClass 
    public static void closeManager(){ 
        manager.close(); 
    }

	@Test
	public void createUser() throws Exception{
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		User user = new User();
		user.setUsername("test-user");
		user.setPreferredNames("test-name");
		user.setSurname("test-surname");
		user.setEmailAddress("test@test.co.uk");
		user.setPassword("test-password");
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
	}

	@Test(expected=RollbackException.class)
	public void createNonUniqueUser() throws Exception {
		createUser();
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		User user = new User();
		user.setUsername("test-user");
		user.setPreferredNames("test-name");
		user.setSurname("test-surname");
		user.setEmailAddress("test@test.co.uk");
		user.setPassword("test-password");
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
	}

	@Test(expected=UserAttributeValidationException.class)
	public void createUserDodgyEmail() throws Exception {
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		User user = new User();
		user.setUsername("test-user");
		user.setPreferredNames("test-name");
		user.setSurname("test-surname");
		user.setEmailAddress("dodgyEmail...");
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
	}

	@Test
	public void createUserWithAnAddress() throws Exception {
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		User user = new User();
		user.setUsername("test-user");
		user.setPreferredNames("test-name");
		user.setSurname("test-surname");
		user.setEmailAddress("test@test.co.uk");
		user.setPassword("test-password");
		Address address = new Address();
		address.setFirstLine("test");
		address.setTown("test");
		address.setCounty("address");
		address.setPostCode("AB12 3CD");
		Vector<Address> addresses = new Vector<Address>();
		addresses.add(address);
		user.setAddresses(addresses);
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
	}

	@Test
	public void createUserWithRoles() throws Exception {
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		User user = new User();
		user.setUsername("test-user");
		user.setPreferredNames("test-name");
		user.setSurname("test-surname");
		user.setEmailAddress("test@test.co.uk");
		user.setPassword("test-password");
		Query getRolesByTitle = manager.createNamedQuery("selectRoleBytitle");
		getRolesByTitle.setParameter(1, "member");
		Role testRole = (Role) getRolesByTitle.getSingleResult();
		List <Role> roles = new Vector<Role>();
		roles.add(testRole);
		user.setRoles(roles);
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();

	}
	
	@Test
	public void authenticateUser() throws Exception {
		User user = new User();
		user.setUsername("test-user");
		user.setPreferredNames("test-name");
		user.setSurname("test-surname");
		user.setEmailAddress("test@test.co.uk");
		user.setPassword("test-password");
		user.authenticate("test-password");
		assertTrue(user.isAuthenticated());
		user.setPassword("adminadmin");
		System.out.println(user.getPassword());
	}
	
	// This isn't really the place to do this as it's actually testing the setup of 
	// the database as opposed to the User class.  It seemed reasonable though.
	@Test
	public void confirmAdminAndGuestUserSetUp() {
		Query findUserQuery = manager.createQuery("select u from User u where u.username = ?1");
		findUserQuery.setParameter(1, "admin");
		User user = (User) findUserQuery.getSingleResult();
		assertNotNull(user);
		List<Role> roles = user.getRoles();
		boolean isAdmin = false;
		for (Role currentRole : roles) {
			if (currentRole.getTitle().equals("admin")) {
				isAdmin = true;
			}
		}
		assertTrue(isAdmin);
		user.authenticate("adminadmin");
		findUserQuery.setParameter(1, "guest");
		user = (User) findUserQuery.getSingleResult();
		assertNotNull(user);
	}

	@After
	public void cleanUp(){
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		Query selectForDeletionQuery = manager.createQuery("select u from User u where u.username = 'test-user'");
		manager.getTransaction().begin();
		List<User> usersToBeDeleted = selectForDeletionQuery.getResultList();
		manager.getTransaction().commit();
		for (User currentUser : usersToBeDeleted) {
			manager.getTransaction().begin();
			manager.remove(currentUser);
			manager.getTransaction().commit();
		}
	}

}
