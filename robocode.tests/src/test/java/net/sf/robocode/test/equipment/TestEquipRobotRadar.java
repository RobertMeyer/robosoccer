package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

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
        return "tested.equipment.RadarLarge,tested.equipment.RadarSmall";
    }
	
	@Override
    public String getInitialPositions() {
    	return "(50,50,0),(50,100,0)";
    }
	
	@Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final IRobotSnapshot largeTurn = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot smallTurn = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch((int) event.getTurnSnapshot().getTurn()) {
    	
    	case 1:
    		testTurnAngle(largeTurn, 63);
    		testTurnAngle(smallTurn, 27);
    		testTurnRadius(largeTurn, 1680);
    		testTurnRadius(smallTurn, 720);
    		break;
    		
    	case 2:
    		testTurnAngle(largeTurn, 65);
    		testTurnAngle(smallTurn, 29);
    		testTurnRadius(largeTurn, 1680);
    		testTurnRadius(smallTurn, 720);
    		break;
    		
    	case 11:
    		testTurnAngle(largeTurn, 128);
    		testTurnAngle(smallTurn, 56);
    		testTurnRadius(largeTurn, 1680);
    		testTurnRadius(smallTurn, 720);
    		break;
    		
    	case 12:
    		testTurnAngle(largeTurn, 130);
    		testTurnAngle(smallTurn, 58);
    		testTurnRadius(largeTurn, 1680);
    		testTurnRadius(smallTurn, 720);
    		break;
    	}
	}
	
	private void testTurnAngle(IRobotSnapshot robot, int expectedAngle){
			Assert.assertNear(expectedAngle, Math.toDegrees(robot.
					getRadarHeading()));
	}
	
	private void testTurnRadius(IRobotSnapshot robot, int expectedRadius){
		Assert.assertNear(expectedRadius, robot.getScanRadius());
	}
}
