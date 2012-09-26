package net.sf.robocode.battle;

import java.util.HashMap;
import java.util.Map;

import net.sf.robocode.battle.peer.RobotPeer;


public class KillstreakTracker {

	private Map<RobotPeer, Integer> killstreakRobots = new HashMap<RobotPeer, Integer>();
	private Battle battle;
	public static boolean enableKillstreaks = false;

	public KillstreakTracker(Battle b) {
		battle = b;
	}

	public void updateKillStreak(RobotPeer killer, RobotPeer victim) {
		if (killstreakRobots.size() == 0) {
			for (RobotPeer robot : battle.getRobotList()) {
				killstreakRobots.put(robot, 0);
			}
			System.out.println("size of killstreakRobots = " + killstreakRobots.size());
		}
		
		if (enableKillstreaks == false) {
			return;
		}
		
		int newKillstreak;
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
		killer.println("SYSTEM: Killstreak is now " + newKillstreak);
		victim.println("SYSTEM: Killstreak reset to 0");
		callKillstreaks();
	}

	private void callKillstreaks() {
		for ( RobotPeer robot : killstreakRobots.keySet()) {

			switch (killstreakRobots.get(robot)) {
			case 5:
				new AirStrike(robot, battle);
				break;
			}

		}
	}
	
	public static void enableKillstreaks(boolean b) {
		enableKillstreaks = b;
	}
}
