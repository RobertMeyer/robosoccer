package net.sf.robocode.test.equipment;

import org.junit.Test;

import net.sf.robocode.test.helpers.RobocodeTestBed;

/**
 * Add a basic Equipment Robot to the game.  If the test fails, it is because
 * the robot has not been added to the game.
 * 
 * @author Jayke Anderson - CSSE2003
 *
 */
public class TestEquipRobotInPlay extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }
	
	/**
	 * Add an Equipment Robot to the game.
	 */
	@Override
	public String getRobotNames() {
		return "tested.equipment.AllSlots, sample.Crazy";
	}
	
}
