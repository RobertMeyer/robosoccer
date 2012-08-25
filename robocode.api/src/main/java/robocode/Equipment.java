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
	public static final Map<String, EquipmentPart> equipment =
			new HashMap<String, EquipmentPart>();

	static {
		
		equipment.put("Division 9 Plasmaprojector",
				new EquipmentPart.Builder(EquipmentPart.Position.WEAPON)
					.speed(-20)
					.bulletDamage(200)
					.bulletRate(-70)
					.build());
		
		equipment.put("Guardian Tank Armor",
				new EquipmentPart.Builder(EquipmentPart.Position.BODY)
					.armor(40)
					.speed(-20)
					.ramAttack(10)
					.ramDefense(80)
					.build());
		
		equipment.put("Thorium Power Cell",
				new EquipmentPart.Builder(EquipmentPart.Position.POWER)
					.energy(30)
					.energyRegen(50)
					.speed(-5)
					.build());
	}
}
