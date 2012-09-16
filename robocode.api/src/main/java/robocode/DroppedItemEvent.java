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
}
