package net.sf.robocode.mode;

/**
 * 
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {
	
	private final String description = "Original robocode mode.";
	
	/**
	 * Modifies the velocity of Robots in a battle
	 * @param velocityIncrement
	 * @return a modified Velocity
	 */
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
	}
	
	/**
	 * @return Returns the string "Classic Mode"
	 */
	
	public String toString() {
		return "Classic Mode";
	}
	
	public String getDescription() {
		return description;
	}
	
}
