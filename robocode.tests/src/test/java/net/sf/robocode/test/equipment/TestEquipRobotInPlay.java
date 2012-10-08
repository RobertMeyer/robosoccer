package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.RobocodeTestBed;

/**
 * Add a basic Equipment Robot to the game.  If the test fails, it is because
 * the robot has not been added to the game.
 * 
 * @author Jayke Anderson
 *
 */
public class TestEquipRobotInPlay extends RobocodeTestBed {
	
	/**
	 * Add an Equipment Robot to the game.
	 */
	@Override
	public String getRobotNames() {
		return "equipment.SampleEquipRobot";
	}
	
}
