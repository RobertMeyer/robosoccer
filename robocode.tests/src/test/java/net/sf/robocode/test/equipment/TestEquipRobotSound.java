package net.sf.robocode.test.equipment;

import org.junit.Test;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.equipment.EquipmentSlot;

/**
 *A simple test that the sound paths are getting passes though the system
 *correctly, are being assigned to their robots correctly, and
 *remain the same throughout the duration of the battle
 * 
 * @author Jarred FIlmer
 */
public class TestEquipRobotSound extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.SoundEquip1,tested.equipment.SoundEquip2";
    }
 
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	String test1 = event.getTurnSnapshot().getRobots()[0].getEquipment().get().get(EquipmentSlot.WEAPON).getSoundPath();
    	
    	// Test that the test1
    	Assert.assertEquals(test1, "Test1");
    	
    	String test2 = event.getTurnSnapshot().getRobots()[1].getEquipment().get().get(EquipmentSlot.WEAPON).getSoundPath();
    	
    	// Test the target robot has lost the correct amount of energy.
    	Assert.assertEquals(test2, "Test2");
    }
}
