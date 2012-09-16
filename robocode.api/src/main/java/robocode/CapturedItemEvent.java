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
}
