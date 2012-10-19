package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * This is to test for the gun heat rate and gun turn angle attributes.
 * 
 * Thus it tests:
 * {@link robocode.RobotAttribute#GUN_HEAT_RATE}
 * {@link robocode.RobotAttribute#GUN_TURN_ANGLE}
 * 
 * @author Jayke Anderson - CSSE2003
 *
 */
public class TestEquipRobotGun extends RobocodeTestBed {

	@Test
    @Override
    public void run() {
        super.run();
    }
	
	@Override
	public String getRobotNames() {
		return "tested.equipment.GunLarge,tested.equipment.GunSmall";
	}
	
	@Override
    public String getInitialPositions() {
    	return "(50,50,0),(100,50,0)";
    }
	
	@Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final IRobotSnapshot largeGun = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot smallGun = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch((int) event.getTurnSnapshot().getTurn()) {
    	
    	case 1:
    		testGunTurnAngle(largeGun, 28, true);
    		testGunTurnAngle(smallGun, 12, false);
    		testGunHeat(largeGun, 2.86);
    		testGunHeat(smallGun, 2.94);
    		break;
    		
    	case 22:
    		testGunHeat(largeGun, 0);
    		break;
    		
    	case 31:
    		testGunTurnAngle(largeGun, 58, true);
    		testGunTurnAngle(smallGun, 26, false);
    		testGunHeat(largeGun, 2.1);
    		testGunHeat(smallGun, 1.14);
    		break;
    		
    	case 48:
    		testGunTurnAngle(largeGun, 88, true);
    		testGunTurnAngle(smallGun, 40, false);
    		testGunHeat(largeGun, 1.288);
    		testGunHeat(smallGun, 0.12);
    		break;
    	
    	case 50:
    		testGunHeat(smallGun, 0);
    		break;
    		
    	case 61:
    		testGunTurnAngle(largeGun, 118, true);
    		testGunTurnAngle(smallGun, 54, false);
    		testGunHeat(largeGun, 2.1);
    		testGunHeat(smallGun, 0.9);
    		break;
    		
    	case 76:
    		testGunHeat(largeGun, 0);
    		testGunHeat(smallGun, 0);
    		break;
    		
    	case 95:
    		testGunTurnAngle(largeGun, 148, true);
    		testGunTurnAngle(smallGun, 68, false);
    		testGunHeat(largeGun, 1.288);
    		testGunHeat(smallGun, 0.552);
    		break;
    		
    	case 105:
    		testGunHeat(largeGun, 0);
    		testGunHeat(smallGun, 0);
    	}
    }
	
	private void testGunHeat(IRobotSnapshot robot, double expectedHeat){
		Assert.assertNear(expectedHeat, robot.getGunHeat());
	}
	
	private void testGunTurnAngle(IRobotSnapshot robot, int expectedAngle, 
			boolean largeGun){
		if(largeGun) {
			Assert.assertNear(expectedAngle, Math.toDegrees(robot.
					getGunHeading()));
		}
		else {
			Assert.assertNear(expectedAngle, Math.toDegrees(robot.
					getGunHeading()));
		}
	}

}
