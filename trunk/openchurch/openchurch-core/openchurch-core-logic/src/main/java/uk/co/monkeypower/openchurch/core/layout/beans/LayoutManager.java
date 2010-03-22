package uk.co.monkeypower.openchurch.core.layout.beans;

import java.util.List;

import javax.ejb.Local;

import uk.co.monkeypower.openchurch.core.layout.entities.Menu;

@Local
public interface LayoutManager {

	public List<Menu> getMenus();
	
	

}
