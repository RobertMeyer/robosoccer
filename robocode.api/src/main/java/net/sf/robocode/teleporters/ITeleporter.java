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
	 * Sets the teleporter's position to x & y coordinates. Requires both x and
	 * y coordinates. Use setX or setY for individual axes.
	 * 
	 * @param x
	 * @param y
	 */
	void setXY(double x, double y);
	
	/**
	 * Sets the teleporter's x position.
	 * 
	 * @param x
	 */
	void setX(double x);
	
	/**
	 * Sets the teleporter's y position.
	 * 
	 * @param y
	 */
	void setY(double y);
	
	/**
	 * Returns the x coordinate of the teleporter.
	 * 
	 * @return
	 */
	double getX();
	
	/**
	 * Get the y coordinate of the teleporter.
	 * 
	 * @return
	 */
	double getY();
	
	/**
	 * Forms a pair with another teleporter. The paired teleporter will be the
	 * destination of the current teleporter and vice versa.
	 * 
	 * @param pair
	 */
	void setPair(ITeleporter pair);
	
	/**
	 * Returns this teleporter's paired teleporter.
	 * 
	 * @return
	 */
	ITeleporter getPair();
}
