package uk.co.monkeypower.openchurch.core.web.servlets;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import uk.co.monkeypower.openchurch.core.users.beans.UserManager;
import uk.co.monkeypower.openchurch.core.users.entities.User;
import uk.co.monkeypower.openchurch.core.web.utils.JNDINames;

public class OpenchurchCoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//private Log LOG = LogFactory.getLog(OpenchurchCoreServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserManager userManager = null;
		try {
			InitialContext ctx = new InitialContext();
			userManager = (UserManager) ctx.lookup(JNDINames.USER_MANAGER);
		} catch (NamingException e) {
			throw new ServletException("Failed to lookup the UserManager EJB", e);
		}
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser"); 
		if (currentUser == null) {
			currentUser = userManager.getUser("guest");
			session.setAttribute("currentUser", currentUser);
		}
		ServletOutputStream out = response.getOutputStream();
		out.println("Welcome " + currentUser.getPreferredNames() + " "
				+ currentUser.getSurname());
	}

}
