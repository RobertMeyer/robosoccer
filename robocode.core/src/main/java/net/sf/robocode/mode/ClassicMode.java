package net.sf.robocode.mode;

/**
 * 
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {
<<<<<<< HEAD
=======
	
	private final String description = "Original robocode mode.";
	
	public void execute(){
		System.out.println("Classic Mode!!!");
	}
>>>>>>> master


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
		return new String("Classic Mode");
	}
	
	public String getDescription() {
		return new String(description);
	}
	
}
