package sample;

import robocode.Minion;
import robocode.MinionProxy;
import robocode.ScannedRobotEvent;

/**
 * 
 * @author James Pettigrew
 *
 */
public class SucideMinion extends Minion {
	String targetName = null;
	String parentName = null;
	
	@Override
	public int getMinionType() {
		return MINION_TYPE_UTL;
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
		// If we have a target and this isn't it then scan again
		if (targetName != e.getName( ) && targetName != null) {
			return;
		} else if (e.getName( ) == parentName) { // If this is the parent then scan again
			return;
		}
		
		// If we have no target then this is now our target
		if (targetName == null) {
			targetName = e.getName( );
		}
		
		// Continuously move towards enemy
		turnRight(e.getBearing( ));
		setAhead(30);
	
    }	

}
