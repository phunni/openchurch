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



public class PageTest {
    
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
    public void createPage() {
        Page page = new Page();
        page.setName("test");
        page.setTitle("test");
        manager.getTransaction().begin();
        manager.persist(page);
        assertTrue(page.getId() >=1);
        List<ContentModule> modules = new ArrayList<ContentModule>();
        ContentModule module = new ContentModule();
        module.setModuleClassName("test");
        module.setDeleteable(true);
        manager.persist(module);
        modules.add(module);
        page.setModules(modules);
        manager.merge(page);
        manager.getTransaction().commit();  
    }
    
   @After
    public void cleanUp(){
        Query cleanUpPagesQuery = manager.createQuery("delete from Page p where p.title = 'test'");
        cleanUpPagesQuery.executeUpdate();
        Query cleanUpModulesQuery = manager.createQuery("delete from ContentModule c where c.moduleClassName = 'test'");
        cleanUpModulesQuery.executeUpdate();
    }
    
    @AfterClass 
    public static void closeManager(){ 
        manager.close(); 
    }

}
