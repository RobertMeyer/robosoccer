package robocode.control.snapshot;

/**
 * Interface of an obstacle snapshot at a specific time in a battle.
 *
 * @since 1.6.2
 */
public interface IObstacleSnapshot {

	/**
	 * Returns the obstacle state.
	 *
	 * @return the obstacle state.
	 */
	//BulletState getState();

	/**
	 * Returns the bullet power.
	 *
	 * @return the bullet power.
	 */
	//double getPower();

	/**
	 * Returns the X position of the obstacle.
	 *
	 * @return the X position of the obstacle.
	 */
	double getX();

	/**
	 * Returns the Y position of the bullet.
	 *
	 * @return the Y position of the bullet.
	 */
	double getY();
	
	/**
	 * Returns the height of the obstacle.
	 *
	 * @return the height of the obstacle.
	 */
	double getHeight();
	
	/**
	 * Returns the width of the bullet.
	 *
	 * @return the width of the bullet.
	 */
	double getWidth();

	/**
	 * Returns the X painting position of the bullet.
	 * Note that this is not necessarily equal to the X position of the bullet, even though
	 * it will be in most cases. The painting position of the bullet is needed as the bullet
	 * will "stick" to its victim when it has been hit, but only visually. 
	 *
	 * @return the X painting position of the bullet.
	 */
	double getPaintX();

	/**
	 * Returns the Y painting position of the bullet.
	 * Note that this is not necessarily equal to the Y position of the bullet, even though
	 * it will be in most cases. The painting position of the bullet is needed as the bullet
	 * will "stick" to its victim when it has been hit, but only visually. 
	 *
	 * @return the Y painting position of the bullet.
	 */
	double getPaintY();

	/**
	 * Returns the color of the bullet.
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
	 * @return heading of the bullet
	 */
	//double getHeading();

	/**
	 * @return contestantIndex of the victim, or -1 if no one hit the obstacle
	 */
	//might need this for obstacles that deals damage (i.e. spiked walls)
	int getVictimIndex();
}
