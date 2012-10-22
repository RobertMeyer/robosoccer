package net.sf.robocode.battle;

/**
 * A class responsible for enabling or disbaling teleporters in battles.
 * @author Team Omega
 *
 */
public class TeleporterEnabler {
	private static boolean teleportersEnabled = false;
	private static boolean blackholesEnabled = false;

	/**
	 * Enable teleporters in the battle.
	 * @param enable true to enable, false to disable.
	 */
	public static void enableTeleporters(boolean enable) {
		teleportersEnabled = enable;
	}

	/**
	 * Enable blackholes in the battle.
	 * @param enable true to enable, false to disable.
	 */
	public static void enableBlackholes(boolean enable) {
		blackholesEnabled = enable;
	}

	/**
	 * Check if teleporters are enabled.
	 * @return true if enabled.
	 */
	public static boolean isTeleportersEnabled() {
		return teleportersEnabled;
	}

	/**
	 * Check if blackholes are enabled. 
	 * @return true if enabled.
	 */
	public static boolean isBlackholesEnabled() {
		return blackholesEnabled;
	}
}
