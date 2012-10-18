package sample;

import robocode.Minion;
import robocode.MinionProxy;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class ShieldMinion extends Minion {
	String targetName = null;
	double targetX = 0;
	double targetY = 0;
	double parentX = 0;
	double parentY = 0;
	double parentTargetBearing = 0;
	double shieldBearing = 0;
	double distanceToShield = 0;
	
	@Override
	public int getMinionType() {
		return MINION_TYPE_DEF;
	}
	
    @Override
    public void run() {
    	MinionProxy parent = getParent();
    	while (true) {
    		// targetName = getName;
    		parentX = parent.getX( );
    		parentY = parent.getY( );
    		System.out.println(parentX);
		
    		// Follow parent
    	
    	
    		// Scan for enemy robot
    		setTurnRadarRight(360);
    	
    		// Move to shield coordinates
    		setTurnRightRadians(shieldBearing);
    		setAhead(distanceToShield);
    	}
    }
    
	public void onScannedRobot(ScannedRobotEvent e) {
		// If we have a target and this isn't it then scan again
		if (targetName != e.getName( ) && targetName != null) {
			return;
		}
		
		// This is our target, update coordinates
		double enemyAbsoluteBearing = getHeading( ) + e.getBearing( );
		targetX = getX( ) + (e.getDistance( ) * Math.sin(enemyAbsoluteBearing * Math.PI / 180));
		targetY = getY( ) + (e.getDistance( ) * Math.cos(enemyAbsoluteBearing * Math.PI / 180));
		
    	double parentTargetDisplacementX = targetX - parentX;
    	double parentTargetDisplacementY = targetY - parentY;	
    	// Acquire bearing of target relative to parent
    	parentTargetBearing = Utils.normalAbsoluteAngle(Math.atan2(parentTargetDisplacementX, parentTargetDisplacementY));
		
    	// Calculate x & y coordinates to move to such that minion will shield parent
    	double shieldX = 10 * Math.sin(parentTargetBearing * Math.PI / 180);
    	double shieldY =  10 * Math.cos(parentTargetBearing * Math.PI / 180);
    	// Calculate bearing to shield coordinates
    	shieldBearing = Utils.normalAbsoluteAngle(Math.atan2(shieldX, shieldY));
    	// Calculate distance to shield coordinates using pythagoras theorem
    	double sqrDistShieldX = Math.pow((shieldX - getX( )), 2);
    	double sqrDistShieldY = Math.pow((shieldY - getY( )), 2);
    	distanceToShield = Math.sqrt(sqrDistShieldX + sqrDistShieldY);	
 	}
}
