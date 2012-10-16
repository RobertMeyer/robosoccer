package robocode.robotinterfaces;

import robocode.HitItemEvent;

/**
 * An event interface for receiving item robot events with an
 * {@link IItemRobot}.
 * 
 * @author Ameer Sabri
 * @see IItemRobot
 * 
 */
public interface IItemEvents {
	
	/**
	 * This method is called whenever the robot hits an item.
	 * </p>
	 * Applies any on-hit effects and item effects (if applicable).
	 * </p>
	 * 
	 * @param event the hit item event that occurred
	 * @see robocode.HitItemEvent
	 * @see robocode.Event
	 */
	void onHitItem(HitItemEvent event);
}
