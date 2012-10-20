package net.sf.robocode.battle.item;

import java.util.List;
import java.util.Random;

import robocode.control.RandomFactory;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.*;
import net.sf.robocode.mode.IMode;
import robocode.*;
import net.sf.robocode.battle.RenderObject;

/**
 * Abstract class for item/powerup drops
 * 
 * Original:
 * @author s4238358 (Dream Team)
 * 
 * Contributors:
 * @author Brandon Warwick (Team-Telos)
 * @author Ameer Sabri (Dream Team) (documentation only)
 */

public abstract	class ItemDrop {
	
	//Set the location of the item outside the battlefield so it is not apart of the battle until it is spawned
	private double xLocation = -50;
	private double yLocation = -50;

	//Same width and height as robots
	private final static double width = 40; 
	private final static double height = 40;

	protected boolean isDestroyable;
	protected int lifespan;
	protected double health;
	protected boolean isEquippable;
	protected BattleRules battleRules;
	protected Battle battle;
	protected final BoundingRectangle boundingBox;
	public String name;
	protected String imageName;
	
	/**
	 * Constructor for the ItemDrop class.
	 * 
	 * @param isDestroyable the destroyability of the item
	 * @param lifespan the lifespan of the item
	 * @param health the health of the item
	 * @param isEquippable the equippability of the item
	 * @param battle the Battle that the item is being added to
	 */
	public ItemDrop(boolean isDestroyable, int lifespan, double health, boolean isEquippable, Battle battle){
		this.isDestroyable = isDestroyable;
		this.lifespan = lifespan;
		this.health = health;
		this.isEquippable = isEquippable;
		this.boundingBox = new BoundingRectangle(xLocation, yLocation, width, height);
		this.battle = battle;
		this.battleRules = this.battle.getBattleRules();		
	}

	/**
	 * Returns the name of the item.
	 * @return the name of the item
	 */
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the bounding box of the item.
	 * @return a bounding box representing the location of the item
	 */
	public BoundingRectangle getBoundingBox(){
		return boundingBox;
	}

	/**
	 * Set the bounding box of the item, with the current x and y coordinates.
	 */
	public void setBoundingBox() {
		this.boundingBox.setRect(xLocation - width / 2 + 2, yLocation - height / 2 + 2, width - 4, height - 4);
	}
	
	/**
	 * Returns the current x-coordinate of the item.
	 * @return the x-coordinate of the item
	 */
	public double getXLocation() {
		return xLocation;
	}
	
	
	/**
	 * Sets a new x-coordinate of the item.
	 * @param the new x-coordinate of the item
	 */
	public void setXLocation(double xLocation) {
		this.xLocation = xLocation;
		setBoundingBox();
	}

	/**
	 * Returns the current y-coordinate of the item.
	 * @return the y-coordinate of the item
	 */
	public double getYLocation() {
		return yLocation;
	}

	/**
	 * Sets a new y-coordinate of the item.
	 * @param the new y-coordinate of the item
	 */
	public void setYLocation(double yLocation) {
		this.yLocation = yLocation;
		setBoundingBox();
	}
	
