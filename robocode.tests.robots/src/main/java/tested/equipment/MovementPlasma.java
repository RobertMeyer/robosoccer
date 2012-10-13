package tested.equipment;

import robocode.*;

/**
 * A sample equipment robot.  To be used for testing the forward movement
 * in relation to velocity.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class MovementPlasma extends AdvancedRobot {
	
	/**
	 * The main run function
	 */
	public void run(){		
		equip("Plasma Test");
		ahead(400);
		back(400);
	}
	
}
