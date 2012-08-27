package robocode;

import java.util.Map;
import java.util.HashMap;

/**
 * A piece of equipment; an association of a value with every robot attribute.
 * 
 * @author Malcolm Inglis (CSSE2003)
 * @see Equipment
 * 
 */
public class EquipmentPart {
	
	private final EquipmentSlot slot;
	private final String imagePath;

	/**
	 * This map holds various attributes of the robot that the part modifies,
	 * where the attribute's value represents a percentage-based increase or
	 * decrease in the effectiveness of that attribute for the robot equipping
	 * this part.
	 */
	private Map<RobotAttribute, Double> attributes =
			new HashMap<RobotAttribute, Double>();

	public static class Builder {
		// Required for builder initialization
		private final EquipmentSlot slot;

		private Map<RobotAttribute, Double> attributes =
				new HashMap<RobotAttribute, Double>();
		
		private String imagePath;

		public Builder(EquipmentSlot slot) {
			this.slot = slot;
		}

		public EquipmentPart build() {
			return new EquipmentPart(this);
		}
		
		public Builder image(String path) {
			imagePath = path;
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
	 * @return the equipment slot this part may be equipped to
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}
	
	/**
	 * @return the path to the image to be used for this part
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Returns the part's modifier for the given attribute. Part modifiers
	 * are defined as 1=+1% effectiveness.
	 * 
	 * @param attribute the attribute modifier value to return
	 * @return the part's modifier value for the given attribute
	 */
	public double get(RobotAttribute attribute) {
		return attributes.get(attribute);
	}
}
