package net.sf.robocode.test.equipment;

import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import net.sf.robocode.test.helpers.Assert;

public class TestEquipRobotBulletPower extends RobocodeTestBed {
	
	private int BulletPowerX = 50;
	private int BulletPowerY = 50;
	private int BulletPowerDirection = 0;
	private int targetX = 50;
	private int targetY = 100;
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.BulletPower,tested.equipment.SittingEquipment";
    }
    
    @Override
    public String getInitialPositions() {
    	return "(" + Integer.toString(BulletPowerX) + "," + Integer.
    			toString(BulletPowerY) + "," + Integer.toString
    			(BulletPowerDirection) + ")" + ", " +
    			"(" + Integer.toString(targetX) + "," + Integer.
    			toString(targetY) + ",0)";
    }
    
    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	
    	final int turnNo = (int) event.getTurnSnapshot().getTurn();
    	final IRobotSnapshot bulletPowerRobot = event.getTurnSnapshot().
    			getRobots()[0];
    	final IRobotSnapshot targetRobot = event.getTurnSnapshot().
    			getRobots()[1];
    	
    	switch(turnNo){	
    	case 41:
    		test(105.46, 86.22, bulletPowerRobot, targetRobot);
    		break;
    		
    	case 56:
    		test(110.92, 72.44, bulletPowerRobot, targetRobot);
    		break;
    	
    	case 71:
    		test(116.38, 58.66, bulletPowerRobot, targetRobot);
    		break;
    	}
    }
    
    private void test(double bulletPowerHealth, double targetHealth, IRobotSnapshot
    		bp, IRobotSnapshot target){
    	// Test the robot has gained the correct energy for hitting a robot
    	Assert.assertNear(bulletPowerHealth, bp.getEnergy());
    	// Test the target robot has lost the correct amount of energy.
    	Assert.assertNear(targetHealth, target.getEnergy());
    }
}
