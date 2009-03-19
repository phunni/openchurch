package uk.co.monkeypower.openchurch.core.web.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.monkeypower.openchurch.core.users.beans.UserManager;
import uk.co.monkeypower.openchurch.core.users.entities.User;
import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;


public class TestUserSetup extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private UserManager userManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	ServletOutputStream out = response.getOutputStream();
	response.setContentType("text/html");
	if( userManager == null) {
	    out.println("Couldn't find the frickin' bean...");
	    return;
	}
	User user = new User();
	user.setUsername("testing");
	user.setPreferredNames("test test");
	user.setPassword("test-password");
	user.setSurname("Smith");
	try {
	    user.setEmailAddress("a@.b.com");
	} catch (UserAttributeValidationException e) {
	    out.println("Failed to set the email address");
	    return;
	}
	userManager.setUser(user);
	userManager.createUser();
	out.println("Succesfully created and persisted the user<br />");
	userManager.removeUser();
	out.println("Succesfully removed the user...");
    }
    
    

}
