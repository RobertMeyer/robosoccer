package net.sf.robocode.battle;

import java.util.List;
import java.util.Random;

import robocode.control.RandomFactory;
import net.sf.robocode.battle.peer.*;
import net.sf.robocode.battle.*;
import robocode.*;

/**
 * Abstract class for item/powerup drops
 * 
 * @author s4238358
 *
 */


public abstract	class ItemDrop {


	private double xLocation;
	private double yLocation;
	private final static double width = 40; //Same width and height as robots
	private final static double height =40;
	private boolean isDestroyable;
	private int lifespan;
	private double health;
	private boolean isEquippable;
	private BattleRules battleRules;
	private final BoundingRectangle boundingBox;
	
	
	ItemDrop(boolean isDestroyable, int lifespan, double health, boolean isEquippable, Battle battle){
		this.isDestroyable = isDestroyable;
		this.lifespan = lifespan;
		this.health = health;
		this.isEquippable = isEquippable;
		System.out.println("Item made");
		this.boundingBox = new BoundingRectangle(xLocation, yLocation, width, height);
		this.battleRules = battle.getBattleRules();
	}

	/**
	 * Returns the bounding box of the item
	 * @return a bounding box representing the location of the item
	 */
	public BoundingRectangle getBoundingBox(){
		return boundingBox;
	}

	/**
	 * Set the bounding box with the current x & y coordinates
	 */
	public void setBoundingBox() {
		boundingBox.setRect(xLocation - width / 2 + 2, yLocation - height / 2 + 2, width - 4, height - 4);
	}
	
	/**
	 * Returns the current x-coordinate of the item
	 * @return a double representing the x-coordinate of the item
	 */
	public double getXLocation() {
		return xLocation;
	}
	
	/**
	 * Sets a new x-coordinate of the item
	 * @param a double representing the new x-coordinate of the item
	 */
	public void setXLocation(double xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * Returns the current y-coordinate of the item
	 * @return a double representing the y-coordinate of the item
	 */
	public double getYLocation() {
		return yLocation;
	}

	/**
	 * Sets a new y-coordinate of the item
	 * @param a double representing the new y-coordinate of the item
	 */
	public void setYLocation(double yLocation) {
		this.yLocation = yLocation;
	}

	/**
	 * Returns the width of the item
	 * @return a double representing the width of the item
	 */
	public double getWidth(){
		return width;
	}
	
	/**
	 * Returns the height of the item
	 * @return a double representing the height of the item
	 */
	public double getHeight(){
		return height;
	}
	
	/**
	 * Returns whether the item is destroyable or not.
	 * This will allow the itemDrop class to expand to things like extra walls, traps, etc
	 * @return a boolean representing whether the item is destroyable or not
	 */
	public boolean getIsDestroyable(){
		return isDestroyable;
	}
	
	/**
	 * Sets if the item isDestroyable or not.
	 * @param a boolean representing whether the item is destroyable or not
	 */
	public void setIsDestroyable(boolean isDestroyable){
		this.isDestroyable = isDestroyable;
	}
	
	/**
	 * Returns the number of turns the item will be valid for
	 * @return an integer representing the nummber of turns the item is valid for
	 */
	public int getLifespan(){
		return lifespan;
	}
	
	/**
	 * Sets the number of turns the item will be valid for
	 * @param an integer that represents the lifespan of the item
	 */
	public void setLifespan(int lifespan){
		this.lifespan = lifespan;
	}
	
	/**
	 * Returns the health of the item. If the health is 0 or below, then it will be destroyed when
	 * a robot collides with the item.
	 * @return a double representing the health of the item
	 */
	public double getHealth(){
		return health;
	}
	
	/**
	 * Set the health of an item
	 * @param a double representing the new health of the item
	 */
	public void setHealth(double health){
		this.health = health;
	}

	public boolean getIsEquippable(){
		return isEquippable;
	}
	
	public void setIsEquippable(boolean isEquippable){
		this.isEquippable = isEquippable;
	}
	
	/**
	 * The effect the item has on the robot. Must be overridden to have any effect.
	 */
	public void doItemEffect(){
	}
	
	public void initialiseRoundItems(List<RobotPeer> robots, List<ItemDrop> items){
		boolean valid = false;
		
		if (!valid) {
			final Random random = RandomFactory.getRandom();
			for (int j = 0; j < 1000; j++) {
				xLocation = ItemDrop.width + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * ItemDrop.width);
				yLocation = ItemDrop.height + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * ItemDrop.height);
				setBoundingBox();

				if (validSpotRobot(robots)) {
					if (validSpotItem(items)){
						break;
					}
				}
			}
		}
	}
	
	public boolean initialiseNewItem(List<RobotPeer> robots, List<ItemDrop> items, double x, double y){
		xLocation = x;
		yLocation = y;
		if (validSpotRobot(robots)) {
			if (validSpotItem(items)){
				setBoundingBox();
				return true;
			}
			else{
				return false;
			}
		}
		else {
			return false;
		}
	} 
	
	private boolean validSpotRobot(List<RobotPeer> robots) {
		for (RobotPeer otherRobot : robots) {
			if (otherRobot != null) {
				if (getBoundingBox().intersects(otherRobot.getBoundingBox())) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean validSpotItem(List<ItemDrop> items) {
		for (ItemDrop otherItems : items) {
			if (otherItems != null) {
				if (getBoundingBox().intersects(otherItems.getBoundingBox())) {
					return false;
				}
			}
		}
		return true;
	}
	

}
