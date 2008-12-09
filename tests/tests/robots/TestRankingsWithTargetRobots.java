/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *******************************************************************************/
package robots;


import static org.hamcrest.CoreMatchers.is;
import helpers.RobotTestBed;

import java.util.List;

import org.junit.Assert;

import robocode.battle.events.BattleFinishedEvent;
import robocode.battle.events.TurnEndedEvent;
import robocode.battle.snapshot.ScoreSnapshot;
import robocode.battle.snapshot.TurnSnapshot;


/**
 * This test is used for checking the rankings of two sample.Target robots, i.e.
 * robots that does not shot, but is just waiting for someone to shot them. As
 * two sample.Targets robots are not fighting each other, the battles are
 * expected to end fairly even, but not necessarily due to collisions between
 * the robots and the wall.
 * 
 * @author Flemming N. Larsen (original)
 */
public class TestRankingsWithTargetRobots extends RobotTestBed {

	TurnSnapshot lastTurnSnapshot;

	@Override
	public String getRobotNames() {
		return "sample.Target,sample.Target";
	}

	@Override
	public int getNumRounds() {
		return 20;
	}

	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		super.onTurnEnded(event);

		lastTurnSnapshot = event.getTurnSnapshot();
	}

	@Override
	public void onBattleEnded(BattleFinishedEvent event) {
		final List<ScoreSnapshot> scores = lastTurnSnapshot.getTeamScores();
		final ScoreSnapshot score1 = scores.get(0);
		final ScoreSnapshot score2 = scores.get(1);

		// 1sts + 2nds = number of rounds, e.g. 4 + 6 = 10 (where 4 is 1st places, and 6 is 2nd places)
		Assert.assertThat("1st ranked robot's total 1st and 2nd places must be equal to the number of rounds",
				score1.getTotalFirsts() + score1.getTotalSeconds(), is(getNumRounds()));
		Assert.assertThat("2nd ranked robot's total 1st and 2nd places must be equal to the number of rounds",
				score2.getTotalFirsts() + score2.getTotalSeconds(), is(getNumRounds()));

		// If 1st robot's 1sts = 6, 2nds = 4, then 2nd robot's 1sts = 4, 2nds = 6
		Assert.assertThat(
				"1st ranked robot's number of 1st places must be equal to the 2nd ranked robot's number of 2nd places",
				score1.getTotalFirsts(), is(score2.getTotalSeconds()));
		Assert.assertThat(
				"2nd ranked robot's number of 1st places must be equal to the 1st ranked robot's number of 2nd places",
				score2.getTotalFirsts(), is(score1.getTotalSeconds()));
	}
}
