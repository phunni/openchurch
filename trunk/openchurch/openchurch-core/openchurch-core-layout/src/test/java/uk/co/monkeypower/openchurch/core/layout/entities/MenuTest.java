package uk.co.monkeypower.openchurch.core.layout.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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


public class MenuTest {
    
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
    public void createMenu() {
        Menu menu = new Menu();
        menu.setName("test");
        manager.getTransaction().begin();
        manager.persist(menu);
        manager.getTransaction().commit();
        assertTrue(menu.getId() >= 1);
        MenuItem firstItem = new MenuItem();
        firstItem.setTitle("test");
        MenuItem secondItem = new MenuItem();
        secondItem.setTitle("test");
        MenuItem thirdItem = new MenuItem();
        thirdItem.setTitle("test");
        manager.getTransaction().begin();
        manager.persist(firstItem);
        manager.persist(secondItem);
        manager.persist(thirdItem);
        manager.getTransaction().commit();
        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);
        menu.setItems(items);
        manager.getTransaction().begin();
        manager.merge(menu);
        manager.getTransaction().commit();
        manager.close();
    }
    
    @After
    public void cleanUp(){
        Query cleanUpMenuItemsQuery = manager.createQuery("select m from MenuItem m where m.title = 'test'");
        manager.getTransaction().begin();
        List<MenuItem> menuItems = cleanUpMenuItemsQuery.getResultList();
        manager.getTransaction().commit();
        for (MenuItem current : menuItems) {
            manager.getTransaction().begin();
            manager.remove(current);
            manager.getTransaction().commit();
        }
        Query cleanUpMenusQuery = manager.createQuery("delete from Menu m where m.name = 'test'");
        cleanUpMenusQuery.executeUpdate();
        manager.close();
    }
    
    @AfterClass 
    public static void closeManager(){ 
        manager.close(); 
    }
}
