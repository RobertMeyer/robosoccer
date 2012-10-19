package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot, that equips an item in every slot, to ensure
 * that it starts on the field, etc.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class AllSlots extends AdvancedRobot {
	
	/**
	 * The main run method.
	 */
	public void run(){
		equip("Plasma Test");
		equip("Energy Test Large");
		equip("Thorium Power Cell"); // add a test one here
		equip("Stealth Tracks"); // add a test one here
		equip("Gun Test Large");
	}
}
