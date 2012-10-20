package net.sf.robocode.battle.item;

import java.util.*;

import net.sf.robocode.battle.peer.*;

/**
 * Item controller. Controls the addition and removal of items from the 
 * battlefield.
 * 
 * @author Jacob Klein (Dream Team)
 * @author Ameer Sabri (Dream Team) (commenting only)
 */

public class ItemController {

	//Lists of items and robots on the current battlefield
	private List<ItemDrop> battleItems = new ArrayList<ItemDrop>();
	private List<RobotPeer> currentRobots = new ArrayList<RobotPeer>();
	
	//Boolean to enable displayed console output
	private boolean debugMessage = true;
	
	/**
	 * Updates the list of robots on the battlefield.
	 * 
	 * @param robots list of robots on the battlefield
	 */	
	public void updateRobots(List<RobotPeer> robots){
		currentRobots = robots;
	}
    /**
     * Constructor.
     */
    public ItemController() {
    }

	/**
	 * Attempts to spawn an item on the battlefield.
	 * 
	 * @param item item to be spawned
	 * @param x x-coordinate of the item
	 * @param y y-coordinate of the item
	 * @return {@code true} if the item was successfully spawned; {@code false} otherwise
	 */
	public boolean spawnItem(ItemDrop item, double x, double y) {
		if(item.addNewItem(currentRobots,battleItems,x,y)){
			battleItems.add(item);
			return true;
		}
		return false;
	}
	
	/**
	 * Calls item initialiser to attempt spawn of item at random spot on battlefield
	 * @param item item to be spawned
	 */
	
	public void spawnRandomItem(ItemDrop item){
		item.addRandomItem(currentRobots, battleItems);
		battleItems.add(item);
	}
	
	/**
	 * Removes the item from the current items on the battlefield
	 * @param item item to remove from the battlefield
	 */
	public void removeItem(ItemDrop item){
		if (battleItems.contains(item)){
			battleItems.remove(item);
		}
	}
	
	/**
	 * Gets the list of items currently on the battlefield.
	 * @return list of items currently on the battlefield
	 */
	public List<ItemDrop> getItems() {
		return battleItems;
	}

	
	/**
	 * Helper method to print messages to console if debugging is enabled.
	 */
	private void printf(String s) {
		if (debugMessage){
			System.out.println(s);
		}
	}

}
