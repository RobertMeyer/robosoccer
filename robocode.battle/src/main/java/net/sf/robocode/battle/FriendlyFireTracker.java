/**
 * @author Jonathan W
 * contains a flag to check if the FriendlyFireCheckbox is checked. Return a true if the box is checked, false otherwise
 */

package net.sf.robocode.battle;

public class FriendlyFireTracker {
	public static boolean enableFriendlyfire = false;
	
	public static void enableFriendlyFire(boolean b){
		enableFriendlyfire = b;
	}

}
