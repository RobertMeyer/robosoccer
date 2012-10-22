package robocode.equipment;

import java.util.Map;
import java.util.HashMap;

import robocode.RobotAttribute;

/**
 * A piece of equipment, representing a set of robot attribute modifiers and
 * resource file paths.
 * 
 * @author CSSE2003 Team Forkbomb
 */
public class EquipmentPart {

	/** The slot that the part occupies on a robot. */
	private final EquipmentSlot slot;
	private final String soundPath;
	private final String imagePath;

	/**
	 * This map holds various attributes of the robot that the part modifies,
	 * where the attribute's value represents a percentage-based increase or
	 * decrease in the effectiveness of that attribute for the robot equipping
	 * this part.
	 */
	private Map<RobotAttribute, Double> attributes = new HashMap<RobotAttribute, Double>();

	public EquipmentPart(EquipmentPartBuilder builder) {
		slot = builder.slot;
		soundPath = builder.soundPath;
		imagePath = builder.imagePath;
		attributes = builder.attributes;
	}

	/**
	 * @return the file path of the sound file for this part, or null if no
	 *         sound path was configured.
	 */
	public String getSoundPath() {
		return soundPath;
	}

	/**
	 * @return the file path of the image file for this part, or null if no
	 *         image path was configured.
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @return the equipment slot that this part will occupy on the robot.
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}

	/**
	 * @param attribute
	 *            the attribute to return the modifier value of.
	 * @return the modifier value of the given attribute; 1.0 represents +100%
	 *         effectiveness.
	 */
	public double get(RobotAttribute attribute) {
		return attributes.get(attribute);
	}
}