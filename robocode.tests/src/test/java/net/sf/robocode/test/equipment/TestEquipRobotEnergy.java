package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;

/**
 * This is to test for the equipment robot energy attribute.
 * 
 * Thus it tests:
 * {@link robocode.RobotAttribute#ENERGY}
 * 
 * @author Jayke Anderson - CSSE2003
 *
 */
public class TestEquipRobotEnergy extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.EnergyLarge,tested.equipment.EnergySmall";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	switch((int) event.getTurnSnapshot().getTurn()) {
    	case 1:
    		// Test the large robot has 120 starting energy
    		Assert.assertNear(120, event.getTurnSnapshot().getRobots()[0].
    				getEnergy());
    		// Test the small robot has 80 starting energy
    		Assert.assertNear(80, event.getTurnSnapshot().getRobots()[1].
    				getEnergy());
    	}
    }
}
