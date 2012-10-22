package robocode.control.snapshot;

public interface ITeleporterSnapshot {

	/**
	 * Returns the X position of the first teleporter.
	 *
	 * @return the X position of the first teleporter.
	 */
	double getPortal1X();

	/**
	 * Returns the Y position of the first teleporter.
	 *
	 * @return the Y position of the first teleporter.
	 */
	double getPortal1Y();

	/**
	 * Returns the X position of the second teleporter.
	 *
	 * @return the X position of the second teleporter.
	 */
	double getPortal2X();

	/**
	 * Returns the Y position of the second teleporter.
	 *
	 * @return the Y position of the second teleporter.
	 */
	double getPortal2Y();
	/**
	 * Return boolean based on whether it is a black hole
	 * @return boolean if is black hole
	 */
	boolean isBlackHole();

	/**
	 * Gets the width of the teleporter
	 * @return the width of the teleporter
	 */
	double getWidth();

	/**
	 * Gets the height of the teleporter
	 * @return the height of the teleporter
	 */
	double getHeight();

}
