/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Luis Crespo
 *     - Added getCurrentScore()
 *     Flemming N. Larsen
 *     - Renamed method names and removed unused methods
 *     - Added methods for getting current scores
 *******************************************************************************/
package net.sf.robocode.battle.peer;

import robocode.BattleResults;

/**
 * @author Mathew A. Nelson (original)
 * @author Luis Crespo (contributor)
 * @author Flemming N. Larsen (contributor)
 */
public interface ContestantStatistics {

    double getTotalScore();

    double getTotalSurvivalScore();

    double getTotalLastSurvivorBonus();

    double getTotalBulletDamageScore();

    double getTotalBulletKillBonus();

    double getTotalRammingDamageScore();

    double getTotalRammingKillBonus();
    
    double getTotalFlagScore();

    int getTotalFirsts();

    int getTotalSeconds();

    int getTotalThirds();

	int getTotalKills();

    double getCurrentScore();

    double getCurrentSurvivalScore();

    double getCurrentSurvivalBonus();

    double getCurrentBulletDamageScore();

    double getCurrentBulletKillBonus();

    double getCurrentRammingDamageScore();

    double getCurrentRammingKillBonus();
    
    double getCurrentFlagScore();
    
    int getCurrentKills();
    
    BattleResults getFinalResults();

    void setRank(int rank);

	void incrementScore();

	double getCurrentRaceScore();

}
