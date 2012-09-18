package robocode;

/**
 * CapturedItemEvent:
 *  signifies that the item is no longer available for capture
 *  Received when a robot picks up the item
 *
 * @author team-Telos
 *
 */
public class CapturedItemEvent {
	/* String representation of the item */
	private String item;
	/* Default priority for the event. ScannedRobot is 10...*/
	private static int DEFAULT_PRIORITY = 80;
	
	/**
	 * New CapturedItemEvent
	 * @param item String representation of the item
	 */
	CapturedItemEvent(String item) {
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
