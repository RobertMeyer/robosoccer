
package freezeSample;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;


public class chaseHeatRobot extends AdvancedRobot {
	// 1 to keep current direction, 0 to change
	private byte moveDirection = 1;
	private int scanDirection = 1;
	
	// The energy of the scanned robot, used to tell if the robot fired
	private double enemyEnergy = 100;
	
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
	 * @see robocode.Robot#onBulletHitBullet(robocode.BulletHitBulletEvent)
	 */
	public void onBulletHitBullet(BulletHitBulletEvent e) {
		// We were shot. Change direction.
		moveDirection *= -1; 
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onHitWall(robocode.HitWallEvent)
	 */
	public void onHitWall(HitWallEvent e) { 
		if (getVelocity() == 0) {
			// We hit a wall, change direction
			moveDirection *= -1; 
		}
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onHitRobot(robocode.HitRobotEvent)
	 */
	public void onHitRobot(HitRobotEvent e) { 
		// Shoot the robot that ran into us
		setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
		if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
			setFire(3);
		}
		
		// We hit a robot, change direction
		if (getVelocity() == 0) {
			// Only change if we hit the robot head on
			moveDirection *= -1; 
		}
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see robocode.Robot#onScannedRobot(robocode.ScannedRobotEvent)
	 */
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.isHeatBot()) {
			setStop();
			execute();
			setAllColors(Color.GREEN);
			return;
		}
		
		scanDirection = -scanDirection;
		setTurnRadarRight(360*scanDirection);
		
		// Slightly face the enemy so we can dodge but still move closer
		setTurnRight(e.getBearing() + 90 - (20 * moveDirection));
		
		// Check if the enemy fired and dodge if need be
		double changeInEnergy = enemyEnergy - e.getEnergy();
		if (changeInEnergy > 0 && changeInEnergy <= 3)
			moveDirection *= -1;
		enemyEnergy = e.getEnergy();
		
		setAhead(150 * moveDirection);
		
		// Move the gun to the predicted direction of the robot
		aimAndFire(e);
		scan();
	}
	

	/**
	 * Aim where the enemy is predicted to be and fire
	 * Assume: the enemies velocity and heading remain the same
	 * @param e the scanned robot that we want to hit
	 */
	void aimAndFire(ScannedRobotEvent e) {
		// Get enemy details
		double enemyVelocity = e.getVelocity();
		double enemyDistance = e.getDistance();
		double enemyHeading = e.getHeadingRadians();

		// Determine the correct power of the bullet
		double power = Math.min((400 / e.getDistance()), 3);
		
		// The time it will take for the bullet to reach the enemy
		double time = enemyDistance / (20 - 3 * power);
		
		// The absolute bearing to the enemy
		double bearing = getHeadingRadians() + e.getBearingRadians();

		// Enemies (x,y) coordinates
		double enemyX = enemyDistance * Math.cos(bearing);
		double enemyY = enemyDistance * Math.sin(bearing);
		
		// Predicted (x,y) coordinates of the enemy
		double distanceTravelled = time * enemyVelocity;
		double futureX = enemyX + (distanceTravelled * Math.cos(enemyHeading));
		double futureY = enemyY + (distanceTravelled * Math.sin(enemyHeading));
		
		// The direction in which to shoot
		double aimDirection = (Math.atan2(futureY,  futureX) - getGunHeadingRadians());
		
		// The direction in which to shoot relative to our heading
		aimDirection =  Utils.normalRelativeAngle(aimDirection);

		// Wait for the gun to cool and be aiming correctly then shoot
		setTurnGunRightRadians(aimDirection);
		
		// Only fire if the gun has finished turning and doesn't need to cool
		if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
			fire(power);
		}
	}
	
}
