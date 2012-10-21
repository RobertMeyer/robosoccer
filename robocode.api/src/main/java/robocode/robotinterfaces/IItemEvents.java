package robocode.robotinterfaces;

import robocode.HitItemEvent;
import robocode.ScannedItemEvent;

/**
 * An event interface for receiving item robot events with an
 * {@link IItemRobot}.
 * 
 * @author Ameer Sabri (Dream Team)
 * @see IItemRobot
 * 
 */
public interface IItemEvents {
	
	/**
	 * This method is called whenever the robot hits an item.
	 * 
	 * @param event the hit item event that occurred
	 * @see robocode.HitItemEvent
	 * @see robocode.Event
	 */
	void onHitItem(HitItemEvent event);
	
	/**
	 * This method is called whenever the robot scans an item.
	 * 
	 * @param event the scanned item event that occurred
	 * @see robocode.Event
	 */
	void onScannedItem(ScannedItemEvent event);
}
