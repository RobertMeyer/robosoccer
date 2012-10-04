package net.sf.robocode.battle;

import java.util.HashMap;
import java.util.Map;

//import net.sf.robocode.battle.killstreaks.*;
import net.sf.robocode.battle.killstreaks.RadarJammer;
import net.sf.robocode.battle.killstreaks.AirStrike;
import net.sf.robocode.battle.killstreaks.RobotFreeze;
import net.sf.robocode.battle.killstreaks.SuperTank;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Tracks the robot's killstreaks if the checkbox is ticked (or if
 * enableKillstreaks is otherwise called)
 **/
public class KillstreakTracker {

	private Map<RobotPeer, Integer> killstreakRobots = new HashMap<RobotPeer, Integer>();
	private Battle battle;
	public static boolean enableKillstreaks = false;

	/* The killstreak abilities */
	private Map<Integer, IKillstreakAbility> killstreakAbilities;

	/**
	 * Constructor for the KillstreakTracker (called when battle is initialized)
	 * Initialize the default kill streaks
	 * 
	 * @param b
	 *            the battle the tracker is tracking
	 */
	public KillstreakTracker(Battle b) {
		battle = b;
		killstreakAbilities = new HashMap<Integer, IKillstreakAbility>();

		/* add the default killstreak abilities to the map */
		addKillstreakAbility(3, new RadarJammer());
		addKillstreakAbility(5, new AirStrike());
		addKillstreakAbility(7, new RobotFreeze());
		addKillstreakAbility(9, new SuperTank());

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
		/* get the current killstreak of the robot */
		int killCount = killstreakRobots.get(robot);

		/* call the killstreak ability associated with that bound (if it exists) */
		IKillstreakAbility ksAbility = killstreakAbilities.get(killCount);
		if (ksAbility != null) {
			ksAbility.callAbility(robot, battle);
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

	/**
	 * Add a callable killstreak ability to the set of abilities, to be called
	 * by a robot if it reaches 'bound' consecutive kills.
	 * 
	 * @param bound
	 *            The number of consecutive kills needed by the robot to call
	 *            the ability
	 * @param ksAbility
	 *            The IKillstreakAbility to add. It's callAbility method will be
	 *            called when 'bound' is reached.
	 */
	public void addKillstreakAbility(int bound, IKillstreakAbility ksAbility) {
		/* if there is already an ability at that bound, remove it */
		if (killstreakAbilities.get(bound) != null) {
			killstreakAbilities.remove(bound);
		}
		killstreakAbilities.put(bound, ksAbility);
	}
}
