package tested.equipment;

import robocode.*;

/**
 * A sample equipment robot.  To be used for testing the higher bullet power
 * than a standard robot.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class BulletPowerPlasma extends AdvancedRobot {
	
	public void run(){		
		equip("Plasma Test");
		while(true){
			setFire(getMaxBulletPower());
			execute();
			
		}
	}
	
}
