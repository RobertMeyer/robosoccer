package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

/**
 * This is to test to ensure that the equipment robot has the correct scan
 * radius and turns at the correct angle.
 * 
 * Thus it tests:
 * {@link robocode.RobotAttribute#SCAN_RADIUS}
 * {@link robocode.RobotAttribute#ROBOT_TURN_ANGLE}
 * 
 * @author Jayke Anderson - CSSE2003
 *
 */
public class TestEquipRobotRadar extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }
	
	@Override
    public String getRobotNames() {
        return "";
    }
	
//	@Override
//    public String getInitialPositions() {
//    	return "(50,50,0),(50,100,0)";
//    }
}
