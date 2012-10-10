package robocode.robotinterfaces.peer;

import java.awt.geom.Rectangle2D;

/**
 * Interface for SoccerRobotProxy for Robots written to play SoccerMode. Used
 * by SoccerRobot class.
 * 
 * @author Carl Hattenfels - Team G1
 * @see IBasicRobotPeer
 * @see IStandardRobotPeer
 * @see IAdvancedRobotPeer
 * @see IJuniorRobotPeer
 */

public interface ISoccerRobotPeer extends ITeamRobotPeer {
	
	/**
	 * Returns the Rectangle bounds of the Robots own goal.
	 */
	public Rectangle2D.Float getOwnGoal();
	
	/**
	 * Returns the Rectangle bounds of the Robots own goal.
	 */
	public Rectangle2D.Float getEnemyGoal();
}
