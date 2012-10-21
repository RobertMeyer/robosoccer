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
public class ImageEquip1 extends AdvancedRobot {

	public void run(){
		equip("ImageEquipTest1");
		while(true){
			fire(3);
		}
	}
}
