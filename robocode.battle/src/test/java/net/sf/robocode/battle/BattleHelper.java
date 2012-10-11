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
package net.sf.robocode.battle;

import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;
import static org.mockito.Mockito.*;
import robocode.BattleRules;
import robocode.BattleRulesForTest;

/**
 *
 * @author lee
 */
public class BattleCreatorForTest {

    public static Battle createBasicBattle() {
        ISettingsManager properties = mock(ISettingsManager.class);
        IBattleManager battleManager = mock(IBattleManager.class);
        IHostManager hostManager = mock(IHostManager.class);
        IRepositoryManager repositoryManager = mock(IRepositoryManager.class);
        ICpuManager cpuManager = mock(ICpuManager.class);
        BattleEventDispatcher eventDispatcher = mock(BattleEventDispatcher.class);
        Battle b = new Battle(properties, battleManager, hostManager, repositoryManager, cpuManager, eventDispatcher);
        return b;
    }


    public static Battle setBattleRules(Battle b, BattleRulesForTest newRules) {
        b.battleRules = newRules.createBattleRules();
        return b;
    }

    public static BattleRules getBattleRules(Battle b) {
        return b.battleRules;
    }
}
