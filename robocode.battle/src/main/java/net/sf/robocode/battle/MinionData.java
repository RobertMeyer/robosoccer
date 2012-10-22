package net.sf.robocode.battle;

import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode._RobotBase;

/**
 * MinionData passes information between the minion UI and RobotPeer.
 * Also stores default information such as default minions.
 *
 * @author Jordan Henderson
 */
public class MinionData {
	private static Boolean minionsEnabled = false;
	private static Boolean isGui = false;
	private static String[] minionList = new String[]{"","",""};
	private static boolean insaneMode = false;
	//TODO: Get default minions from james.
	private static String[] defaultMinions = new String[]{"sample.TurretMinion","sample.DecoyMinion","sample.SuicideMinion"};

	/**
	 * Set a specific minion to a specified classname.
	 * @param minionStr - the minion classname.
	 * @param minionType
	 */
	public static void setMinion(String minionStr, int minionType) {
		if(!(minionType >= _RobotBase.MINION_TYPE_ATK && minionType < _RobotBase.MINION_TYPE_RND))
			return;
		if(minionStr.isEmpty())
			minionStr = defaultMinions[minionType];
		minionList[minionType] = minionStr;
	}
	
	/**
	 * Returns the list of minions for loading by robocode.
	 * @return a concatenated string containing the minions to load.
	 */
	public static String getMinions() {
		for(int i = 0; i <= 2; i++) {
			String minionStr = minionList[i];
			if(minionStr.isEmpty()) {
				minionList[i] = defaultMinions[i];
			}
		}
		String minions = minionList[0] + "," + minionList[1] + "," + minionList[2];
		return minions;
	}
	
	/**
	 * @return true if minions are enabled, false otherwise.
	 */
	public static boolean getMinionsEnabled() {
		return minionsEnabled;
	}
	
	/**
	 * Sets the minions enabled flag to the specified parameter.
	 * @param flag
	 */
	public static void setMinionsEnabled(boolean flag) {
		minionsEnabled = flag;
	}
	
	/**
	 * @return true if robocode is in GUI mode, false otherwise
	 */
	public static boolean getIsGui() {
		return isGui;
	}
	
	/**
	 * Sets the is in GUI mode flag to the specified parameter.
	 * @param flag
	 */
	public static void setIsGui(boolean flag) {
		isGui = flag;
	}
	
	/**
	 * Sets the insane mode flag to the specified parameter.
	 * @param flag
	 */
	public static void setInsaneMode(boolean flag) {
		insaneMode = flag;
	}
	
	/**
	 * @return true if insane mode is enabled, false otherwise.
	 */
	public static boolean getInsaneMode() {
		return insaneMode;
	}
}
