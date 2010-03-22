package uk.co.monkeypower.openchurch.core.layout.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.co.monkeypower.openchurch.core.layout.entities.Menu;
import uk.co.monkeypower.openchurch.core.users.beans.UserManager;
import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

@Stateful
public class LayoutManagerBean implements LayoutManager {
	
	private final Log LOG = LogFactory.getLog(LayoutManagerBean.class);
	
	@EJB
	private UserManager userManager;
	
	public List<Menu> getMenus() {
		User currentUser = userManager.getUser();
		List<Role> roles = currentUser.getRoles();
		
		
		return null;
	}

}
