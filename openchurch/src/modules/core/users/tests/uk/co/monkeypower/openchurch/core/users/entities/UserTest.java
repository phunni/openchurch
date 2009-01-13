package uk.co.monkeypower.openchurch.core.users.entities;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
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
    
    @After
    public void cleanUp(){
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	Query deleteQuery = manager.createQuery("delete from User u where u.username = 'test-user'");
	manager.getTransaction().begin();
	deleteQuery.executeUpdate();
	manager.getTransaction().commit();
    }

}
