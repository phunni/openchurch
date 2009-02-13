package uk.co.monkeypower.openchurch.core.users.entities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;


public class RoleTest {
    
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
    public void createRole() {
	Role role = new Role();
	role.setTitle("test");
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	manager.getTransaction().begin();
	manager.persist(role);
	manager.getTransaction().commit();
	Query locateTestRole = manager.createQuery("select r from Role r where r.title = 'test'");
	Role testRole = (Role)locateTestRole.getSingleResult();
	assertNotNull("There should be one test role", testRole);
    }
    
    @After
    public void cleanUp(){
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	Query deleteTestData = manager.createQuery("delete from Role r where r.title = 'test'");
	manager.getTransaction().begin();
	int deleted = deleteTestData.executeUpdate();
	manager.getTransaction().commit();
	System.out.println("Number of rows cleaned up: " + deleted);
    }

}
