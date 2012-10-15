package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A simple equipment robot for testing, that fires at scanned robots so that
 * the aspects of the bullet can be checked.
 * 
 * @author Jayke Anderson - CSSE2003
 *
 */
public class BulletPowerPistol extends AdvancedRobot {

	public void run(){
		equip("Pistol Test");
		while(true){
			setFire(this.getMaxBulletPower());
			execute();
		}
	}
	
}
