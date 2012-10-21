package sample;

import robocode.Minion;
import robocode.MinionProxy;
import robocode.util.Utils;

/**
 * 
 * @author James Pettigrew
 *
 */
public class DecoyMinion extends Minion {
	
	
	@Override
	public int getMinionType() {
		return MINION_TYPE_DEF;
	}
	
	public void run( ) {		
		MinionProxy parent = getParent();
		double distanceToParent = 0;
		double parentX;
		double parentY;
		
		while (true) {
			// Get x & y coordinates of parent
    		parentX = parent.getX( );
    		parentY = parent.getY( );
			
    		// Calculate x & y displacement between parent and minion
			double parentDisplacementX = parentX - getX( );
			double parentDisplacementY = parentY - getY( );
			
			double bearingToParent = Utils.normalAbsoluteAngle(Math.atan2(parentDisplacementX, parentDisplacementY));
			
			// Calculate distance to parent using pythagoras
	    	double sqrParentDistX = Math.pow((parentDisplacementX), 2);
	    	double sqrParentDistY = Math.pow((parentDisplacementY), 2);
	    	distanceToParent = Math.sqrt(sqrParentDistX + sqrParentDistY);
	    	
	    	// Rotate and move towards parent, maintaining 200 range at all times
	    	turnRightRadians(bearingToParent - getHeadingRadians());
	    	setAhead(distanceToParent - 200);	    	
		}
	}
	
}
