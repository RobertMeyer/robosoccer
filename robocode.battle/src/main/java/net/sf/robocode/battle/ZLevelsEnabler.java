package net.sf.robocode.battle;

public class ZLevelsEnabler {

	private static boolean zRand = false;
	
	public static void enableZRand(boolean b) {
		zRand = b;
	}
	
	public static boolean getZRand() {
		return zRand;
	}
}
