package uk.co.monkeypower.openchurch.core.data.beans;
import java.util.Properties;

import javax.ejb.Local;

import uk.co.monkeypower.openchurch.core.users.beans.exceptions.PropertiesManagerException;

@Local
public interface PropertiesManager {
	
	Properties getOpenChurchProperties();
	Properties getPortalProperties();
	void setOpenChurchProperty(String name, String value) throws PropertiesManagerException;
	void setPortalProperty(String name, String value)throws PropertiesManagerException;

}
