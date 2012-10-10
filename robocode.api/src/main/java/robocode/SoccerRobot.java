package robocode;

import java.awt.geom.Rectangle2D;
import robocode.robotinterfaces.ISoccerRobot;
import robocode.robotinterfaces.peer.ISoccerRobotPeer;

/**
 * An advanced type of robot specifically for playing SoccerMode based games.
 * Can be safely added to other game modes, but may not be very effective. 
 * SoccerRobot extends TeamRobot so advanced players can program team startegy
 * although this is unnecessary.
 *
 * @author Team-G1
 * 
 * @see JuniorRobot
 * @see Robot
 * @see AdvancedRobot
 * @see Droid
 * @see TeamRobot
 */
public class SoccerRobot extends TeamRobot implements ISoccerRobot {
	
	public Rectangle2D.Float getOwnGoal() {
		return ((ISoccerRobotPeer) peer).getOwnGoal();
	}
	
	public Rectangle2D.Float getEnemyGoal() {
		return ((ISoccerRobotPeer) peer).getEnemyGoal();
	}
}
