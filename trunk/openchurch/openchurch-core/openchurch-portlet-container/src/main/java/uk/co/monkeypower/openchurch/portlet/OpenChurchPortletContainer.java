package uk.co.monkeypower.openchurch.portlet;

import java.io.IOException;

import javax.portlet.Event;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pluto.container.ContainerServices;
import org.apache.pluto.container.PortletContainer;
import org.apache.pluto.container.PortletContainerException;
import org.apache.pluto.container.PortletWindow;

public class OpenChurchPortletContainer implements PortletContainer {

	public void init() throws PortletContainerException {
		// TODO Auto-generated method stub

	}

	public void destroy() throws PortletContainerException {
		// TODO Auto-generated method stub

	}

	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContainerServices getContainerServices() {
		// TODO Auto-generated method stub
		return null;
	}

	public void doRender(PortletWindow portletWindow,
			HttpServletRequest request, HttpServletResponse response)
			throws PortletException, IOException, PortletContainerException {
		// TODO Auto-generated method stub
		
	}

	public void doServeResource(PortletWindow portletWindow,
			HttpServletRequest request, HttpServletResponse response)
			throws PortletException, IOException, PortletContainerException {
		// TODO Auto-generated method stub
		
	}

	public void doAction(PortletWindow portletWindow,
			HttpServletRequest request, HttpServletResponse response)
			throws PortletException, IOException, PortletContainerException {
		// TODO Auto-generated method stub
		
	}

	public void doLoad(PortletWindow portletWindow,
			HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws PortletException,
			IOException, PortletContainerException {
		// TODO Auto-generated method stub
		
	}

	public void doAdmin(PortletWindow portletWindow,
			HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws PortletException,
			IOException, PortletContainerException {
		// TODO Auto-generated method stub
		
	}

	public void doEvent(PortletWindow portletWindow,
			HttpServletRequest request, HttpServletResponse response,
			Event event) throws PortletException, IOException,
			PortletContainerException {
		// TODO Auto-generated method stub
		
	}

}
