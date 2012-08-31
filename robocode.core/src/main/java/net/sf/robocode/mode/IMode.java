package net.sf.robocode.mode;


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
	 * Respawns the robot in a random place.
	 */
	public void respawn();
}
