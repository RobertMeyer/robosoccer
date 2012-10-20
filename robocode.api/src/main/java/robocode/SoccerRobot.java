package robocode;

import java.awt.Color;
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

	private final int GOALX = 80;
	private final int GOALY = 236;
	private static final int LEFT = 1;
	private static final int RIGHT = -1;
	

	public Rectangle2D.Float getOwnGoal() {
		Rectangle2D.Float ownGoal = ((ISoccerRobotPeer) peer).getOwnGoal();
		if (ownGoal == null) {
			ownGoal = new Rectangle2D.Float(0, (int) (getBattleFieldWidth() / 2) 
					- (GOALY / 2), GOALX, GOALY);
		}
		return ownGoal;
	}

	public Rectangle2D.Float getEnemyGoal() {
		Rectangle2D.Float enemyGoal = ((ISoccerRobotPeer) peer).getEnemyGoal();
		if (enemyGoal == null) {
			enemyGoal = new Rectangle2D.Float((int) getBattleFieldWidth()
					- GOALX, (int) (getBattleFieldHeight() / 2) - (GOALY / 2),
					GOALX, GOALY);
		}
		return enemyGoal;
	}

	public int getGoalSide() {
		return (getEnemyGoal().getCenterX() < getBattleFieldWidth() / 2) ? LEFT
				: RIGHT;
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	public void setBodyColor(Color color) {
		if (peer != null) {
			Color teamColor = (getGoalSide() == LEFT) ? Color.RED : Color.BLUE;
			peer.setBodyColor(teamColor);
		} else {
			uninitializedException();
		}
	}
}
