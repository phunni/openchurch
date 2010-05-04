package uk.co.monkeypower.openchurch.core.layout.beans;

import java.util.List;

import javax.ejb.Local;

import uk.co.monkeypower.openchurch.core.layout.entities.Menu;
import uk.co.monkeypower.openchurch.core.users.entities.User;

@Local
public interface LayoutManager {

	public List<Menu> getMenus(User user);

}
