package robocode.control.snapshot;

/**
 * Interface of an obstacle snapshot at a specific time in a battle.
 *
 * @author Michael Tsai
 * 
 */
public interface IObstacleSnapshot {

	/**
	 * Returns the X position of the obstacle.
	 *
	 * @return the X position of the obstacle.
	 */
	double getX();

	/**
	 * Returns the Y position of the obstacle.
	 *
	 * @return the Y position of the obstacle.
	 */
	double getY();
	
	/**
	 * Returns the height of the obstacle.
	 *
	 * @return the height of the obstacle.
	 */
	int getHeight();
	
	/**
	 * Returns the width of the obstacle.
	 *
	 * @return the width of the obstacle.
	 */
	int getWidth();

	/**
	 * Returns the color of the obstacle.
	 *
	 * @return an ARGB color value. (Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are blue)
	 * 
	 * @see java.awt.Color#getRGB()
	 */
	int getColor();

	/**
	 * Returns the ID of the obstacle used for identifying the obstacle in a collection of obstacles.
	 *
	 * @return the ID of the obstacle.
	 */
	int getObstacleId();

	/**
	 * @return contestantIndex of the victim, or -1 if no one hit the obstacle
	 */
	//might need this for obstacles that deals damage (i.e. spiked walls)
	int getVictimIndex();
}
