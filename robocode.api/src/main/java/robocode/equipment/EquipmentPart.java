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

	/**
	 * Implements the builder pattern for equipment parts, allowing the
	 * configuration of optional attribute modifiers and resource file paths.
	 */
	public static class Builder {
		private final EquipmentSlot slot;
		private String soundPath;
		private String imagePath;

		private Map<RobotAttribute, Double> attributes = new HashMap<RobotAttribute, Double>();

		public Builder(EquipmentSlot slot) {
			this.slot = slot;
		}

		public EquipmentPart build() {
			return new EquipmentPart(this);
		}

		public Builder soundPath(String path) {
			this.soundPath = path;
			return this;
		}

		public Builder imagePath(String path) {
			this.imagePath = path;
			return this;
		}

		public Builder set(RobotAttribute attribute, double value) {
			attributes.put(attribute, value);
			return this;
		}

		public Double get(RobotAttribute attribute) {
			return attributes.get(attribute);
		}
	}

	private EquipmentPart(Builder builder) {
		slot = builder.slot;
		soundPath = builder.soundPath;
		imagePath = builder.imagePath;

		// Copy attributes from builder; default to 0 if attribute wasn't set.
		for (RobotAttribute attribute : RobotAttribute.values()) {
			Double value = builder.get(attribute);
			if (value == null) {
				value = Double.valueOf(0);
			}
			attributes.put(attribute, value);
		}
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
	 * @return the equipment slot this part may be equipped to
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}

	/**
	 * Returns the part's modifier for the given attribute. Part modifiers are
	 * defined as 1=+1% effectiveness.
	 * 
	 * @param attribute the attribute modifier value to return
	 * @return the part's modifier value for the given attribute
	 */
	public double get(RobotAttribute attribute) {
		return attributes.get(attribute);
	}
}