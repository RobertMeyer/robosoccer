package robocode;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import robocode.robotinterfaces.ISoccerRobot;
import robocode.robotinterfaces.peer.ISoccerRobotPeer;

/**
 * An advanced type of robot specifically for playing SoccerMode based games.
 * Can be safely added to other game modes, but may not be very effective.
 * SoccerRobot extends TeamRobot so advanced players can program team strategy
 * although this is not necessary.
 * 
 * @author Carl Hattenfels - Team-G1
 * 
 * @see JuniorRobot
 * @see Robot
 * @see AdvancedRobot
 * @see Droid
 * @see TeamRobot
 */
public class SoccerRobot extends TeamRobot implements ISoccerRobot {

	private final int GOALX = 80;
	private final int GOALY = 236;
	private static final int LEFT = 1;
	private static final int RIGHT = -1;
	
	/**
	 * Returns the location and size of the players ownGoal, in the form of
	 * a Rectangle2D.Float object.
	 * See java.awt.geom.Rectangle2D.
	 * @return Rectangle2D.Float - own goal bounding rectangle.
	 */
	public Rectangle2D.Float getOwnGoal() {
		Rectangle2D.Float ownGoal = null;
		if (peer != null) {
			ownGoal = ((ISoccerRobotPeer) peer).getOwnGoal();
		} else {
			uninitializedException();
		}
		return ownGoal;
	}
	
	/**
	 * Returns the location and size of the players enemy Goal, in the form of
	 * a Rectangle2D.Float object.
	 * See java.awt.geom.Rectangle2D.
	 * @return Rectangle2D.Float - enemy goal bounding rectangle.
	 */
	public Rectangle2D.Float getEnemyGoal() {
		Rectangle2D.Float enemyGoal = null;
		if (peer != null) {
			enemyGoal = ((ISoccerRobotPeer) peer).getEnemyGoal();
		} else {
			uninitializedException();
		}
		return enemyGoal;
	}
	
	/**
	 * Returns SoccerRobot.LEFT if the enemy goal is located on the left of
	 * the screen, and SoccerRobot.RIGHT if the enemy goal is located on the 
	 * right of the screen.
	 * @return int - SoccerRobot.LEFT or SoccerRobot.RIGHT, dependant on enemy
	 * goal side.
	 */
	public int getGoalSide() {
		return (getEnemyGoal().getCenterX() < getBattleFieldWidth() / 2) ? LEFT
				: RIGHT;
	}

	/**
	 * Sets Robot gun colour and radar colour, the parameter bodyColor will be
	 * unused as SoccerRobots are assigned a body colour according to their
	 * current team (red or blue).
	 * @param bodyColor - unused
	 * @param gunColor - java.awt.Color value to make gun colour.
	 * @param radarColor - java.awt.Color value to make radar colour.
	 */
	public void setColors(Color bodyColor, Color gunColor, Color radarColor) {
		if (peer != null) {
			Color teamColor = (getGoalSide() == LEFT) ? Color.RED : Color.BLUE;
			peer.setBodyColor(teamColor);
			peer.setGunColor(gunColor);
			peer.setRadarColor(radarColor);
		} else {
			uninitializedException();
		}
	}

	/**
	 * Sets Robot gun colour, radar colour, bullet colour and scan arc colour. 
	 * The parameter bodyColor will be unused as SoccerRobots are assigned a 
	 * body colour according to their current team (red or blue).
	 * @param bodyColor - unused
	 * @param gunColor - java.awt.Color value to make gun colour.
	 * @param radarColor - java.awt.Color value to make radar colour.
	 * @param bulletColor - java.awt.Color value to make radar colour.
	 * @param scanArcColor - java.awt.Color value to make scan arc colour.
	 */
	public void setColors(Color bodyColor, Color gunColor, Color radarColor,
			Color bulletColor, Color scanArcColor) {
		if (peer != null) {
			Color teamColor = (getGoalSide() == LEFT) ? Color.RED : Color.BLUE;
			peer.setBodyColor(teamColor);
			peer.setGunColor(gunColor);
			peer.setRadarColor(radarColor);
			peer.setBulletColor(bulletColor);
			peer.setScanColor(scanArcColor);
		} else {
			uninitializedException();
		}
	}

	/**
	 * Sets Robot colour to parameter color, except for robot body colour, which
	 * is automatically assigned to a team colour for SoccerRobots.
	 * @param bodyColor - unused
	 * @param color - java.awt.Color value to make all robot colour, except body
	 */
	public void setAllColors(Color color) {
		if (peer != null) {
			Color teamColor = (getGoalSide() == LEFT) ? Color.RED : Color.BLUE;
			peer.setBodyColor(teamColor);
			peer.setGunColor(color);
			peer.setRadarColor(color);
			peer.setBulletColor(color);
			peer.setScanColor(color);
		} else {
			uninitializedException();
		}
	}

	/**
	 * This function ignores color parameter and assigns the body color of a 
	 * SoccerRobot to either Blue or Red according to whether it is on the 
	 * Blue or Red soccer team. When not playing SoccerMode, all SoccerRobots
	 * are assigned to the Red team.
	 * @param color - unused
	 */
	public void setBodyColor(Color color) {
		if (peer != null) {
			Color teamColor = (getGoalSide() == LEFT) ? Color.RED : Color.BLUE;
			peer.setBodyColor(teamColor);
		} else {
			uninitializedException();
		}
	}
}
