package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a smaller gun heat rate
 * and gun turn angle.
 * 
 * Thus, it tests:
 * RobotAttribute#GUN_HEAT_RATE
 * RobotAttribute#GUN_TURN_ANGLE
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class GunSmall extends AdvancedRobot {
	
	public void run(){		
		equip("Gun Test Small");
		while (true){
			if(this.getTime()%30 == 0){
				if(this.getTime()!= 90){
					setFire(getMaxBulletPower());
					setTurnGunRight(14);
				}
			}
			if(getTime()%47 == 0){
				setFire(getMinBulletPower());
				setTurnGunRight(14);
			}
			execute();
		}
	}
	
}
