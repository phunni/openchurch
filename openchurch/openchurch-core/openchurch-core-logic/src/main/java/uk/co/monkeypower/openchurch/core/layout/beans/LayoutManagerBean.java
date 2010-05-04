package uk.co.monkeypower.openchurch.core.layout.beans;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.co.monkeypower.openchurch.core.layout.beans.utils.MenuComparator;
import uk.co.monkeypower.openchurch.core.layout.entities.Menu;
import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

@Stateful
public class LayoutManagerBean implements LayoutManager {
	
	private final Log LOG = LogFactory.getLog(LayoutManagerBean.class);


	
	public List<Menu> getMenus(User currentUser) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Current User is: " + currentUser.getUsername());
		}
		List<Role> roles = currentUser.getRoles();
		Vector<Menu> menus = new Vector<Menu>();
		for (Role currentRole : roles) {
			menus.addAll(currentRole.getMenus());
		}
		menus.trimToSize();
		Collections.sort(menus, new MenuComparator());
		if (LOG.isDebugEnabled()) {
			LOG.debug("The number of returned menus was: " + menus.size());
			LOG.debug("The returned menus are: ");
			for (Menu m : menus) {
				LOG.debug(m.getName() + " : " + m.getPriority());
			}
		}
		return menus;
	}

}
 