package uk.co.monkeypower.openchurch.core.data.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import uk.co.monkeypower.openchurch.core.users.beans.exceptions.PropertiesManagerException;

/**
 * Session Bean implementation class PropertiesManagerBean
 */
@Stateless(mappedName = "propertiesManager")
public class PropertiesManagerBean implements PropertiesManager {
	
	private static final Logger LOG = Logger.getLogger(PropertiesManagerBean.class);

    private Properties openchurchProperties;
    private Properties portalProperties;
	
	/**
     * Default constructor. 
	 * @throws PropertiesManagerException 
     */
    public PropertiesManagerBean() throws PropertiesManagerException {
    	Connection connection = null;
        try {
			InitialContext context = new InitialContext();
			DataSource datasource = (DataSource) context.lookup("jdbc/openchurch");
			connection = datasource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery("select name,property_value from openchurch_properties");
			openchurchProperties = new Properties();
			while (results.next()) {
				String key = results.getString("name");
				String value = results.getString("property_value");
				openchurchProperties.setProperty(key, value);
			}
			
			results = stmt.executeQuery("select name,property_value from openchurch_portal_properties");
			portalProperties = new Properties();
			while (results.next()) {
				String key = results.getString("name");
				String value = results.getString("property_value");
				portalProperties.setProperty(key, value);
			}
			
		} catch (NamingException e) {
			throw new PropertiesManagerException("Failed to lookup the Datasource", e);
		} catch (SQLException e) {
			throw new PropertiesManagerException("Failed to get the properties from the database", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LOG.error("Failed to close db connection", e);
				}
			}
		}
    }

	@Override
	public Properties getOpenChurchProperties() {
		return openchurchProperties;
	}

	@Override
	public Properties getPortalProperties() {
		return portalProperties;
	}

	@Override
	public void setOpenChurchProperty(String name, String value) throws PropertiesManagerException {
		setProperty(name, value, "openchurch_properties");
	}

	private void setProperty(String name, String value, String tableName)
			throws PropertiesManagerException {
		final String insertSQL = "insert into " + tableName + " values (" + name + "," + value + ")";
		final String updateSQL = "update " + tableName + " set property_value = " + value + " where name = " + name;
		if (LOG.isDebugEnabled()) {
			LOG.debug("insert SQl is: " + insertSQL);
			LOG.debug("Update SQL is: " + updateSQL);
		}
		Connection connection = null;
		try {
			InitialContext context = new InitialContext();
			DataSource datasource = (DataSource) context.lookup("jdbc/openchurch");
			connection = datasource.getConnection();
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate(updateSQL);
			if (rows == 0) {
				rows = stmt.executeUpdate(insertSQL);
			}
		} catch (NamingException e) {
			throw new PropertiesManagerException("Failed to lookup the Datasource", e);
		} catch (SQLException e) {
			throw new PropertiesManagerException("Failed to set the properties from the database", e);
		} finally {
			
		}
	}

	@Override
	public void setPortalProperty(String name, String value) throws PropertiesManagerException {	
		setProperty(name, value, "openchurch_portal_properties");
	}

}
