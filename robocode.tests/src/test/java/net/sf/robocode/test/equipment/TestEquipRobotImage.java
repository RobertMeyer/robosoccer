package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.equipment.EquipmentSlot;
/**
 * A test to check if robot image paths are being passed 
 * all the way through the system correctly, that each robot
 * keeps its own image and that the custom image 
 * is not being applied to all robots on the battlefield
 * 
 * @author Andrew Schaul
 */
public class TestEquipRobotImage extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.ImageEquip1,tested.equipment.ImageEquip2";
    }
 
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	String imagePath1 = event.getTurnSnapshot().getRobots()[0].getEquipment().get().get(EquipmentSlot.GUN).getImagePath();
    	Assert.assertEquals(imagePath1, "/net/sf/robocode/ui/images/turret.png");
    	
    	String imagePath2 = event.getTurnSnapshot().getRobots()[1].getEquipment().get().get(EquipmentSlot.GUN).getImagePath();
    	Assert.assertEquals(imagePath2, "/net/sf/robocode/ui/images/twinturret.png");
    }
}