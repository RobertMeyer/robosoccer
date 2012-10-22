package net.sf.robocode.battle.peer;

import java.util.List;

/**
 * SoccerTeamPeer is the team peer for robots competing in Soccer Mode.
 * 
 * @author kirstypurcell @ TeamG1
 */
@SuppressWarnings("serial")
public class SoccerTeamPeer extends TeamPeer {

	private final SoccerTeamStatistics teamStatistics;

	/**
	 * Creates a SoccerTeamPeer
	 * 
	 * @param name
	 *            - name of the soccer team.
	 * @param memberNames
	 *            - a list of robots in the team
	 * @param teamIndex
	 *            - an integer key for the given team
	 */
	public SoccerTeamPeer(String name, List<String> memberNames, int teamIndex) {
		super(name, memberNames, teamIndex);
		this.teamStatistics = new SoccerTeamStatistics(this);
	}

	@Override
	public ContestantStatistics getStatistics() {
		return teamStatistics;
	}

}
