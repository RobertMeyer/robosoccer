package net.sf.robocode.battle;

/**
 * 
 * @author Team Omega
 *
 */
public class TeleporterEnabler {
	private static boolean teleportersEnabled = false;
	private static boolean blackholesEnabled = false;
	
	/**
	 * 
	 * @param enable
	 */
	public static void enableTeleporters(boolean enable) {
		teleportersEnabled = enable;
		System.out.println("t enabled");
	}
	
	/**
	 * 
	 * @param enable
	 */
	public static void enableBlackholes(boolean enable) {
		blackholesEnabled = enable;
		System.out.println("b enabled");
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isTeleportersEnabled() {
		return teleportersEnabled;
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isBlackholesEnabled() {
		return blackholesEnabled;
	}
}
