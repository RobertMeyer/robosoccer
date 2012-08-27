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
}
