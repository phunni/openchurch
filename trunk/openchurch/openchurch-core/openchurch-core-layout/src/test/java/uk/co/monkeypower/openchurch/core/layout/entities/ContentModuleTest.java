package uk.co.monkeypower.openchurch.core.layout.entities;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



public class ContentModuleTest {
	
    private static EntityManager manager;

    @BeforeClass
    public static void setUpJNDI() {
        Properties jdbcProperties = new Properties();
        try {
            jdbcProperties.load(MenuItem.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            System.out.println("Failed to locate properties file for datasource: " + e);
        }
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("openchurch_layout_test", jdbcProperties);
        manager = factory.createEntityManager();

    }

    @Test
    public void createContentModule() {
        ContentModule module = new ContentModule();
        module.setDeleteable(false);
        module.setModuleClassName("test");
        manager.getTransaction().begin();
        manager.persist(module);
        manager.getTransaction().commit();
        assertTrue(module.getId() != 0);
        manager.close();
    }

    @After
    public void cleanUpTestData() {
        EntityManager manager = Persistence.createEntityManagerFactory("openchurch_layout_test").createEntityManager();
        Query cleanUpQuery = manager.createQuery("delete from ContentModule c where c.moduleClassName = 'test'");
        cleanUpQuery.executeUpdate();
        manager.close();
    }
    
    @AfterClass 
    public static void closeManager(){ 
        manager.close(); 
    }

}
