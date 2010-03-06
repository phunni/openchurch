package uk.co.monkeypower.openchurch.core.web.servlets;

import java.io.IOException;
import java.util.HashMap;
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
		LOG.info("doPost in the core servlet");
		UserManager userManager = null;
		try {
			InitialContext ctx = new InitialContext();
			userManager = (UserManager) ctx.lookup(JNDINames.USER_MANAGER);
		} catch (NamingException e) {
			throw new ServletException("Failed to lookup the UserManager EJB",
					e);
		}
		HttpSession session = request.getSession();
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
		
		session.setAttribute("model", model);
		
		//select the view
		String action = request.getParameter("action");
		String viewURL = "/WEB-INF/jsp/index.jsp";
		if (action == null) {
			//do nothing we want the default
		} else if (action.equals("login")) {
			viewURL = "/WEB-INF/jsp/login.jsp";
		}
		
		
		//invoke the view
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewURL);
		dispatcher.forward(request, response);
		
	}

}
