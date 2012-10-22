package net.sf.robocode.test.robots;

import org.junit.Ignore;

import robocode.Equipment;
import robocode.EquipmentPart;
import robocode.control.events.RoundStartedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
/**
public class TestSword extends RobocodeTestBed{

	@Ignore
	public String getRobotNames() {
		return "sample.Crazy,sample.Target";
	}
	
	/**
	@Override
    public void onRoundStarted(final RoundStartedEvent event) {
        super.onRoundStarted(event);
        
        if (event.getRound() == 0) {
            IRobotSnapshot crazy = event.getStartSnapshot().getRobots()[0];
            IRobotSnapshot target = event.getStartSnapshot().getRobots()[1];
            
            EquipmentPart part = Equipment.getPart("Sword");
            
           Assert.assertEquals(part, crazy.getEquipment().get().get(part.getSlot()));
           Assert.assertNotSame(part, target.getEquipment().get().get(part.getSlot()));
        }
    }
    */
	
	/**
	 IBasicRobotPeer robotpeer = Mockito.mock(IBasicRobotPeer.class);
	 
     public void OnScannRobot(ScannedRobotEvent event){
		
    	 Mockito.when(robotpeer.checkSword()).thenReturn(false);
    	 Mockito.verify(robotpeer).setFire(1);
    	 Mockito.when(robotpeer.checkSword()).thenReturn(true);
    	 Mockito.verify(robotpeer, Mockito.never()).setFire(1);
	}
	*/

//}
