package uk.co.monkeypower.openchurch.core.web.servlets;

import java.io.IOException;

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

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(LoginServlet.class);
	
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("The username is: " + username);
		}
		
		String viewURL = "/WEB-INF/jsp/login.jsp";
		
		if (username == null && password == null) {
			//do nothing - just display the form
		} else if (username == null || password == null) {
			request.setAttribute("errorText", "Invalid username or password");
		} else {
			UserManager userManager;
			try {
				InitialContext ctx = new InitialContext();
				userManager = (UserManager) ctx
						.lookup(JNDINames.USER_MANAGER);
			} catch (NamingException e) {
				throw new ServletException(
						"Failed to lookup the UserManager EJB", e);
			}
			User currentUser;
			try {
				currentUser = userManager.getUser(username);
			} catch (UserManagementException e) {
				//Should return the guest user
				currentUser = (User) session.getAttribute("currentUser");
			}
			currentUser.authenticate(password);
			if (currentUser.isAuthenticated()) {
				session.setAttribute("currentUser", currentUser);
				userManager.setUser(currentUser);
				response.sendRedirect("/openchurch/");
				return;
			} else {
				request.setAttribute("errorText", "Invalid username or password");
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewURL);
		dispatcher.forward(request, response);
	}
	
	

}
