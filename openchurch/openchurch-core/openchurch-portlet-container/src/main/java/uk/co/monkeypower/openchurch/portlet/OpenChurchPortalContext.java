package uk.co.monkeypower.openchurch.portlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

public class OpenChurchPortalContext implements PortalContext {

	private List<PortletMode> portletModes = new ArrayList<PortletMode>();
	private List<WindowState> windowStates = new ArrayList<WindowState>();
	
	private Properties portalProperties = new Properties();
	
	public OpenChurchPortalContext() {
		portletModes.add(PortletMode.VIEW);
		portletModes.add(PortletMode.HELP);
		portletModes.add(PortletMode.EDIT);
		portletModes.add(new PortletMode("ABOUT"));
		
		windowStates.add(WindowState.MAXIMIZED);
		windowStates.add(WindowState.MINIMIZED);
		windowStates.add(WindowState.NORMAL);
	}
	
	public String getProperty(String name) {
		if (portalProperties.containsKey(name)) {
			return portalProperties.getProperty(name);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Enumeration<String> getPropertyNames() {
		return (Enumeration<String>) portalProperties.propertyNames();
	}

	public Enumeration<PortletMode> getSupportedPortletModes() {
		return Collections.enumeration(portletModes);
	}

	public Enumeration<WindowState> getSupportedWindowStates() {
		return Collections.enumeration(windowStates);
	}

	public String getPortalInfo() {
		return "openchurch";
	}

}
