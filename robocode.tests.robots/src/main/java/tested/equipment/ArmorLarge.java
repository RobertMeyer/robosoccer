package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a higher then normal
 * armor rating.
 * 
 * Thus, it tests:
 * {@link robocode.RobotAttribute#ARMOR}
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class ArmorLarge extends AdvancedRobot {
	
	public void run(){		
		equip("Armor Test Large");
		while (true){
			if(getTime()%10 == 0 && getTime() >= 40) setFire(
					getMaxBulletPower());
			execute();
		}
		
	}
	
}
