package sample;

import robocode.HitByBulletEvent;
import robocode.Minion;
import robocode.MinionProxy;
import robocode.util.Utils;

/**
 * 
 * @author James Pettigrew
 *
 */
public class DecoyMinion extends Minion {
	double distanceToParent = 0;
	
	@Override
	public int getMinionType() {
		return MINION_TYPE_DEF;
	}
	
	public void run( ) {		
		MinionProxy parent = getParent();
		while (true) {
    		// parentX = parent.getX( );
    		// parentY = parent.getY( );
			
			// double parentDisplacementX = parentX - getX( );
			// double parentDisplacementY = parentY - getY( );
			// double bearingToParent = Utils.normalAbsoluteAngle(Math.atan2(parentDisplacementX, parentDisplacementY));
			
	    	// double sqrParentDistX = Math.pow((parentDisplacementX - getX( )), 2);
	    	// double sqrParentDistY = Math.pow((parentDisplacementY - getY( )), 2);
	    	// distanceToParent = Math.sqrt(sqrParentDistX + sqrParentDistY);
	    	
	    	// setTurnRight(bearingToParent);
	    	setAhead(distanceToParent - 10);	    	
		}
	}
	
    public void onHitByBullet(HitByBulletEvent e) {
		// If trailing parent and parent is hit, move towards bullet's origin
    	if (distanceToParent < 20) {
    		turnRight(e.getBearing( ));
    		setAhead(300);
    	}
    	// Return to trailing parent
    }

}
