package robocode.robotinterfaces.peer;

/**
 * The item robot peer for the {@link robocode.ItemRobot}.
 * </p>
 * A robot peer is the object that deals with game mechanics and rules, and
 * makes sure your robot abides by them.
 * 
 * TODO Add parameters to doItemEffect if applicable
 * 
 * @author Ameer Sabri
 * @see IBasicRobotPeer
 * @see IStandardRobotPeer
 * @see ITeamRobotPeer
 * @see IJuniorRobotPeer
 * @see IAdvancedRobotPeer
 *
 */
public interface IItemRobotPeer extends IAdvancedRobotPeer {
	
	void doItemEffect();
}
