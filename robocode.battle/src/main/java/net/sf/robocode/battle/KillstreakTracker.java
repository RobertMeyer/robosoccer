package net.sf.robocode.battle;

import java.util.HashMap;
import java.util.Map;

import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Tracks the robot's killstreaks if the checkbox is ticked (or if
 * enableKillstreaks is otherwise called)
 **/
public class KillstreakTracker {

	private Map<RobotPeer, Integer> killstreakRobots = new HashMap<RobotPeer, Integer>();
	private Battle battle;
	public static boolean enableKillstreaks = false;

	/**
	 * Constructor for the KillstreakTracker (called when battle is initialized)
	 * 
	 * @param b
	 *            the battle the tracker is tracking
	 */
	public KillstreakTracker(Battle b) {
		battle = b;
	}

	/**
	 * Updates the robots killstreaks when one is killed, resets the victim's
	 * killstreak and increments the killers
	 * 
	 * @param killer
	 *            The robot who got the kill
	 * @param victim
	 *            The robot who got killed
	 */
	public void updateKillStreak(RobotPeer killer, RobotPeer victim) {
		int newKillstreak;
		if (enableKillstreaks == false) {
			return;
		}

		if (killstreakRobots.containsKey(killer)) {
			newKillstreak = killstreakRobots.get(killer);
			newKillstreak++;
			killstreakRobots.put(killer, newKillstreak);

		} else {
			newKillstreak = 1;
			killstreakRobots.put(killer, 1);
		}

		if (killstreakRobots.containsKey(victim)) {
			killstreakRobots.put(victim, 0);
		}

		/* Print disabled due to failing tests */
		killer.println("KILLSTREAK: Killstreak is now " + newKillstreak);
		victim.println("KILLSTREAK: Killstreak reset to 0");
		callKillstreak(killer);
	}

	/**
	 * Calls the killstreak if the killer has one
	 * 
	 * @param robot
	 *            The robot who got the kill
	 */
	private void callKillstreak(RobotPeer robot) {

		/* check the killers new killstreak */
		switch (killstreakRobots.get(robot)) {
		case 1:
			new RobotFreeze(robot, battle);
			break;
		case 3:
			new RadarJammer(robot, battle);
			break;
		case 5:
			new AirStrike(robot, battle);
			break;
		}
	}

	/**
	 * Set the enabled flag for the killstreak tracker. Without the flag,
	 * killstreaks will not be updated or called. Enable with the checkbox
	 * in-battle
	 * 
	 * @param b
	 *            the boolean to set the flag
	 */
	public static void enableKillstreaks(boolean b) {
		enableKillstreaks = b;
	}
}
