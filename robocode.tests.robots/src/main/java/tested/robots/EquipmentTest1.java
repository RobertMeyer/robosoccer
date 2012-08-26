package tested.robots;

import robocode.AdvancedRobot;
import robocode.Equipment;
import robocode.EquipmentSlot;

/**
 * Tests the equipment feature provided by Team Forkbomb.
 *
 * @author Malcolm Inglis (CSSE2003)
 *
 */
public class EquipmentTest1 extends AdvancedRobot {

	public void equipment() {
		equip(EquipmentSlot.BODY,
				Equipment.getPart("Guardian Tank Armor"));
	}

	public void run() {
		while (true) {
			ahead(100);
			back(100);
		}
	}
}
