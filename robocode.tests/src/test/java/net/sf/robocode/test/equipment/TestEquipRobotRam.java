package net.sf.robocode.test.equipment;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

/**
 * A simple test to test the ram attack and defense of equipment robots.
 * 
 * Thus, this tests:
 * {@link robocode.RobotAttribute#RAM_ATTACK}
 * {@link robocode.RobotAttribute#RAM_DEFENSE}
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class TestEquipRobotRam extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.RamLarge,tested.equipment.RamSmall";
    }
    
    @Override
    public String getInitialPositions() {
    	return "(50,50,0),(50,100,180)";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final IRobotSnapshot largeRam = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot smallRam = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch((int) event.getTurnSnapshot().getTurn()) {
    	case 10:
    		checkHealth(largeRam, 100.84);
    		checkHealth(smallRam, 99.64);
    		break;
    		
    	case 15:
    		checkHealth(largeRam, 100);
    		checkHealth(smallRam, 100);
    		break;
    	}
    }
    
    private void checkHealth(IRobotSnapshot robot, double expectedHealth){
    	int robotHealth = (int) (robot.getEnergy()*100);
    	double finalHealth = ((double) robotHealth)/100;
    	Assert.assertNear(expectedHealth, finalHealth);
    }
}
