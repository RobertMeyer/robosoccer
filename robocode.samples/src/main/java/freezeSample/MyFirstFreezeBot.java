package freezeSample;

import robocode.FreezeRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;


public class MyFirstFreezeBot extends FreezeRobot {
	private int scanDirection = 1;
	
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
		// Hit the target. Move away and search for another
		setAhead(-100);
		setTurnRadarRight(270);
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onScannedRobot(robocode.ScannedRobotEvent)
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.isFrozen()) {
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