package uk.co.monkeypower.openchurch.core.layout.entities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import uk.co.monkeypower.openchurch.core.db.OpenChurchUtilityDatasourceForTesting;

public class MenuItemTest {
    
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
	public void createMenuItem() {
	    EntityManagerFactory factory = Persistence.createEntityManagerFactory("openchurch_layout_test");
	    EntityManager manager = factory.createEntityManager();
	    MenuItem item = new MenuItem();
	    item.setTitle("test menu item");
	    manager.getTransaction().begin();
	    manager.persist(item);
	    manager.getTransaction().commit();
	    assertTrue(item.getId() != 0);
	    ContentModule module = new ContentModule();
	    module.setModuleClassName("test");
	    module.setDeleteable(false);
	    manager.getTransaction().begin();
	    manager.persist(module);
	    manager.getTransaction().commit();
	    item.setModule(module);
	    manager.getTransaction().begin();
	    manager.merge(item);
	    manager.getTransaction().commit();	    
	}
	
	@After
	public void cleanUpTestData() {
		EntityManager manager = Persistence.createEntityManagerFactory("openchurch_layout_test").createEntityManager();
		Query cleanUpQuery = manager.createQuery("delete from MenuItem m where m.title = 'test menu item'");
		cleanUpQuery.executeUpdate();
		cleanUpQuery = manager.createQuery("delete from ContentModule c where c.moduleClassName = 'test'");
		cleanUpQuery.executeUpdate();
	}

}
