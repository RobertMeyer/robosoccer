package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a smaller than normal
 * armor rating.
 * 
 * Thus, it tests:
 * {@link robocode.RobotAttribute#ARMOR}
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class ArmorSmall extends AdvancedRobot {
	
	public void run(){		
		equip("Armor Test Small");
		while (true){
			if(getTime()%15 == 0 && getTime() >= 40) setFire(
					getMaxBulletPower());
			execute();
		}
		
	}
	
}
