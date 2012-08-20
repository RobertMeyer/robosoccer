package robocode;

import net.sf.robocode.teleporters.ITeleportedEvent;

public final class TeleportedEvent extends Event implements ITeleportedEvent {
	
	// TODO: update interface, add getters for event.
	
	private final double fromX;
	private final double fromY;
	private final double toX;
	private final double toY;
	private final double previousBearing;
	private final double newBearing;

	/**
	 * Eclipse generated serial.
	 */
	private static final long serialVersionUID = 7129762240849651643L; // Ignore.
	
	public TeleportedEvent(double x1, double y1, double x2, double y2, 
			double bearing) {
		fromX = x1;
		fromY = y1;
		toX = x2;
		toY = y2;
		previousBearing = bearing;
		newBearing = bearing; // make new bearing perpendicular to portal.
	}
	
	public double getNewBearing() {
		return newBearing;
	}
	
}
