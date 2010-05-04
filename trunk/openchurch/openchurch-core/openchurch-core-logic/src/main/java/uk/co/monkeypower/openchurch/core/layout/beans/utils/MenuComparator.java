package uk.co.monkeypower.openchurch.core.layout.beans.utils;

import java.util.Comparator;

import uk.co.monkeypower.openchurch.core.layout.entities.Menu;

public class MenuComparator implements Comparator<Menu> {

	public int compare(Menu m1, Menu m2) {
		int firstPriority = m1.getPriority();
		int secondPriority = m2.getPriority();
		if (firstPriority < secondPriority) {
			return -1;
		} else if (firstPriority == secondPriority) {
			return 0;
		} else {
			return 1;
		}
	}

}
