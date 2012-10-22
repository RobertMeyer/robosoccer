package net.sf.robocode.battle.peer;

import robocode.BattleResults;

/**
 * SoccerTeamStatistics stores score and rank associated with a Soccer Team as
 * well as setters and getters for each.
 * 
 * @author kirstypurcell @ TeamG1
 */
public class SoccerTeamStatistics extends TeamStatistics {

	private final TeamPeer teamPeer;
	private int rank;
	private double score;

	public SoccerTeamStatistics(TeamPeer teamPeer) {
		super(teamPeer);
		this.teamPeer = teamPeer;
		score = 0;
	}

	/**
	 * Sets current rank to new rank.
	 * 
	 * @param rank
	 *            - the new rank to be assigned.
	 */
	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Returns total score of the team.
	 * 
	 * @return accumulated score.
	 */
	@Override
	public double getTotalScore() {
		return score;
	}

	@Override
	public void incrementScore() {
		score++;
	}

	/**
	 * Returns the final results
	 * 
	 * @return a BattleResults representation of the final team results.
	 */
	@Override
	public BattleResults getFinalResults() {
		return new BattleResults(teamPeer.getName(), rank, getTotalScore(), 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

}
