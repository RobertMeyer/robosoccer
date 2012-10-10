package freezeSample;

import java.awt.Color;

import robocode.FreezeRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;


public class MyFirstFreezeBot extends FreezeRobot {
	private int scanDirection = 1;
	private int moveDirection = 1;
	
	public void run() {
		// Allow parts to move separately
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		while (true) {
			// Continually scan for robots
			turnRadarRight(360);
		}
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onHitRobot(robocode.HitRobotEvent)
	 */
	public void onHitRobot(HitRobotEvent e) { 
		// Hit the target. 
		// Move away
		setStop();
		moveDirection *= -1;
		setAhead(200 * moveDirection);
		execute();
		
		// Turn away so to hit the same robot again
		setTurnRight(45);
		execute();
		
		// Scan for next target
		setTurnRadarRight(270);
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onScannedRobot(robocode.ScannedRobotEvent)
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.isFrozen() || e.isFreezeBot()) {
			return;
		}

		// Oscillate the radar while keeping on the enemy to look for other enemies
		scanDirection = -scanDirection;
		setTurnRadarRight(360*scanDirection);
		
		// Slightly face the enemy so we can dodge but still move closer
		setTurnRight(e.getBearing());
				
		setAhead(500);

		scan();
	}
}
