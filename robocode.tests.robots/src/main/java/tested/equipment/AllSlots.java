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
		equip("Division 9 Plasmaprojector");
		equip("Guardian Tank Armor");
		equip("Thorium Power Cell");
		equip("Stealth Tracks");
		equip("Small Radar");
	}
}
