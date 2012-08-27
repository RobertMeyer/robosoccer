package net.sf.robocode.mode;


/**
 * This interface defines all the methods in Robocode's ClassicMode.
 */
public interface IMode {

	/**
	 * Modifies the velocity of Robots in a battle
	 * @param velocityIncrement
	 * @return a modified Velocity
	 */
	public double modifyVelocity(double velocityIncrement);
	
	/**
	 * @return A string representation of a Mode
	 */
	public String toString();

}


