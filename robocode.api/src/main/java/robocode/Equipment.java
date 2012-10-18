package robocode;

import java.util.HashMap;
import java.util.Map;

/**
 * A container for all the parts that can be equipped by AdvancedRobots.
 *
 * @author Malcolm Inglis (CSSE2003)
 *
 */
public class Equipment {

	/**
	 * Maps names to EquipmentPart objects, such that the robot programmer can
	 * equip parts just by referring to their name.
	 */
	private static final Map<String, EquipmentPart> parts = new HashMap<String, EquipmentPart>();

	static {
		parts.put(
				"Division 9 Plasmaprojector",
				new EquipmentPart.Builder(EquipmentSlot.WEAPON)
						.sound("/net/sf/robocode/sound/sounds/pewpew.wav")
						.set(RobotAttribute.SPEED, -20)
						.set(RobotAttribute.GUN_HEAT_RATE, 70)
						.set(RobotAttribute.BULLET_DAMAGE, 90).build());

		parts.put(
				"Guardian Tank Armor",
				new EquipmentPart.Builder(EquipmentSlot.BODY)
						.set(RobotAttribute.ARMOR, 40)
						.set(RobotAttribute.SPEED, -20)
						.set(RobotAttribute.RAM_ATTACK, 10)
						.set(RobotAttribute.RAM_DEFENSE, 10).build());

		parts.put(
				"Thorium Power Cell",
				new EquipmentPart.Builder(EquipmentSlot.POWER)
						.set(RobotAttribute.ENERGY, 30)
						.set(RobotAttribute.ENERGY_REGEN, 50)
						.set(RobotAttribute.SPEED, -5).build());

		parts.put(
				"Stealth Tracks",
				new EquipmentPart.Builder(EquipmentSlot.TRACKS)
						.set(RobotAttribute.SPEED, 40)
						.set(RobotAttribute.GUN_HEAT_RATE, 50)
						.set(RobotAttribute.BULLET_SPEED, 90)
						.set(RobotAttribute.ENERGY, -60).build());

		parts.put(
				"Small Radar",
				new EquipmentPart.Builder(EquipmentSlot.RADAR)
						.set(RobotAttribute.ACCELERATION, 20)
						.set(RobotAttribute.RADAR_ANGLE, -25)
						.set(RobotAttribute.SCAN_RADIUS, 50)
						.set(RobotAttribute.SPEED, 30).build());

		parts.put(
				"Twin Turret",
				new EquipmentPart.Builder(EquipmentSlot.WEAPON)
						.set(RobotAttribute.SPEED, -10)
						.set(RobotAttribute.GUN_HEAT_RATE, -50)
						.set(RobotAttribute.BULLET_SPEED, 100)
						.set(RobotAttribute.ENERGY, -60)
						.image("/net/sf/robocode/ui/images/twinturret.png")
						.build());

		// add an new weapon option for robot and the RobotAttribute
		// will be set for the system to run properly
		parts.put(
				"Sword",
				new EquipmentPart.Builder(EquipmentSlot.WEAPON)
						.set(RobotAttribute.ACCELERATION, 20)
						.set(RobotAttribute.RADAR_ANGLE, -25)
						.set(RobotAttribute.SCAN_RADIUS, 50)
						.set(RobotAttribute.SPEED, 30)
						.image("/net/sf/robocode/ui/images/Sword.png").build());

		parts.put("Test", new EquipmentPart.Builder(EquipmentSlot.WEAPON)
				.sound("/net/sf/robocode/sound/sounds/pewpew.wav").build());

		/*
		 * Do not remove the following items or change them, as doing so would
		 * likely cause tests to fail.
		 */
		// Do not Remove, this will cause tests to fail.
		parts.put(
				"Plasma Test",
				new EquipmentPart.Builder(EquipmentSlot.WEAPON)
						.set(RobotAttribute.SPEED, -20)
						.set(RobotAttribute.GUN_HEAT_RATE, 70)
						.set(RobotAttribute.BULLET_DAMAGE, 90).build());

		// Do not remove, this will cause the tests to fail.
		parts.put(
				"Pistol Test",
				new EquipmentPart.Builder(EquipmentSlot.WEAPON)
						.set(RobotAttribute.ACCELERATION, 30)
						.set(RobotAttribute.BULLET_DAMAGE, -30)
						.set(RobotAttribute.BULLET_SPEED, 20)
						.set(RobotAttribute.GUN_HEAT_RATE, -20)
						.set(RobotAttribute.GUN_TURN_ANGLE, 40)
						.set(RobotAttribute.SPEED, 40)
						.set(RobotAttribute.ENERGY_REGEN, 30).build());
		
		// Do not remove, this will cause the tests to fail
		parts.put("Energy Test Large",
				new EquipmentPart.Builder(EquipmentSlot.BODY)
						.set(RobotAttribute.ENERGY, 20).build());
		
		// Do not remove, this will cause the tests to fail
				parts.put("Energy Test Small",
						new EquipmentPart.Builder(EquipmentSlot.BODY)
								.set(RobotAttribute.ENERGY, -20).build());
		
		// Do not remove, this will cause the tests to fail.
		parts.put("Test1", new EquipmentPart.Builder(EquipmentSlot.WEAPON)
				.sound("Test1").build());

		// Do not remove, this will cause the tests to fail.
		parts.put("Test2", new EquipmentPart.Builder(EquipmentSlot.WEAPON)
				.sound("Test2").build());

		/*
		 * End of Test equipment
		 */

	}

	/**
	 * @param name
	 *            the name of the part
	 * @return the part associated with the given name, or null if none
	 */
	public static EquipmentPart getPart(String name) {
		return parts.get(name);
	}
}
