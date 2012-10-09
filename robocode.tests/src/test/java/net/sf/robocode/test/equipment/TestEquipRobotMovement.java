package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * Test that an equipment robot is moving at the correct velocity, and
 * moving to the correct place in a given number of turns.  That is the
 * appropriate movement attributes are working.
 * 
 * @author Jayke Anderson
 */
public class TestEquipRobotMovement extends RobocodeTestBed {
	
	private int currentX = 0;
	private int currentY = 0;
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "equipment.SampleEquipRobot";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	for (IRobotSnapshot robot : event.getTurnSnapshot().getRobots()) {
    		
    	}
    }
}
