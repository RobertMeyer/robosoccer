package net.sf.robocode.test.equipment;

import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import net.sf.robocode.test.helpers.Assert;

public class TestEquipRobotBulletPower extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.BulletPowerPistol,tested.equipment." +
        		"SittingEquipment," + "tested.equipment.BulletPowerPlasma," +
        				"tested.equipment.SittingEquipment";
    }
    
    @Override
    public String getInitialPositions() {
    	return "(50,50,0),(50,100,0),(100,50,0),(100,100,0)";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final int turnNo = (int) event.getTurnSnapshot().getTurn();
    	final IRobotSnapshot bulletPowerRobot = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot targetRobot = event.getTurnSnapshot().
    			getRobots()[1];
    	final IRobotSnapshot plasmaRobot = event.getTurnSnapshot().
    			getRobots()[2];
    	final IRobotSnapshot target2Robot = event.getTurnSnapshot().
    			getRobots()[3];
    	
    	switch(turnNo){
    	case 30:
    		test(111.4, 58.14, plasmaRobot, target2Robot);
    		break;
    		
    	case 41:
    		test(105.46, 86.22, bulletPowerRobot, targetRobot);
    		break;
    	
    	case 52:
    		test(122.8, 16.28, plasmaRobot, target2Robot);
    		break;
    		
    	case 56:
    		test(110.92, 72.44, bulletPowerRobot, targetRobot);
    		break;
    	
    	case 71:
    		test(116.38, 58.66, bulletPowerRobot, targetRobot);
    		break;
    		
    	case 74:
    		test(134.2, 0, plasmaRobot, target2Robot);
    		break;
    	}
    }
    
    private void test(double shooterHealth, double targetHealth, IRobotSnapshot
    		shooter, IRobotSnapshot target){
    	// Test the robot has gained the correct energy for hitting a robot
    	Assert.assertNear(shooterHealth, shooter.getEnergy());
    	// Test the target robot has lost the correct amount of energy.
    	Assert.assertNear(targetHealth, target.getEnergy());
    }
}
