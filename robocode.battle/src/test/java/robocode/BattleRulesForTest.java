/*
 *  Copyright (C) 2012 lee
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package robocode;

import java.util.Hashtable;
import robocode.BattleRules;

/**
 *
 * @author lee
 */
public class BattleRulesForTest {

    private int battlefieldWidth;
    private int battlefieldHeight;
    private int numRounds;
    private double gunCoolingRate;
    private long inactivityTime;
    private boolean hideEnemyNames;
    private Hashtable<String, Object> modeRules;

    public BattleRules createBattleRules() {
        return BattleRules.createHiddenHelper().createRules(battlefieldWidth,
                                                            battlefieldHeight,
                                                            numRounds,
                                                            gunCoolingRate,
                                                            inactivityTime,
                                                            hideEnemyNames,
                                                            modeRules);
    }

    public int getBattlefieldWidth() {
        return battlefieldWidth;
    }

    public void setBattlefieldWidth(int battlefieldWidth) {
        this.battlefieldWidth = battlefieldWidth;
    }

    public int getBattlefieldHeight() {
        return battlefieldHeight;
    }

    public void setBattlefieldHeight(int battlefieldHeight) {
        this.battlefieldHeight = battlefieldHeight;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    public double getGunCoolingRate() {
        return gunCoolingRate;
    }

    public void setGunCoolingRate(double gunCoolingRate) {
        this.gunCoolingRate = gunCoolingRate;
    }

    public long getInactivityTime() {
        return inactivityTime;
    }

    public void setInactivityTime(long inactivityTime) {
        this.inactivityTime = inactivityTime;
    }

    public boolean isHideEnemyNames() {
        return hideEnemyNames;
    }

    public void setHideEnemyNames(boolean hideEnemyNames) {
        this.hideEnemyNames = hideEnemyNames;
    }

    public Hashtable<String, Object> getModeRules() {
        return modeRules;
    }

    public void setModeRules(Hashtable<String, Object> modeRules) {
        this.modeRules = modeRules;
    }
}
