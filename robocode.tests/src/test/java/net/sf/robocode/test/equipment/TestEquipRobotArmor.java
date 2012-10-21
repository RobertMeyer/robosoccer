package net.sf.robocode.test.equipment;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

/**
 * A simple test to test the armor of equipment robots.
 * 
 * Thus, this tests:
 * {@link robocode.RobotAttribute#ARMOR}
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class TestEquipRobotArmor extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.ArmorLarge,tested.equipment.ArmorSmall";
    }
    
    @Override
    public String getInitialPositions() {
    	return "(50,50,0),(50,100,180)";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final IRobotSnapshot largeArmor = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot smallArmor = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch((int) event.getTurnSnapshot().getTurn()) {
    	case 41:
    		checkHealth(largeArmor, 97);
    		checkHealth(smallArmor, 100);
    		break;
    		
    	case 43:
    		checkHealth(largeArmor, 106);
    		checkHealth(smallArmor, 73.33);
    		break;
    	
    	case 46:
    		checkHealth(largeArmor, 106);
    		checkHealth(smallArmor, 70.33);
    		break;
    		
    	case 48:
    		checkHealth(largeArmor, 94.57);
    		checkHealth(smallArmor, 79.33);
    		break;
    	}
    }
    
    private void checkHealth(IRobotSnapshot robot, double expectedHealth){
    	int robotHealth = (int) (robot.getEnergy()*100);
    	double finalHealth = ((double) robotHealth)/100;
    	Assert.assertNear(expectedHealth, finalHealth);
    }
}
