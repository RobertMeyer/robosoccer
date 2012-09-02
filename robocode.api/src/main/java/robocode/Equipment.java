package robocode;

import java.util.HashMap;
import java.util.Map;

/**
 * A container for all the parts that can be equipped by StatRobots.
 * TODO: equipping API and implementation.
 *
 * @author Malcolm Inglis (CSSE2003)
 *
 */
public class Equipment {

	/**
	 * Maps names to EquipmentPart objects, such that the robot programmer
	 * can refer to the defined EquipmentParts (by name), and equip them.
	 */
	private static final Map<String, EquipmentPart> parts =
			new HashMap<String, EquipmentPart>();

	static {
		parts.put("Division 9 Plasmaprojector",
				new EquipmentPart.Builder(EquipmentPartSlot.WEAPON)
					.set(RobotAttribute.SPEED, -20)
					.set(RobotAttribute.BULLET_DAMAGE, 200)
					.set(RobotAttribute.GUN_HEAT_RATE, 70)
					.build());

		parts.put("Guardian Tank Armor",
				new EquipmentPart.Builder(EquipmentPartSlot.BODY)
					.set(RobotAttribute.ARMOR, 40)
					.set(RobotAttribute.SPEED, -20)
					.set(RobotAttribute.RAM_ATTACK, 10)
					.set(RobotAttribute.RAM_DEFENSE, 10)
					.build());

		parts.put("Thorium Power Cell",
				new EquipmentPart.Builder(EquipmentPartSlot.POWER)
					.set(RobotAttribute.ENERGY, 30)
					.set(RobotAttribute.ENERGY_REGEN, 50)
					.set(RobotAttribute.SPEED, -5)
					.build());

		parts.put("Stealth Tracks",
				new EquipmentPart.Builder(EquipmentPartSlot.TRACKS)
					.set(RobotAttribute.SPEED, 40)
					.set(RobotAttribute.GUN_HEAT_RATE, 50)
					.set(RobotAttribute.BULLET_SPEED, 90)
					.set(RobotAttribute.ENERGY, -60)
					.build());
	}

	/**
	 * @param partName the name of the part
	 * @return the part associated with the given name, or null if none
	 */
	public static EquipmentPart getPart(String partName) {
		return parts.get(partName);
	}
}
