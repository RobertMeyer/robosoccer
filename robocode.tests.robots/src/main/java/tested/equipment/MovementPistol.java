package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing the forward movement
 * in relation to velocity and acceleration.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class MovementPistol extends AdvancedRobot {
	
	/**
	 * The main run function.
	 */
	public void run(){		
		equip("Pistol");
		ahead(400);
		back(400);
	}
}
