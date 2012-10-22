/**
 * 
 */
package tested.equipment;

import robocode.AdvancedRobot;

/**
 * @author Andrew
 *
 * Stub robot to test whether the image path for a piece of 
 * equipment is passed through the system correctly without errors
 */
public class ImageEquip2 extends AdvancedRobot {

	public void run(){
		equip("ImageEquipTest2");
		while(true){
			fire(3);
		}
	}
}
