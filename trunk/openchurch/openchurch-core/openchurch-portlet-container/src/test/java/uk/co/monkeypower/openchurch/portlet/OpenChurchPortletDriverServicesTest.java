package uk.co.monkeypower.openchurch.portlet;

import static org.junit.Assert.*;

import org.junit.Test;

public class OpenChurchPortletDriverServicesTest {

	@Test
	public void create() {
		OpenChurchPortletDriverServices driverServices = new OpenChurchPortletDriverServices();
		driverServices.setPortalContext(new OpenChurchPortalContext());
		assertNotNull(driverServices);
		assertNotNull(driverServices.getPortalContext());
	}

}
