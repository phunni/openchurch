package uk.co.monkeypower.openchurch.core.users.entities;


import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;



public class UserTest {
    
    private static DataSource dataSource;
    
    @BeforeClass
    public static void setUpJNDI() throws NamingException {	
	System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
	System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
	InitialContext initCtx = new InitialContext();
	initCtx.createSubcontext("java:");
	initCtx.createSubcontext("java:comp");
	initCtx.createSubcontext("java:comp/env");
	initCtx.createSubcontext("java:comp/env/jdbc");

	dataSource = new OpenChurchUtilityDatasourceForTesting();
	initCtx.bind("java:comp/env/jdbc/openchurch", dataSource);
    }
    
    @Test
    public void createUser() throws Exception{
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	User user = new User();
	user.setUsername("test-user");
	user.setPreferredNames("test-name");
	user.setSurname("test-surname");
	user.setEmailAddress("test@test.co.uk");
	manager.getTransaction().begin();
	manager.persist(user);
	manager.getTransaction().commit();
    }
    
    @Test(expected=RollbackException.class)
    public void createNonUniqueUser() throws Exception {
	createUser();
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	User user = new User();
	user.setUsername("test-user");
	user.setPreferredNames("test-name");
	user.setSurname("test-surname");
	user.setEmailAddress("test@test.co.uk");
	manager.getTransaction().begin();
	manager.persist(user);
	manager.getTransaction().commit();
    }
    
    @Test(expected=UserAttributeValidationException.class)
    public void createUserDodgyEmail() throws Exception {
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
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
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	User user = new User();
	user.setUsername("test-user");
	user.setPreferredNames("test-name");
	user.setSurname("test-surname");
	user.setEmailAddress("test@test.co.uk");
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
    
    @After
    public void cleanUp(){
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
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
