package robocode;

/**
 * Dropped Item Event:
 *  Signifies that the item is now on the board
 *  Received when the round starts
 *  Received when a robot dies
 *  Received by the robot to tell it to scan for the item to find it
 *
 * @author team-Telos
 */
public class DroppedItemEvent {
	/* String representation of the item */
	private String item;
	/* Default priority for the event. ScannedRobot is 10...*/
	private static int DEFAULT_PRIORITY = 50;
	
	/**
	 * New DroppedItemEvent
	 * @param item String representation of the item
	 */
	DroppedItemEvent(String item) {
		this.item = item;
	}
	
	/**
	 * Get the name of the item scanned
	 * @return String representation of the name of the item
	 */
	public String getItemName() {
		return this.item;
	}
	
	/**
	 * Get the current priority of the event
	 * @return the priority of the event
	 */
	public int getPriority() {
		return DEFAULT_PRIORITY;
	}
	
	/**
	 * set the priority for the event. Each item may have a different
	 * priority so this method will be used to change each items priority.
	 * @param priority
	 */
	public static void setPriority(int priority) {
		DEFAULT_PRIORITY = priority;
	}

}
