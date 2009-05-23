package uk.co.monkeypower.openchurch.core.users.entities;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class AddressTest {
    
	private static EntityManager manager;

    @BeforeClass
    public static void setUpJNDI() {
        Properties jdbcProperties = new Properties();
        try {
            jdbcProperties.load(AddressTest.class.getClassLoader().getResourceAsStream("db.properties"));
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
    public void createAddress() throws Exception {
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	Address address = new Address();
	address.setFirstLine("test");
	address.setTown("test");
	address.setCounty("test");
	address.setPostCode("AB12 3CD");
	manager.getTransaction().begin();
	manager.persist(address);
	manager.getTransaction().commit();
    }
    
    @Test(expected=RollbackException.class)
    public void createAddressWithNullData(){
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	Address address = new Address();
	address.setFirstLine("test");
	manager.getTransaction().begin();
	manager.persist(address);
	manager.getTransaction().commit();
    }
    
    @Test(expected=AddressAttributeValidationException.class)
    public void createAddressWithInvalidPostCode() throws Exception{
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	Address address = new Address();
	address.setFirstLine("test");
	address.setTown("test");
	address.setCounty("test");
	address.setPostCode("test");
	manager.getTransaction().begin();
	manager.persist(address);
	manager.getTransaction().commit();
    }
    
    @After
    public void cleanUp() {
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users_test").createEntityManager();
	Query deleteQuery = manager.createQuery("delete from Address a where a.firstLine = 'test'");
	manager.getTransaction().begin();
	deleteQuery.executeUpdate();
	manager.getTransaction().commit();
    }
    
}
