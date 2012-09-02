package net.sf.robocode.mode;

import java.util.List;
import net.sf.robocode.peer.*;


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
	 * Returns a list of integers representing the item id's to 
	 * spawn in the beginning of the round
	 * @return list of item id's
	 */
	public List<Integer> getItemIds();
	
	/**
	 * Create a list of integers representing the item id's to
	 * spawn in the beginning of the round
	 */
	public void setItemIds();
}
