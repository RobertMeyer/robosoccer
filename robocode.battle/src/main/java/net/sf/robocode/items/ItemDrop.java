package net.sf.robocode.items;

import java.util.List;
import java.util.Random;

import robocode.control.RandomFactory;
import net.sf.robocode.battle.*;
import net.sf.robocode.battle.peer.*;
import net.sf.robocode.mode.IMode;
import robocode.*;

/**
 * Abstract class for item/powerup drops
 * 
 * Original:
 * @author s4238358
 * 
 * Contributors:
 * @author Brandon Warwick (Team-Telos)
 */

/*
 * Changes made by team-Telos */
public abstract	class ItemDrop {
	
	private double xLocation;
	private double yLocation;
	private final static double width = 40; //Same width and height as robots
	private final static double height = 40;
	private boolean isDestroyable;
	private int lifespan;
	private double health;
	private boolean isEquippable;
	private final BoundingRectangle boundingBox;
	private BattleRules battleRules;
	
	/* Unique ID for the item */
	private static int id;
	
	ItemDrop(boolean isDestroyable, int lifespan, double health, boolean isEquippable, Battle battle){
		this.isDestroyable = isDestroyable;
		this.lifespan = lifespan;
		this.health = health;
		this.isEquippable = isEquippable;
		System.out.println("Item made");
		this.boundingBox = new BoundingRectangle(xLocation, yLocation, width, height);
		this.battleRules = battle.getBattleRules();		
	}

	public BoundingRectangle getBoundingBox(){
		return boundingBox;
	}

	public void setBoundingBox() {
		boundingBox.setRect(xLocation - width / 2 + 2, yLocation - height / 2 + 2, width - 4, height - 4);
	}
	
	public double getXLocation() {
		return xLocation;
	}

	public void setXLocation(double xLocation) {
		this.xLocation = xLocation;
	}
	
	public double getYLocation() {
		return yLocation;
	}

	public void setYLocation(double yLocation) {
		this.yLocation = yLocation;
	}

	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public boolean getIsDestroyable(){
		return isDestroyable;
	}
	
	public void setIsDestroyable(boolean isDestroyable){
		this.isDestroyable = isDestroyable;
	}
	
	public int getLifespan(){
		return lifespan;
	}
	
	public void setLifespan(int lifespan){
		this.lifespan = lifespan;
	}
	
	public double getHealth(){
		return health;
	}
	
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
	 * Set the unique ID of the item
	 * @param className The name of the class for the ID
	 */
	public static void setId(String className) {
		ItemDrop.id = className.hashCode();
	}
	
	/**
	 * Get the unique ID of the item
	 * @return The item's unique ID
	 */
	public static int getId() {
		return id;
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
