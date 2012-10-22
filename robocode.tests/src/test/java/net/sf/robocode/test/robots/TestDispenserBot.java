/*******************************************************************************
 * Copyright (c) 2012 Team The Fightin' Mongooses
 *
 * Contributors:
 * 	Paul Wade
 * 	Chris Irving
 * 	Jesse Claven
 *******************************************************************************/
package net.sf.robocode.test.robots;

import org.junit.*;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * Test that a robot's energy level increases when it is in close proximity
 * to a Dispenser robot.
 * 
 * @author The Fightin' Mongooses
 */
public class TestDispenserBot extends RobocodeTestBed {
	private int[] previousEnergy;
	private int turnCount = 0;
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "sample.Target";
//        return "sample.Target,tested.robots.DispenserBotHeal";
    }

    /**
     * Place the Target robot within range of the Dispenser robot in order for
     * its energy to be increased.
     */
    @Override
    public String getInitialPositions() {
        return "(50,50,0), (70,70,0)";
    }

    /**
     * Store the robot's energy level each round.
     */
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);
        IRobotSnapshot bp = event.getTurnSnapshot().getRobots()[turnCount];
        
        previousEnergy[turnCount] = (int) bp.getEnergy();
        System.out.println("Turn count: " + turnCount + "Energy level: " + bp.getEnergy());
        turnCount++;
    }
    
    /**
     * Check that the energy level of Target increases each round as it is
     * within range of the Dispenser robot.
     */
    @Ignore
    @Test
    public void testEnergyLevel() {
		Assert.assertNotSame("The robot's energy level did not increase.", previousEnergy[0], previousEnergy[1]);
    }
}
