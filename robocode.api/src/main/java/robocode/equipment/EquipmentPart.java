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
			// All modifiers default to 0.
			for (RobotAttribute attribute : RobotAttribute.values()) {
				attributes.put(attribute, 0.0);
			}
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

		/**
		 * @param attribute the attribute to set the modifier of.
		 * @param value the modifier value; 1 represents +1% effectiveness.
		 * @return the builder object for chained modifications.
		 */
		public Builder set(RobotAttribute attribute, double value) {
			attributes.put(attribute, value / 100.0);
			return this;
		}
	}

	private EquipmentPart(Builder builder) {
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