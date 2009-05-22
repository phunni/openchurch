package uk.co.monkeypower.openchurch.core.db;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;


/***
 * Simple implementation to provide a datasource to facilitate unit testing
 * @author p-hunnisett
 * 
 */
public class OpenChurchUtilityDatasourceForTesting implements DataSource {

    public Connection getConnection() throws SQLException {
	Properties props = new Properties();
	try {
	    props.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
	} catch (IOException e) {
	    System.out.println("Failed to open database connection: " + e);
	    throw new SQLException("Failed to locate properties file for datasource");
	}
	try {
	    Class.forName(props.getProperty("jdbc_driver_class"));
	} catch (ClassNotFoundException e) {
	    System.out.println("Failed to open database connection: " + e);
	    throw new SQLException("Failed to locate driver class for datasource");
	}
	String url = props.getProperty("jdbc_url");
	String username = props.getProperty("jdbc_username");
	String password = props.getProperty("jdbc_password");
	System.out.println("**** User: " + username + "\n URL: " + url);
	Connection conn = DriverManager.getConnection(url, username, password);
	return conn;	
    }

    public Connection getConnection(String username, String password) throws SQLException {
	return getConnection();
    }

    public PrintWriter getLogWriter() throws SQLException {
	return null;
    }

    public int getLoginTimeout() throws SQLException {
	return 0;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
    }

    public void setLoginTimeout(int seconds) throws SQLException {
    }

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

}
