/**
 * @author CSSE2003 Team MCJJ
 * A flag to check for checkbox
 */
package net.sf.robocode.battle;

public class TeamCollisionTracker {
	public static boolean enableteamCollision = false;
	
	public static void enableFriendlyFire(boolean b){
		enableteamCollision = b;
	}
	
}
