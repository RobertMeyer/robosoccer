package robocode;

import robocode.robotinterfaces.IItemEvents;
import robocode.robotinterfaces.IItemRobot;

/**
 * An extension of {@link AdvancedRobot} that allows robots to interact
 * with items.
 * 
 * @author Ameer Sabri
 * @see JuniorRobot
 * @see Robot
 * @see TeamRobot
 * @see AdvancedRobot
 * @see Droid
 */
public class ItemRobot extends AdvancedRobot implements IItemRobot, IItemEvents {
	
	/**
	 * {@inheritDoc}
	 */
	public void onHitItem(HitItemEvent event) {}
	
	/**
	 * {@inheritDoc}
	 */
	public void onScannedItem(ScannedItemEvent event) {}
	
	/**
	 * Do not call this method!
	 * <p/>
	 * {@inheritDoc}
	 */
	public final IItemEvents getItemEventListener() {
		return this; // this robot is listening
	}
}
