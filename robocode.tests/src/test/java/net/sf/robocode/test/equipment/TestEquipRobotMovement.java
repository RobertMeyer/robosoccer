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
        return "equipment.PlasmaEquipRobot,equipment.PistolEquipRobot";
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
    	case 34:
    	case 99:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 250);
    		Assert.assertNear(plasmaRobot.getVelocity(), 6.4);
    		break;
    		
    	case 29:
    	case 76:
    		Assert.assertNear(pistolRobot.getX(), 150);
    		Assert.assertNear(pistolRobot.getY(), 250);
    		Assert.assertNear(pistolRobot.getVelocity(), 11.2);
    		break;
    	
    	case 65:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 450);
    		Assert.assertNear(plasmaRobot.getVelocity(), 6.4);
    		break;
    		
    	case 47:
    		Assert.assertNear(pistolRobot.getX(), 150);
    		Assert.assertNear(pistolRobot.getY(), 450);
    		Assert.assertNear(pistolRobot.getVelocity(), 11.2);
    		break;
    		
    	case 130:
    		Assert.assertNear(plasmaRobot.getX(), 50);
    		Assert.assertNear(plasmaRobot.getY(), 50);
    		Assert.assertNear(plasmaRobot.getVelocity(), 6.4);
    		break;
    		
    	case 94:
    		Assert.assertNear(pistolRobot.getX(), 150);
    		Assert.assertNear(pistolRobot.getY(), 50);
    		Assert.assertNear(pistolRobot.getVelocity(), 11.2);
    		break;
    	}
    }
}
