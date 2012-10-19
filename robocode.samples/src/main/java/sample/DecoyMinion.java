package sample;

import robocode.HitByBulletEvent;
import robocode.Minion;
import robocode.MinionProxy;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * 
 * @author James Pettigrew
 *
 */
public class DecoyMinion extends Minion {
	String parentName = null;
	
	@Override
	public int getMinionType() {
		return MINION_TYPE_DEF;
	}
	
	public void run( ) {		
		MinionProxy parent = getParent( );
		parentName = parent.getName( );
		while (true) {
			// Scan entire battlefield for enemy robots
			turnRadarRight(360);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		// If this isn't the parent then scan again
		if (parentName != e.getName( )) {
			return;
		}
		
		if (e.getDistance( ) < 75) {
			ahead(-50);
		}
		
		// Continuously trail parent, making sure not to ram it
		turnRight(e.getBearing( ));
		setAhead(e.getDistance() - 50);	
	}
	
   /* public void onHitByBullet(HitByBulletEvent e) {
		// If trailing parent and parent is hit, move towards bullet's origin
    	if (distanceToParent < 20) {
    		turnRight(e.getBearing( ));
    		setAhead(300);
    	}
    	// Return to trailing parent
    }*/

}
