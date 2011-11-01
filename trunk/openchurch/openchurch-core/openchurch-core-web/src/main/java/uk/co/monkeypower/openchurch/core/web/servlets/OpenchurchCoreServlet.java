package uk.co.monkeypower.openchurch.core.web.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uk.co.monkeypower.openchurch.core.layout.beans.LayoutManager;
import uk.co.monkeypower.openchurch.core.layout.entities.Menu;
import uk.co.monkeypower.openchurch.core.layout.entities.MenuItem;
import uk.co.monkeypower.openchurch.core.layout.entities.Page;
import uk.co.monkeypower.openchurch.core.users.beans.UserManager;
import uk.co.monkeypower.openchurch.core.users.beans.exceptions.UserManagementException;
import uk.co.monkeypower.openchurch.core.users.entities.User;
import uk.co.monkeypower.openchurch.core.web.utils.JNDINames;

public class OpenchurchCoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(OpenchurchCoreServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//Get the User object
		if (LOG.isDebugEnabled()) {
			LOG.debug("doPost in the core servlet");
		}
		HttpSession session = request.getSession();
		String command = request.getParameter("command");
		if (LOG.isDebugEnabled()) {
			LOG.debug("Command is: " + command);
		}
		if ("logout".equals(command)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Logging the user out.");
			}
			
			session.invalidate();
			session = request.getSession();
		}
		UserManager userManager = null;
		try {
			InitialContext ctx = new InitialContext();
			userManager = (UserManager) ctx.lookup(JNDINames.USER_MANAGER);
		} catch (NamingException e) {
			throw new ServletException("Failed to lookup the UserManager EJB",
					e);
		}
		User currentUser = (User) session.getAttribute("currentUser"); 
		if (currentUser == null) {
			try {
				currentUser = userManager.getUser("guest");
			} catch (UserManagementException e) {
				throw new ServletException("FATAL: guest user not found in db",
						e);
			}
			session.setAttribute("currentUser", currentUser);
		}
		
		//Begin to create the model
		Map<String, Object> model = new HashMap<String, Object>();
		
		//create the layout
		LayoutManager layoutManager = null;
		try {
			InitialContext ctx = new InitialContext();
			layoutManager = (LayoutManager) ctx.lookup(JNDINames.LAYOUT_MANAGER);
		} catch (NamingException e) {
			throw new ServletException("Failed to lookup the LayoutManager EJB",
					e);
		}
		List<Menu> menus = layoutManager.getMenus(currentUser);
		model.put("layout", menus);
		
		//The reason for the slightly inaccurate parameter name is simple
		//obfuscation.
		String pageID = request.getParameter("page");
		Page page = null;
		if (pageID != null) {
			pageID = pageID.trim();
			long menutItemID = Long.parseLong(pageID);
			for (Menu currentMenu : menus) {
				for (MenuItem currentMenuItem : currentMenu.getItems()) {
					if (currentMenuItem.getId() == menutItemID){
						page = currentMenuItem.getPage();
					}
				}
			}
			if (LOG.isDebugEnabled()){
				LOG.debug("The current page is: " + page.getName());
			}
			model.put("page", page);
		}
		
		//TODO: We can do this better, we probably don't need to recreate the 
		//entire model every request.
		request.setAttribute("model", model);
		
		//select the view
		String action = request.getParameter("action");
		String viewURL = "/WEB-INF/jsp/index.jsp";
		if (action == null) {
			//do nothing we want the default
		} 
		
		
		//invoke the view
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewURL);
		dispatcher.forward(request, response);
		
	}

}
