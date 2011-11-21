package uk.co.monkeypower.openchurch.core.layout.beans;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LayoutManagerTest {

	private static EJBContainer ejbContainer;
	private static Context ctx;

	@BeforeClass
	public static void setUp() {
		ejbContainer = EJBContainer.createEJBContainer();
		ctx = ejbContainer.getContext();
	}

	@AfterClass
	public static void tearDown() {
		ejbContainer.close();
	}

	@Test
	public void getMenus() {
		LayoutManager manager = null;
		try {
			manager = (LayoutManager) ctx.lookup("java:global/classes/LayoutManagerBean!uk.co.monkeypower.openchurch.core.layout.beans.LayoutManager");
		} catch (NamingException e) {
			System.out.println("Failed to lookup the gosh darned bean!");
		}
		assertNotNull(manager);
		//Menu[] menus = manager.getMenus();
		//assertTrue(menus.length > 1);
	}

}
