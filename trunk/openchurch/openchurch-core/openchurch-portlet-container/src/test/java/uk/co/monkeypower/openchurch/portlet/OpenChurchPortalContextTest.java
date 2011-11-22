package uk.co.monkeypower.openchurch.portlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.junit.Test;

public class OpenChurchPortalContextTest {
	
	@Test
	public void createPortalContext() {
		OpenChurchPortalContext context = new OpenChurchPortalContext();
		assertNotNull(context);
		Enumeration<PortletMode> modes = (Enumeration<PortletMode>) context.getSupportedPortletModes();
		int numberOfModes = 0;
		while (modes.hasMoreElements()) {
			numberOfModes++;
			modes.nextElement();
		}
		assertTrue(numberOfModes > 1);
		Enumeration<WindowState> states = (Enumeration<WindowState>) context.getSupportedWindowStates();
		int numberOfStates = 0;
		while (states.hasMoreElements()) {
			numberOfStates++;
			states.nextElement();
		}
		assertTrue(numberOfStates > 1);
	}

}
