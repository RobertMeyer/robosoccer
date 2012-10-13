package net.sf.robocode.test.equipment;

import org.junit.Test;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.util.Utils;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * Test that an equipment robot is moving at the correct velocity, and
 * moving to the correct place in a given number of turns.  That is the
 * appropriate movement attributes are working.
 * 
 * @author Jayke Anderson
 */
public class TestEquipRobotMovement extends RobocodeTestBed {
	
	private int plasmaStartX = 50;
	private int plasmaStartY = 50;
	private int pistolStartX = 150;
	private int pistolStartY = 50;
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.MovementPlasma,tested.equipment.MovementPistol";
    }
    
    @Override
    public String getInitialPositions() {
    	return "(" + Integer.toString(plasmaStartX) + "," + Integer.
    			toString(plasmaStartY) + ",0)" + ", " +
    			"(" + Integer.toString(pistolStartX) + "," + Integer.
    			toString(pistolStartY) + ",0)";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final int turnNo = (int) event.getTurnSnapshot().getTurn();
    	final IRobotSnapshot plasmaRobot = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot pistolRobot = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch(turnNo){
    	// Where MovementPlasma robot should be
    	// Case 34 halfway through movement North
    	// Case 99 halway back towards starting position
    	case 34:
    	case 99:
    		Assert.assertNear(50, plasmaRobot.getX());
    		Assert.assertNear(plasmaRobot.getY(), 250);
    		Assert.assertNear(plasmaRobot.getVelocity(), 6.4);
    		break;
    	
    	// The MovementPistol should have moved approx 200 pixels forward
    	case 28:
    		Assert.assertNear(150, pistolRobot.getX());
    		Assert.assertNear(253.3, pistolRobot.getY());
    		Assert.assertNear(11.2, pistolRobot.getVelocity());//
    		break;
    	case 76:
    		Assert.assertNear(pistolRobot.getX(), 150);
    		Assert.assertNear(pistolRobot.getY(), 250);
    		Assert.assertNear(pistolRobot.getVelocity(), 11.2);
    		break;
    	
    	// Case 65 MovementPlasma should have moved 400 pixels forward
    	case 65:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 450);
    		Assert.assertNear(plasmaRobot.getVelocity(), 6.4);
    		break;
    	
    	// Case 47 MovementPistol should have moved 400 pixels forward
    	case 47:
    		Assert.assertNear(pistolRobot.getX(), 150);
    		Assert.assertNear(pistolRobot.getY(), 450);
    		Assert.assertNear(pistolRobot.getVelocity(), 11.2);
    		break;
    	
    	// Case 130 MovementPlasma should be back to the starting position
    	case 130:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 50);
    		Assert.assertNear(plasmaRobot.getVelocity(), 6.4);
    		break;
    	
    	// Case 94 Movement Pistol should be back to the starting position
    	case 94:
    		Assert.assertNear(pistolRobot.getX(), 150);
    		Assert.assertNear(pistolRobot.getY(), 50);
    		Assert.assertNear(pistolRobot.getVelocity(), 11.2);
    		break;
    	}
    }
}
