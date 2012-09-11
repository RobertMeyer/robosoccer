package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

public class ItemMode extends ClassicMode {
	
	boolean setItems = false;

	List<String> items = new ArrayList<String>();
	
	public String toString(){
		return "Item Mode";
	}
	
	public String getDescription() {
		return "A mode with items and power-ups that robots can pickup and use.";
	}
	
	/**
	 * Get the items needed for the items
	 * @return the items needed
	 */
	public List<String> getItems() {
		if (!setItems){
			items.add("HealthPack");
			items.add("HealthPack");
			items.add("HealthPack");
			items.add("HealthPack");
			items.add("HealthPack");
			setItems = true;
		}
		return items;
	}
}
