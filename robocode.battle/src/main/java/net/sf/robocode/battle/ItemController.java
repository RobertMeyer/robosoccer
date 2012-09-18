package net.sf.robocode.battle;

import java.util.*;
import net.sf.robocode.battle.peer.*;

/**
 * Item controller
 * 
 * @author Jacob Klein - Dream Team
 * 
 */

public class ItemController {

	//Lists of current battlefield
	private List<ItemDrop> battleItems = new ArrayList<ItemDrop>();
	private List<RobotPeer> currentRobots = new ArrayList<RobotPeer>();
	
	//Boolean to display console output
	private boolean debugMessage = true;
	/**
	 * Updates robot list used for collision detection
	 * @param robots - List of robots on battlefield
	 */	
	public void updateRobots(List<RobotPeer> robots){
		currentRobots = robots;
	}
    /**
     * Constructor
     */
    public ItemController() {
    }

	/**
	 * Calls item initialiser to attempt spawn of item
	 * @param item - Item to be spawned
	 * @param x - x coordinate to attempt spawn
	 * @param y - y coordinate to attempt spawn
	 * @return boolean if spawn was successful
	 */
	public boolean spawnItem(ItemDrop item, double x, double y) {
		if(item.initialiseNewItem(currentRobots,battleItems,x,y)){
			battleItems.add(item);
			return true;
		}
		return false;
	}
	
	/**
	 * Calls item initialiser to attempt spawn of item at random spot on battlefield
	 * @param item - Item to be spawned
	 */
	
	public void spawnRandomItem(ItemDrop item){
		item.initialiseRoundItems(currentRobots, battleItems);
		battleItems.add(item);
	}
	
	/**
	 * Removes item from current battlefield items
	 * @param item - item to remove;
	 */
	public void removeItem(ItemDrop item){
		if (battleItems.contains(item)){
			battleItems.remove(item);
		}
	}
	
	/**
	 * Public method to access current items on the battlefield
	 * @return List of current items on the battlefield
	 */
	public List<ItemDrop> getItems() {
		return battleItems;
	}

	
	/**
	 * Helper method to print messages to console if debugMessage = true
	 */
	private void printf(String s) {
		if (debugMessage){
			System.out.println(s);
		}
	}

}
