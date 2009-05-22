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

public class MenuItemTest {

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
    public void createMenuItem() {
        MenuItem item = new MenuItem();
        item.setTitle("test menu item");
        manager.getTransaction().begin();
        manager.persist(item);
        assertTrue(item.getId() != 0);
        Page page = new Page();
        page.setTitle("test");
        page.setName("test");
        item.setPage(page);
        manager.persist(page);
        manager.merge(item);
        manager.getTransaction().commit();
    }

    @After
    public void cleanUpTestData() {
        EntityManager manager = Persistence.createEntityManagerFactory("openchurch_layout_test").createEntityManager();
        Query cleanUpQuery = manager.createQuery("delete from MenuItem m where m.title = 'test menu item'");
        cleanUpQuery.executeUpdate();
        cleanUpQuery = manager.createQuery("delete from Page p where p.name = 'test'");
        cleanUpQuery.executeUpdate();
    }

    
     @AfterClass 
     public static void closeManager(){ 
         manager.close(); 
     }
    

}
