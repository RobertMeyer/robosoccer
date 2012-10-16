package net.sf.robocode.test.equipment;

import org.junit.Test;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * Test that an equipment robot is moving at the correct velocity, and
 * moving to the correct place in a given number of turns.  That is the
 * appropriate movement attributes are working.
 * 
 * Tests the following attributes:
 * RobotAttribute#SPEED
 * RobotAttribute#ACCELERATION
 * RobotAttribute#DECELERATION
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
    	// The MovementPistol should have moved approx 200 pixels forward
    	case 22:
    		Assert.assertNear(150, pistolRobot.getX());
    		Assert.assertNear(253.6, pistolRobot.getY());
    		Assert.assertNear(11.2, pistolRobot.getVelocity());
    		Assert.assertNear(1.3, pistolRobot.getAcceleration());
    		break;
    		
    	// The MovementPlasma should have moved approx 200 pixels forward
    	case 34:
    		Assert.assertNear(50, plasmaRobot.getX());
    		Assert.assertNear(250.2, plasmaRobot.getY());
    		Assert.assertNear(6.4, plasmaRobot.getVelocity());
    		break;
    		
    	// The MovementPistol should have moved approx 400 pixels forward
    	case 44:
    		Assert.assertNear(150, pistolRobot.getX());
    		Assert.assertNear(448.284, pistolRobot.getY());
    		Assert.assertNear(-1.508, pistolRobot.getVelocity());
    		break;
    		
    	// The MovementPlasma should have moved approx 400 pixels forward
    	case 68:
    		Assert.assertNear(50, plasmaRobot.getX());
    		Assert.assertNear(449.3, plasmaRobot.getY());
    		Assert.assertNear(-0.7, plasmaRobot.getVelocity());
    		break;
    	
    	// The MovementPistol should have moved aprox 200 pixels back to 
    	// the starting position going backwards
    	case 65:
    		Assert.assertNear(150, pistolRobot.getX());
    		Assert.assertNear(244.528, pistolRobot.getY());
    		Assert.assertNear(-11.2, pistolRobot.getVelocity());
    		break;
    	
    	// The Movement Pistol should be back to the starting position
    	case 87:
    		Assert.assertNear(150, pistolRobot.getX());
    		Assert.assertNear(50, pistolRobot.getY());
    		Assert.assertNear(0, pistolRobot.getVelocity());
    		break;
    	
    	// The MovementPlasma should be halfway back to the starting position
    	// going backwards
    	case 101:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 251.6);
    		Assert.assertNear(plasmaRobot.getVelocity(), -6.4);
    		break;

    	// The MovementPlasma should be back to the starting position
    	case 135:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 50);
    		Assert.assertNear(plasmaRobot.getVelocity(), 0);
    		break;
    	
    	}
    }
}
