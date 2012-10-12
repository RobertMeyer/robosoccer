package net.sf.robocode.battle.peer;

import robocode.BattleResults;

public class SoccerTeamStatistics extends TeamStatistics{
	
	 private final TeamPeer teamPeer;
	 private int rank;
	 private double score;

	public SoccerTeamStatistics(TeamPeer teamPeer) {
		super(teamPeer);
		this.teamPeer = teamPeer;
		score = 0;
	}
	
	@Override
    public void setRank(int rank) {
        this.rank = rank;
    }
	
	@Override
	public double getTotalScore() {
		return score;
	}
	
	@Override
	public void incrementScore() {
		score++;
	}
	
	@Override
    public BattleResults getFinalResults() {
        return new BattleResults(teamPeer.getName(), rank, getTotalScore(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

}

