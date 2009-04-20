package uk.co.monkeypower.openchurch.core.layout.entities;

import java.util.ArrayList;
import java.util.List;

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


public class PageTest {
    
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
    public void createPage() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("openchurch_layout_test");
        EntityManager manager = factory.createEntityManager();
        Page page = new Page();
        page.setName("test");
        page.setTitle("test");
        manager.getTransaction().begin();
        manager.persist(page);
        manager.getTransaction().commit();
        assertTrue(page.getId() >=1);
        List<ContentModule> modules = new ArrayList<ContentModule>();
        ContentModule module = new ContentModule();
        module.setModuleClassName("test");
        module.setDeleteable(true);
        manager.getTransaction().begin();
        manager.persist(module);
        manager.getTransaction().commit();
        modules.add(module);
        page.setModules(modules);
        manager.getTransaction().begin();
        manager.merge(page);
        manager.getTransaction().commit();
        
    }

}
