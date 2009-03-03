package uk.co.monkeypower.openchurch.core.users.beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.monkeypower.openchurch.core.users.beans.OpenChurchUtilityDatasourceForTesting;
import uk.co.monkeypower.openchurch.core.users.beans.exceptions.UserManagementException;
import uk.co.monkeypower.openchurch.core.users.entities.User;
import static org.junit.Assert.*;



public class UserManagerTest {
    
    private static DataSource dataSource;
    
    @BeforeClass
    public static void setUpJNDI() {	
	System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
	System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

	dataSource = new OpenChurchUtilityDatasourceForTesting();
	try {
	    InitialContext initCtx = new InitialContext();
	    initCtx.createSubcontext("java:");
	    initCtx.createSubcontext("java:comp");
	    initCtx.createSubcontext("java:comp/env");
	    initCtx.createSubcontext("java:comp/env/jdbc");
	    initCtx.bind("java:comp/env/jdbc/openchurch", dataSource);
	} catch(NamingException e){
	    //do nothing...
	}
    }
    
    
    @Test
    public void createUser() {
	EntityManager entityManager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	UserManagerBean manager = new UserManagerBean();
	manager.setEntityManager(entityManager);
	assertNotNull(manager);
	User user = null;
	try {
	    user = manager.createUser("test-user", "your mother", "rocks", "a@b.com", null, null, null, null, null);
	} catch (UserManagementException e) {
	    System.out.println("Dodgy email crept in somehow...");
	}
	assertNotNull(user);
	assertTrue(user.getUsername().equals("test-user"));
    }
    
    @Test
    public void findUsers() {
	//Not sure there's much point continuing with this unit test class - we won't be able to do proper 
	//persistence outside of the container...
    }
    
    @Test
    public void editUser(){
	//see above... 
    }
    
    @Test
    public void removeUser(){
	//see above...
    }

}
