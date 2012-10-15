package net.sf.robocode.teleporters;

/**
 * Work in Progress
 * A teleporter interface.
 * 
 * @author Team OMeGA
 *
 */
public interface ITeleporter {
	
	/**
	 * Enumerator used to distinguish between the two teleporters.
	 * Use PORTAL1 for the first and PORTAL2 for the second.
	 */
	public enum Portal {PORTAL1, PORTAL2};
	
	/**
	 * Sets the teleporter's position to x & y coordinates. 
	 * Use setX or setY for individual axes.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param target 
	 */
	void setXY(double x, double y, Portal target);
	
	/**
	 * Sets the teleporter's x position.
	 * 
	 * @param x
	 */
	void setX(double x, Portal target);
	
	/**
	 * Sets the teleporter's y position.
	 * 
	 * @param y
	 */
	void setY(double y, Portal target);
	
	/**
	 * Returns the x coordinate of the teleporter.
	 * 
	 * @return
	 */
	double getX(Portal target);
	
	/**
	 * Get the y coordinate of the teleporter.
	 * 
	 * @return
	 */
	double getY(Portal target);
	
	/**
	* Checks to see if the current teleporter is a black hole
	* @return true if is a black hole, false if it is not 
	*/
	boolean isBlackHole();
	
	/**
	* Sets a new width and height for the black hole
	*/
	void updateBlackHoleSize();
	
	/**
	 * Gets and returns the width of the teleporter
	 * @return the width of the teleporter
	 */
	double getWidth();
	/**
	 * Gets and returns the height of the teleporter
	 * @return the height of the teleporter
	 */
	double getHeight();
	
	
	
}
