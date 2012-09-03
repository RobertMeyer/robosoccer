package net.sf.robocode.mode;

import java.util.List;

/**
 * This interface defines all the methods in every Mode in Robocode.
 */
public interface IMode {

	/**
	 * Modifies the velocity of Robots in a battle
	 * @param velocityIncrement
	 * @return a modified Velocity
	 */
	public double modifyVelocity(double velocityIncrement);
	
	/**
	 * Returns a string representation of the current Mode
	 * @return string representation of a Mode
	 */
	public String toString();
	
	/**
	 * Returns a string representing the Mode's description.
	 * @return String description
	 */
	public String getDescription();
	
	/**
	 * Returns a list of String of the item to 
	 * spawn in the beginning of the round
	 * @return list of items
	 */
	public List<String> getItems();
	
	/**
	 * Create a list of Strings representing the items to
	 * spawn in the beginning of the round
	 */
	public void setItems();
	
	/**
	 * Increments the score specific to the different modes
	 */
	public void scorePoints();
}
