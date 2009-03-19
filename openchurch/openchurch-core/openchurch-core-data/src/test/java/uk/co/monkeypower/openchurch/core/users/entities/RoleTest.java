package uk.co.monkeypower.openchurch.core.users.entities;

import java.util.List;
import java.util.Vector;

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
    public void createRole() {
	Role role = new Role();
	role.setTitle("test");
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	manager.getTransaction().begin();
	manager.persist(role);
	manager.getTransaction().commit();
	Query locateTestRole = manager.createQuery("select r from Role r where r.title = 'test'");
	Role testRole = (Role)locateTestRole.getSingleResult();
	assertNotNull("There should be one test role", testRole);
    }
    
    @Test
    public void searchUsersInRole() throws Exception{
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
	assertEquals("The test user should be returned by ID.", user, testRole.getUser(user.getId()));
	assertEquals("The test user should be returned by Username.", user, testRole.getUser(user.getUsername()));
    }
    
    @After
    public void cleanUp(){
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	Query deleteTestData = manager.createQuery("delete from Role r where r.title = 'test'");
	manager.getTransaction().begin();
	int deleted = deleteTestData.executeUpdate();
	manager.getTransaction().commit();
	deleteTestData = manager.createQuery("delete from User u where u.username = 'test-user'");
	manager.getTransaction().begin();
	deleted += deleteTestData.executeUpdate();
	manager.getTransaction().commit();
	System.out.println("Number of rows cleaned up: " + deleted);
    }

}
