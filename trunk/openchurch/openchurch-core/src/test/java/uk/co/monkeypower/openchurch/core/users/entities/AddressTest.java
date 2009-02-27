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


public class AddressTest {
    
private static DataSource dataSource;
    
    @BeforeClass
    public static void setUpJNDI() throws NamingException {	
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
    public void createAddress() throws Exception {
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
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
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	Address address = new Address();
	address.setFirstLine("test");
	manager.getTransaction().begin();
	manager.persist(address);
	manager.getTransaction().commit();
    }
    
    @Test(expected=AddressAttributeValidationException.class)
    public void createAddressWithInvalidPostCode() throws Exception{
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
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
	EntityManager manager = Persistence.createEntityManagerFactory("openchurch_users").createEntityManager();
	Query deleteQuery = manager.createQuery("delete from Address a where a.firstLine = 'test'");
	manager.getTransaction().begin();
	deleteQuery.executeUpdate();
	manager.getTransaction().commit();
    }
    
}
