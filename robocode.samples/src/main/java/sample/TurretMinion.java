package sample;

import robocode.Minion;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * 
 * @author James Pettigrew
 *
 */
public class TurretMinion extends Minion {
	String targetName = null;
	
	@Override
	public int getMinionType() {	
		return MINION_TYPE_ATK;
	}
	
	public void run( ) {
		while (true) {
			// Scan entire battlefield for enemy robots
			turnRadarRight(360);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		// If we have a target and this isn't it then scan again
		if (targetName != e.getName( ) && targetName != null) {
			return;
		}
		
		// If no robot is being targeted, then target this robot 
		if (targetName == null) {
			targetName = e.getName( );
		}

		// Adjust bullet power according to distance from enemy
		double bulletPower = 400 / e.getDistance( );
		// Equate velocity of bullet given the power of the bullet
		double bulletVel = 20 - (3 * bulletPower);
		// Calculate time required for bullet to travel to enemy
		double bulletTime = e.getDistance( ) / bulletVel;
		// Calculate current enemy x * y velocities
		double enemyVelX = e.getVelocity( ) * Math.sin(e.getHeading( ) * Math.PI / 180);
		double enemyVelY = e.getVelocity( ) * Math.cos(e.getHeading( ) * Math.PI / 180);
		double enemyAbsoluteBearing = getHeading( ) + e.getBearing( );
		// Calculate current enemy x & y coordinates
		double enemyCurrentX = getX( ) + (e.getDistance( ) * Math.sin(enemyAbsoluteBearing * Math.PI / 180));
		double enemyCurrentY = getY( ) + (e.getDistance( ) * Math.cos(enemyAbsoluteBearing * Math.PI / 180));	
		// Predict enemy x & y coordinates
		double enemyPredictedX = enemyCurrentX  + (enemyVelX * bulletTime);
		double enemyPredictedY = enemyCurrentY  + (enemyVelY * bulletTime);
		// Calculate displacement between minion and enemy bot
		double enemyDisplacementX = enemyPredictedX - getX( );
		double enemyDisplacementY = enemyPredictedY - getY( );
		// Calculate bearing and gun bearing from predicted coordinates
		double enemyPredictedBearing = Utils.normalAbsoluteAngle(Math.atan2(enemyDisplacementX, enemyDisplacementY));
		double bearingFromGun = Utils.normalRelativeAngle(enemyPredictedBearing - getGunHeadingRadians());
		setTurnGunRightRadians(bearingFromGun);
		setFire(bulletPower);
 	}
	
}
