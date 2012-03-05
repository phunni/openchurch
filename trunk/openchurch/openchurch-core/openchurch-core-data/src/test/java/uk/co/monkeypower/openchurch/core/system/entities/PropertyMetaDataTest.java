package uk.co.monkeypower.openchurch.core.system.entities;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.monkeypower.openchurch.core.users.entities.RoleTest;

public class PropertyMetaDataTest {

	
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
	public void createPropertyMetaData() {
		fail("Not yet implemented");
	}

}
