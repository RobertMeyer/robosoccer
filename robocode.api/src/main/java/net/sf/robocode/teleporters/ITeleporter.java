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
	 * Use Portal.PORTAL1 for the first and Portal.PORTAL2 for the second.
	 * 
	 */
	public enum Portal {PORTAL1, PORTAL2};
	
	/**
	 * Sets the teleporter's position to x & y coordinates. Requires both x and
	 * y coordinates. Use setX or setY for individual axes.
	 * 
	 * @param x
	 * @param y
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
}
