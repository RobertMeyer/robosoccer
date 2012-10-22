package freezeSample;

import java.awt.Color;

import robocode.FreezeRobot;
import robocode.HeatRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;


public class MyFirstHeatBot extends HeatRobot {
	private int scanDirection = 1;
	private int moveDirection = 1;
	
	public void run() {
		// Allow parts to move separately
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		setAllColors(Color.RED);
		
		while (true) {
			// Continually scan for robots
			turnRadarRight(360);
		}
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onHitRobot(robocode.HitRobotEvent)
	 */
	@Override
	public void onHitRobot(HitRobotEvent e) { 
		// Hit a robot
		// Move away
		moveDirection *= -1;
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onScannedRobot(robocode.ScannedRobotEvent)
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.isFrozen() || e.isFreezeBot()) {
			return;
		}

		// Slightly face the enemy so we can dodge but still move closer
		setTurnRight(e.getBearing() + 90);
				
		setAhead(150 * moveDirection);
		
		scan();
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onHitWall(robocode.HitWallEvent)
	 */
	@Override
	public void onHitWall(HitWallEvent e) { 
		if (getVelocity() == 0) {
			// We hit a wall, change direction
			moveDirection *= -1; 
			setTurnRight(40);
		}
	}
	

}
