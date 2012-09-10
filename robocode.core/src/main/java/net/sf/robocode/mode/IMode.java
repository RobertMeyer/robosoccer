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
	 * Add's mode specific robots to the list of selected robots.
	 * 
	 * @param current list of selected robots in the form: 
	 * "robots.myRobot*,robots.yourRobot*"...
	 * @return list of selected robots plus any modeSpecific robots appended.
	 */
	public String addModeRobots(String selectedRobots);

}
