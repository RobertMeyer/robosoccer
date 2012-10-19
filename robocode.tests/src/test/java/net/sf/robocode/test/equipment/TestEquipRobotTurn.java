package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

/**
 * This is to test to ensure that the equipment robot turns at the correct
 * angles.
 * 
 * Thus it tests:
 * {@link robocode.RobotAttribute#ROBOT_TURN_ANGLE}
 * 
 * @author Jayke Anderson - CSSE2003
 *
 */
public class TestEquipRobotTurn extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }
	
	@Override
    public String getRobotNames() {
        return "tested.equipment.TurnLarge,tested.equipment.TurnSmall";
    }
	
	@Override
    public String getInitialPositions() {
    	return "(50,50,0),(50,100,0)";
    }
}
