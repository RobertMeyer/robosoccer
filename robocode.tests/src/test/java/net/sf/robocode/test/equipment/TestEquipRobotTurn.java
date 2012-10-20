package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

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
	
	@Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final IRobotSnapshot largeTurn = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot smallTurn = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch((int) event.getTurnSnapshot().getTurn()) {
    	
    	case 1:
    		testTurnAngle(largeTurn, 14);
    		testTurnAngle(smallTurn, 6);
    		break;
    		
    	case 2:
    		testTurnAngle(largeTurn, 16);
    		testTurnAngle(smallTurn, 8);
    		break;
    		
    	case 11:
    		testTurnAngle(largeTurn, 30);
    		testTurnAngle(smallTurn, 14);
    		break;
    		
    	case 12:
    		testTurnAngle(largeTurn, 32);
    		testTurnAngle(smallTurn, 16);
    		break;
    	}
	}
	
	private void testTurnAngle(IRobotSnapshot robot, int expectedAngle){
		Assert.assertNear(expectedAngle, Math.toDegrees(robot.
				getBodyHeading()));
	}
    	
}
