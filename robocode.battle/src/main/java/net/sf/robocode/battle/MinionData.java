package net.sf.robocode.battle;

import robocode._RobotBase;

public class MinionData {
	public static boolean minionsEnabled = false;
	public static String[] minionList = new String[]{"","",""};
	//TODO: Get default minions from james.
	public static String[] defaultMinions = new String[]{"minions.MinionBot*","minions.MinionBot*","minions.MinionBot*"};
	public static void setMinionsEnabled(boolean flag) {
		minionsEnabled = flag;
	}
	public static void setMinion(String minionStr, int minionType) {
		if(!(minionType >= _RobotBase.MINION_TYPE_ATK && minionType < _RobotBase.MINION_TYPE_RND))
			return;
		if(minionStr.isEmpty())
			minionStr = defaultMinions[minionType];
		minionList[minionType] = minionStr;
	}
	
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

}
