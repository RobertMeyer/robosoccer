package net.sf.robocode.mode;

/**
 * Basic Construct for the CTF mode:
 *  Contains methods for initialising settings
 *  
 * 
 * @author Team Telos
 *
 */
public class FlagMode extends ClassicMode {
	
	/* ATTENTION FLAGMODE PEOPLE
	 * From Team-IZO
	 * Your mode needs to extend ClassicMode not implement IMode.
	 * ClassicMode has default behaviour. This class already implements IMode.
	 * If you don't, you will have to implement everything that ClassicMode does, otherwise, you can just
	 * implement the methods that you want to change. This is a problem because now we've added a new feature
	 * where each mode has a method getDescription(), which returns a String that describes the mode,
	 * and is displayed in the UI. You, and future modes, don't have to implement this right away, because you will
	 * inherit the default method from ClassicMode.
	 * 
	 * tl;dr: Your mode must extend ClassicMode, not implement IMode, or you will break the code when we add stuff.
	 * I've gone and switched it, I am going to contact your group to make sure it's ok.
	 */
	
	// Class Variables
	private double pointLimit;
	private double timeLimit;
	
	public void execute() {
		System.out.println("Capture The Flag");
	}
	
	/**
	 * 
	 * @param pointSet
	 */
	public void setPointLimit(double pointSet) {
		// Called in gui, pointSet is set to the forms' value
		this.pointLimit = pointSet;
		return;
	}
	
	/**
	 * 
	 * @param timeSet
	 */
	public void setTimeLimit(double timeSet) {
		/* 
		* Called in gui, timeSet is set to the forms' value
		* Round time entered in seconds, then it can be converted to turns
		* to allow for time scaling in robocode
		*/
		this.timeLimit = timeSet;
		return;
	}
	
	/**
	 * 
	 * @return the point limit
	 */
	public double getPointLimit() {
		return this.pointLimit;
	}
	
	/**
	 * 
	 * @return the time limit
	 */
	public double getTimeLimit() {
		return this.timeLimit;
	}

	@Override
	public double modifyVelocity(double velocityIncrement) {
		// Maybe upon pick up flag make slightly slower.
		return 0;
	}

}
