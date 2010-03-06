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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LOG.info("The username is: " + username);
		
		HttpSession session = request.getSession();
		//Get the User object
		UserManager userManager = null;
		try {
			InitialContext ctx = new InitialContext();
			userManager = (UserManager) ctx.lookup(JNDINames.USER_MANAGER);
		} catch (NamingException e) {
			throw new ServletException("Failed to lookup the UserManager EJB",
					e);
		}
		User currentUser = null;
		try {
			currentUser = userManager.getUser(username);
		} catch (UserManagementException e) {
			//if there is no user for this username thewn return the current
			//user - which should be guest
			currentUser = (User) session.getAttribute("currentUser");
		}
		currentUser.authenticate(password);
		String redirectURL = "";
		if (currentUser.isAuthenticated()) {
			session.setAttribute("currentUser", currentUser);
		} else {
			LOG.info("Invalid username or password");
			request.setAttribute("errorText", "Invalid username or password");
			redirectURL = "/openchurch/?action=login";
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(redirectURL);
		dispatcher.forward(request, response);
	}
	
	

}