	/**
	 * Update the location of the item to a random x-coordinate and y-coordinate.
	 * @author - Brandon Warwick (team-Telos)
	 */
	public void updateToRandomLocation() {
		final Random random = RandomFactory.getRandom();
		
		this.setXLocation(ItemDrop.width + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * ItemDrop.width));
		this.setYLocation(ItemDrop.height + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * ItemDrop.height));
	}

	/**
	 * Returns the width of the item.
	 * @return a double representing the width of the item
	 */
	public double getWidth(){
		return width;
	}
	
	/**
	 * Returns the height of the item.
	 * @return a double representing the height of the item
	 */
	public double getHeight(){
		return height;
	}
	
	/**
	 * Returns whether the item is destroyable or not.
	 * This will allow the itemDrop class to expand to things like extra walls, traps, etc.
	 * @return {@code true} if the item is destroyable; {@code false} otherwise
	 */
	public boolean getIsDestroyable(){
		return isDestroyable;
	}
	
	/**
	 * Sets if the item is destroyable.
	 * @param {@code true} if the item is destroyable; {@code false} otherwise
	 */
	public void setIsDestroyable(boolean isDestroyable){
		this.isDestroyable = isDestroyable;
	}
	
	/**
	 * Returns the number of turns the item will exist.
	 * @return the number of turns the item is valid for
	 */
	public int getLifespan(){
		return lifespan;
	}
	
	/**
	 * Sets the number of turns the item will exist.
	 * @param the lifespan of the item
	 */
	public void setLifespan(int lifespan){
		this.lifespan = lifespan;
	}
	
	/**
	 * Returns the health of the item. If the health is 0 or below, then it will
	 * be destroyed when a robot collides with the item.
	 * @return the health of the item
	 */
	public double getHealth(){
		return health;
	}
	
	/**
	 * Set the health of an item.
	 * @param the health of the item
	 */
	public void setHealth(double health){
		this.health = health;
	}

	/**
	 * Returns whether the item is equippable or not.
	 * Equippable items will be extended for various items (eg. a flag in CTF)
	 * @return a boolean representing whether the item is equippable or not
	 */
	public boolean getIsEquippable(){
		return isEquippable;
	}
	
	/**
	 * Set whether the item is equippable or not
	 * @param {@code true} if the item is equippable; {@code false} otherwise
	 */
	public void setIsEquippable(boolean isEquippable){
		this.isEquippable = isEquippable;
	}
	
	/**
	 * The effect the item has on the robot. Must be overridden to have any effect.
	 */
	public void doItemEffect(RobotPeer robot){
		
	}
	
	/**
	 * Adds a new, random item to the battlefield.
	 * 
	 * @param robots the list of robots
	 * @param items the list of items
	 */
	public void addRandomItem(List<RobotPeer> robots, List<ItemDrop> items){
		boolean valid = false;
		
		if (!valid) {
			
			/*
			 * Runs a loop until a random location is found that an item can be 
			 * added, by checking for collisions between itself and other
			 * robots and items.
			 */
			for (int j = 0; j < 1000; j++) {
				this.updateToRandomLocation();
				this.setBoundingBox();
				
				if (validSpotRobot(robots)) {
					if (validSpotItem(items)) {
						break;
					}
				}
			}
		}
		//Adds the item to the field.
		//A renderobject should not be created unless a valid imageName has been set.
		if(this.imageName == null)
			return;
		this.battle.getCustomObject().add(new RenderObject(this.name, "/net/sf/robocode/ui/images/" + this.imageName, this.xLocation,this.yLocation));
	}
	
	/**
	 * Adds a new item to the battlefield at a specific location.
	 * 
	 * @param robots the list of robots
	 * @param items the list of items
	 * @param x the x-coordinate of the item
	 * @param y the y-coordinate of the item
	 * @return {@code true} if the item can be added at the location; {@code false} otherwise
	 */
	public boolean addNewItem(List<RobotPeer> robots, List<ItemDrop> items, double x, double y){
		this.xLocation = x;
		this.yLocation = y;
		
		if (validSpotRobot(robots)) {
			if (validSpotItem(items)){
				setBoundingBox();
				//A renderobject should not be created unless a valid imageName has been set.
				if(this.imageName == null)
					//return true, the item 'can' be added here, except the image is empty.
					return true;
				this.battle.getCustomObject().add(new RenderObject(this.name, "/net/sf/robocode/ui/images/" + this.imageName, this.xLocation,this.yLocation));
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	} 
	
	/**
	 * Checks to see whether a spot is valid for an item to be spawned,
	 * by checking with the list of robots.
	 * 
	 * @param robots the list of robots
	 * @return {@code true} if the spot is valid; {@code false} otherwise
	 * @see validSpotItem
	 */
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
	
	/**
	 * Checks to see whether a spot is valid for an item to be spawned,
	 * by checking with the list of items.
	 * 
	 * @param items the list of items
	 * @return {@code true} if the spot is valid; {@code false} otherwise
	 * @see validSpotRobot
	 */
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

	/**
	 * Create a Flag item based on the mode
	 * @param mode The mode to create the Flag for
	 * @param battle The battle for where it is to be placed
	 * @return A new Flag object designed for the mode 'mode'
	 */
	public static ItemDrop createForMode(IMode mode, Battle battle) {
		return null;
	}
}
