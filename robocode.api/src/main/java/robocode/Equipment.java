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
     * Maps names to EquipmentPart objects, such that the robot programmer
     * can equip parts just by referring to their name.
     */
    private static final Map<String, EquipmentPart> parts =
            new HashMap<String, EquipmentPart>();

    static {
        parts.put("Division 9 Plasmaprojector",
                  new EquipmentPart.Builder(EquipmentSlot.WEAPON)
        		.set(RobotAttribute.SPEED, -20)
        		.set(RobotAttribute.GUN_HEAT_RATE, 70)
                .set(RobotAttribute.BULLET_DAMAGE, 90)
                .build());

        parts.put("Guardian Tank Armor",
                  new EquipmentPart.Builder(EquipmentSlot.BODY)
                .set(RobotAttribute.ARMOR, 40)
                .set(RobotAttribute.SPEED, -20)
                .set(RobotAttribute.RAM_ATTACK, 10)
                .set(RobotAttribute.RAM_DEFENSE, 10)
                .build());

        parts.put("Thorium Power Cell",
                  new EquipmentPart.Builder(EquipmentSlot.POWER)
                .set(RobotAttribute.ENERGY, 30)
                .set(RobotAttribute.ENERGY_REGEN, 50)
                .set(RobotAttribute.SPEED, -5)
                .build());
        
        parts.put("Stealth Tracks",
                  new EquipmentPart.Builder(EquipmentSlot.TRACKS)
                .set(RobotAttribute.SPEED, 40)
                .set(RobotAttribute.GUN_HEAT_RATE, 50)
                .set(RobotAttribute.BULLET_SPEED, 90)
                .set(RobotAttribute.ENERGY, -60)
                .build());
        
        parts.put("Small Radar",
        		new EquipmentPart.Builder(EquipmentSlot.RADAR)
        		.set(RobotAttribute.ACCELERATION, 20)
        		.set(RobotAttribute.RADAR_ANGLE, -25)
        		.set(RobotAttribute.SCAN_RADIUS, 50)
        		.set(RobotAttribute.SPEED, 30)
        		.build());
        
        parts.put("Pistol",
        		new EquipmentPart.Builder(EquipmentSlot.WEAPON)
        		.set(RobotAttribute.ACCELERATION, 30)
        		.set(RobotAttribute.BULLET_DAMAGE, -30)
        		.set(RobotAttribute.BULLET_SPEED, 20)
        		.set(RobotAttribute.GUN_HEAT_RATE, -20)
        		.set(RobotAttribute.GUN_TURN_ANGLE, 40)
        		.set(RobotAttribute.SPEED, 40)
        		.build());
        
        parts.put("Test", 
        		  new EquipmentPart.Builder(EquipmentSlot.WEAPON)
        		.sound("/robocode.sound/src/main/resources/net/sf/robocode/sound/sounds/zap.wav")
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
