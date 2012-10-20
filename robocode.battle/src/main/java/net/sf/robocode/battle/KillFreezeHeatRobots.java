package net.sf.robocode.battle;

import java.util.List;

import robocode.control.snapshot.RobotState;

import net.sf.robocode.battle.peer.RobotPeer;

/**
 * This class kills all the heat and freeze robots on the battlefield.
 * They are only killed when there is 1 robot on the field that is neither a freeze
 * robot or a heat robot. 
 * 
 * @author Roan Coetzee CSSE2003
 *
 */
public class KillFreezeHeatRobots {

	/**
	 * Kills all freeze and heat robots on the battlefield if there is only 1
	 * non freeze or heat robot remaining on the battlefield
	 */
	public void killFreezeHeatRobot(List<RobotPeer> robotList) {
		// stores number of non freeze or heat robots
		int numberOfNonFreezeHeatBots = 0;
		// finds all the alive non freeze or heat robots
		for (int i = 0; i < robotList.size(); i++) {
			if (!robotList.get(i).isFreezeRobot()
					&& !robotList.get(i).isHeatRobot()
					&& robotList.get(i).isAlive()) {
				numberOfNonFreezeHeatBots++;
			}
		}
		// Checks if number of non freeze robots == 1
		if (numberOfNonFreezeHeatBots == 1) {
			// finds all the alive freeze and heat robots
			for (int i = 0; i < robotList.size(); i++) {
				if (robotList.get(i).isFreezeRobot()
						|| robotList.get(i).isHeatRobot()) {
					// kills the freeze and heat robots
					robotList.get(i).setState(RobotState.DEAD);
				}
			}
		}
	}

}
