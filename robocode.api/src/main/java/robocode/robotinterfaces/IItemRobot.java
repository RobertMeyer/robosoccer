package robocode.robotinterfaces;

/**
 * A robot interface for creating an item-interactable robot like
 * {@link robocode.ItemRobot} that is able to handle robot item events.
 * 
 * @author Ameer Sabri
 * @see robocode.ItemRobot
 * @see IBasicRobot
 * @see IJuniorRobot
 * @see IInteractiveRobot
 * @see ITeamRobot
 * @see IAdvancedRobot
 */
public interface IItemRobot extends IAdvancedRobot{
	
	/**
	 * This method is called by the game to notify this robot about item events.
	 * Hence, this method must be implemented so it returns your
	 * {@link IItemEvents} listener.
	 *
	 * @return listener to item events or {@code null} if this robot should
	 *         not receive the notifications.
	 */
	IItemEvents getItemEventListener();
}
