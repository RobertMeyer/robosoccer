/**
 * @author CSSE2003 Team MCJJ
 * contains a flag to check if the TeamCollisionCheckbox is checked. Return a true if the box is checked, false otherwise
 */
package net.sf.robocode.battle;

public class TeamCollisionTracker {
	public static boolean enableteamCollision = false;
	
	public static void enableFriendlyFire(boolean b){
		enableteamCollision = b;
	}
	
}
