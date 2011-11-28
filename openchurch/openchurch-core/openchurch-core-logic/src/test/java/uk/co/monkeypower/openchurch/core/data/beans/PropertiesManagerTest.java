package uk.co.monkeypower.openchurch.core.data.beans;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replayAll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.monkeypower.openchurch.core.users.beans.exceptions.PropertiesManagerException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PropertiesManagerBean.class )
public class PropertiesManagerTest {
	

	private void setupMockCreationForConstructor(InitialContext context,
			DataSource datasource, Connection connection, Statement stmt,
			ResultSet results) {
		try {
			expectNew(InitialContext.class).andReturn(context);
			expect(context.lookup("jdbc/openchurch")).andReturn(datasource);
			expect(datasource.getConnection()).andReturn(connection);
			expect(connection.createStatement()).andReturn(stmt);
			expect(stmt.executeQuery("select name,property_value from openchurch_properties")).andReturn(results);
			expect(results.next()).andReturn(true);
			expect(results.getString("name")).andReturn("Test Property 1");
			expect(results.getString("property_value")).andReturn("test");
			expect(results.next()).andReturn(true);
			expect(results.getString("name")).andReturn("Test Property 2");
			expect(results.getString("property_value")).andReturn("test");
			expect(results.next()).andReturn(false);
			expect(stmt.executeQuery("select name,property_value from openchurch_portal_properties")).andReturn(results);
			expect(results.next()).andReturn(true);
			expect(results.getString("name")).andReturn("Test Property 1");
			expect(results.getString("property_value")).andReturn("test");
			expect(results.next()).andReturn(true);
			expect(results.getString("name")).andReturn("Test Property 2");
			expect(results.getString("property_value")).andReturn("test");
			expect(results.next()).andReturn(false);
			connection.close();
		} catch (Exception e) {
			System.out.println("It didn't work: " + e);
		}
	}

	@Test
	public void createPropertiesManagerBean() {
		InitialContext context = createMock(InitialContext.class);
		DataSource datasource = createMock(DataSource.class);
		Connection connection = createMock(Connection.class);
		Statement stmt = createMock(Statement.class);
		ResultSet results = createMock(ResultSet.class);
		
		setupMockCreationForConstructor(context, datasource, connection, stmt,
				results);
		replayAll(context);	
		PropertiesManager props = null;
		try {
			props = new PropertiesManagerBean();
		} catch (PropertiesManagerException e) {
			e.printStackTrace();
			fail("Error thrown!");
		}
		assertNotNull(props);
		Properties openchurchProps = props.getOpenChurchProperties();
		assertNotNull(openchurchProps);
		assertTrue("test".equals(openchurchProps.getProperty("Test Property 1")));
		assertTrue("test".equals(openchurchProps.getProperty("Test Property 2")));
		assertTrue(openchurchProps.size() == 2);
		Properties portalProps = props.getPortalProperties();
		assertNotNull(portalProps);
		assertTrue("test".equals(portalProps.getProperty("Test Property 1")));
		assertTrue("test".equals(portalProps.getProperty("Test Property 2")));
		assertTrue(portalProps.size() == 2);
	}
	
	@Test
	public void testSetOpenChurchProperties() {
		InitialContext context = createMock(InitialContext.class);
		DataSource datasource = createMock(DataSource.class);
		Connection connection = createMock(Connection.class);
		Statement stmt = createMock(Statement.class);
		ResultSet results = createMock(ResultSet.class);
		String key = "test key";
		String value = "test value";
		setupMockCreationForConstructor(context, datasource, connection, stmt,
				results);
		try {
			expectNew(InitialContext.class).andReturn(context);
			expect(context.lookup("jdbc/openchurch")).andReturn(datasource);
			expect(datasource.getConnection()).andReturn(connection);
			expect(connection.createStatement()).andReturn(stmt);
			expect(stmt.executeUpdate("update openchurch_properties set property_value = "
							+ value + " where name = " + key)).andReturn(0);
			expect(stmt.executeUpdate("insert into openchurch_properties values " +
					"(" + key + "," + value + ")")).andReturn(1);
			connection.close();
		} catch (Exception e) {
			System.out.println("It didn't work: " + e);
			fail("Error thrown!");
		}
		replayAll(context);	
		PropertiesManager props = null;
		try {
			props = new PropertiesManagerBean();
		} catch (PropertiesManagerException e) {
			fail("Error thrown!");
			e.printStackTrace();
		}
		assertNotNull(props);
		try {
			props.setOpenChurchProperty(key, value);
		} catch (PropertiesManagerException e) {
			fail("Error thrown!");
			e.printStackTrace();
		}
	}


	@Test
	public void testSetPortletProperties() {
		InitialContext context = createMock(InitialContext.class);
		DataSource datasource = createMock(DataSource.class);
		Connection connection = createMock(Connection.class);
		Statement stmt = createMock(Statement.class);
		ResultSet results = createMock(ResultSet.class);
		String key = "test key";
		String value = "test value";
		setupMockCreationForConstructor(context, datasource, connection, stmt,
				results);
		try {
			expectNew(InitialContext.class).andReturn(context);
			expect(context.lookup("jdbc/openchurch")).andReturn(datasource);
			expect(datasource.getConnection()).andReturn(connection);
			expect(connection.createStatement()).andReturn(stmt);
			expect(stmt.executeUpdate("update openchurch_portal_properties set property_value = "
							+ value + " where name = " + key)).andReturn(0);
			expect(stmt.executeUpdate("insert into openchurch_portal_properties values " +
					"(" + key + "," + value + ")")).andReturn(1);
			connection.close();
		} catch (Exception e) {
			System.out.println("It didn't work: " + e);
			fail("Error thrown!");
		}
		replayAll(context);	
		PropertiesManager props = null;
		try {
			props = new PropertiesManagerBean();
		} catch (PropertiesManagerException e) {
			fail("Error thrown!");
		}
		assertNotNull(props);
		try {
			props.setPortalProperty(key, value);
		} catch (PropertiesManagerException e) {
			fail("Error thrown!");
		}
	}

}
