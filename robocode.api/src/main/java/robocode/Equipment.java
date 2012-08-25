package robocode;

import java.util.HashMap;
import java.util.Map;

/**
 * A container for all the parts that can be equipped by StatRobots.
 * TODO: equipping API and implementation.
 * 
 * @author Malcolm Inglis
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
				new EquipmentPart.Builder(EquipmentPart.Position.WEAPON)
					.speed(-20)
					.bulletDamage(200)
					.bulletRate(-70)
					.build());
		
		parts.put("Guardian Tank Armor",
				new EquipmentPart.Builder(EquipmentPart.Position.BODY)
					.armor(40)
					.speed(-20)
					.ramAttack(10)
					.ramDefense(80)
					.build());
		
		parts.put("Thorium Power Cell",
				new EquipmentPart.Builder(EquipmentPart.Position.POWER)
					.energy(30)
					.energyRegen(50)
					.speed(-5)
					.build());
	}
	
	/**
	 * @param partName the name of the part
	 * @return the part associated with the given name, or null if none
	 */
	public EquipmentPart getPart(String partName) {
		return parts.get(partName);
	}
}
