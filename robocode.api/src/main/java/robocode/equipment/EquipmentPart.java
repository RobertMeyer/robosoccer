package robocode.equipment;

import java.util.Map;
import java.util.HashMap;

import robocode.RobotAttribute;

/**
 * A piece of equipment. This class defines all the stats that a piece of
 * equipment can change. Note that the actual mechanical change is performed in
 * the StatRobotPeer class.
 * 
 * @author Malcolm Inglis (CSSE2003)
 * 
 */
public class EquipmentPart {

	/** The position that the part occupes on the robot. */
	private final EquipmentSlot slot;
	private final String soundPath;
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
		private String soundPath;
		private String imagePath;

		private Map<RobotAttribute, Double> attributes =
				new HashMap<RobotAttribute, Double>();

		public Builder(EquipmentSlot slot) {
			this.slot = slot;
		}

		public EquipmentPart build() {
			return new EquipmentPart(this);
		}
		
		public Builder sound(String path){
			this.soundPath = path;
			return this;
		}
		
		public Builder image(String path){
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
	
	public String getSoundPath(){
		return soundPath;
	}
	
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