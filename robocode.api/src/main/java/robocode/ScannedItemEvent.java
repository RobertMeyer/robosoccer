package robocode;

import java.awt.peer.RobotPeer;

/**
 * ScannedItemEvent:
 *  Received when a robot scans the item, whether it is captured or not
 *  Returns a ScannedRobotEvent directly after this IF a robot has the item
 *  Tells the robot the location of the item, and the name of robot
 *  that is holding it
 *
 * @author team-Telos
 *
 */
public class ScannedItemEvent {
	/** Can be simplified if we have access to ItemDrop here */
	
//	private final static int DEFAULT_PRIORITY = ; TODO 
	/* String representation of the item */
	private final String item;
	/* Robot's name carrying the item */
	private final String robotName;
	/* x-location of the item */
	private final int x;
	/* y-location of the item */
	private final int y;
	
	/**
	 * New ScannedItemEven
	 * @param item String representation of the item
	 * @param robotName Robot's name carrying the item
	 * @param x x-location of the item
	 * @param y y-location of the item
	 */
	ScannedItemEvent(String item, String robotName, int x, int y) {
		this.item = item;
		this.robotName = robotName;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the name of the item scanned
	 * @return String representation of the name of the item
	 */
	public String getItemName() {
		return this.item;
	}
	
	/**
	 * Get the Robot's name holding the item
	 * @return The Robot's name holding the item OR null if none
	 */
	public String getRobotName() {
		return this.robotName;
	}
	
	/**
	 * Get the x location of the item
	 * @return x location of item
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get the y location of the item
	 * @return y location of item
	 */
	public int getY() {
		return this.y;
	}
}
