package uk.co.monkeypower.openchurch.core.users.entities;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.monkeypower.openchurch.core.layout.entities.Menu;



public class RoleTest {
    
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
    public void checkMenus() {
    	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
		Query locateAdminRole = manager
				.createQuery("select r from Role r where r.title = 'admin'");
		Role adminRole = (Role)locateAdminRole.getSingleResult();
		assertNotNull(adminRole);
		List<Menu> menus = adminRole.getMenus();
		assertFalse(menus.isEmpty());
		assertTrue(menus.size() >= 1);
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
