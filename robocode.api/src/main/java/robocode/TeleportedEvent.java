package robocode;

import net.sf.robocode.teleporters.ITeleportedEvent;
import net.sf.robocode.teleporters.ITeleporter;

public final class TeleportedEvent extends Event implements ITeleportedEvent {	
	private final ITeleporter teleporter;

	/**
	 * Eclipse generated serial.
	 */
	private static final long serialVersionUID = 7129762240849651643L;
	
	/**
	 * Called to create a Teleport event.
	 * @param teleporter the teleporter.
	 */
	public TeleportedEvent(ITeleporter teleporter) {
		super();
		this.teleporter = teleporter;
	}
	
	/**
	 * Returns the teleporter that teleported the robot.
	 * @return a teleporter object.
	 */
	public ITeleporter getTeleporter() {
		return teleporter;
	}
}
