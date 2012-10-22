package robocode.equipment;

import java.util.HashMap;
import java.util.Map;

import robocode.RobotAttribute;

/**
 * Implements the builder pattern for equipment parts, allowing the
 * configuration of optional attribute modifiers and resource file paths.
 */
public class EquipmentPartBuilder {
	final EquipmentSlot slot;
	String soundPath;
	String imagePath;
	Map<RobotAttribute, Double> attributes = new HashMap<RobotAttribute, Double>();

	/**
	 * @param slot the slot to be occupied by the part to be built.
	 */
	public EquipmentPartBuilder(EquipmentSlot slot) {
		this.slot = slot;
		// All modifiers default to 0.
		for (RobotAttribute attribute : RobotAttribute.values()) {
			attributes.put(attribute, 0.0);
		}
	}

	/**
	 * @return a new equipment part according to the state of this builder.
	 */
	public EquipmentPart build() {
		return new EquipmentPart(this);
	}

	/**
	 * @param path the path of the sound resource.
	 * @return the builder object for chained modifications.
	 */
	public EquipmentPartBuilder soundPath(String path) {
		this.soundPath = path;
		return this;
	}

	/**
	 * @param path the path of the image resource.
	 * @return the builder object for chained modifications.
	 */
	public EquipmentPartBuilder imagePath(String path) {
		this.imagePath = path;
		return this;
	}

	/**
	 * @param attribute the attribute to set the modifier of.
	 * @param value the modifier value; 1 represents +1% effectiveness.
	 * @return the builder object for chained modifications.
	 */
	public EquipmentPartBuilder set(RobotAttribute attribute, double value) {
		attributes.put(attribute, value / 100.0);
		return this;
	}
}