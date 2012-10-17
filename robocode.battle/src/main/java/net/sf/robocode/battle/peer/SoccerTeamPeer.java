package net.sf.robocode.battle.peer;

import java.util.List;

@SuppressWarnings("serial")
public class SoccerTeamPeer extends TeamPeer{
	
	/* TODO Score rework */
//	 private final TeamStatistics teamStatistics;

	public SoccerTeamPeer(String name, List<String> memberNames, int teamIndex) {
		super(name, memberNames, teamIndex);
//		this.teamStatistics = new SoccerTeamStatistics(this);
	}
	
//	@Override
//    public ContestantStatistics getStatistics() {
//        return teamStatistics;
//    }

}
