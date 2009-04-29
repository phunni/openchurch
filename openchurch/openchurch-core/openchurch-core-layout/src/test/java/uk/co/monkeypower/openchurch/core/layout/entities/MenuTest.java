package uk.co.monkeypower.openchurch.core.layout.entities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import uk.co.monkeypower.openchurch.core.db.OpenChurchUtilityDatasourceForTesting;


public class MenuTest {
    
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
    public void createMenu() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("openchurch_layout_test");
        EntityManager manager = factory.createEntityManager();
        Menu menu = new Menu();
        menu.setName("test");
        manager.getTransaction().begin();
        manager.persist(menu);
        manager.getTransaction().commit();
        assertTrue(menu.getId() >= 1);
    }
    

}
