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
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;
import static org.mockito.Mockito.*;
import robocode.BattleRules;
import robocode.BattleRulesForTest;

/**
 *
 * @author lee
 */
public class BattleHelper {

    public static BattleHelper createBasicBattleWithHelper() {
        ISettingsManager properties = mock(ISettingsManager.class);
        IBattleManager battleManager = mock(IBattleManager.class);
        IHostManager hostManager = mock(IHostManager.class);
        IRepositoryManager repositoryManager = mock(IRepositoryManager.class);
        ICpuManager cpuManager = mock(ICpuManager.class);
        BattleEventDispatcher eventDispatcher = mock(BattleEventDispatcher.class);
        return new BattleHelper(properties, battleManager, hostManager, repositoryManager, cpuManager, eventDispatcher);
    }

    public static Battle createBasicBattle() {
        ISettingsManager properties = mock(ISettingsManager.class);
        IBattleManager battleManager = mock(IBattleManager.class);
        IHostManager hostManager = mock(IHostManager.class);
        IRepositoryManager repositoryManager = mock(IRepositoryManager.class);
        ICpuManager cpuManager = mock(ICpuManager.class);
        BattleEventDispatcher eventDispatcher = mock(BattleEventDispatcher.class);
        Battle battle = new Battle(properties, battleManager, hostManager, repositoryManager, cpuManager, eventDispatcher);
        battle.battleMode = new ClassicMode();
        return battle;
    }

    public static Battle setBattleRules(Battle b, BattleRulesForTest newRules) {
        b.battleRules = newRules.createBattleRules();
        return b;
    }

    public static BattleRules getBattleRules(Battle b) {
        return b.battleRules;
    }
    public final ISettingsManager properties;
    public final IBattleManager battleManager;
    public final IHostManager hostManager;
    public final IRepositoryManager repositoryManager;
    public final ICpuManager cpuManager;
    public final BattleEventDispatcher eventDispatcher;
    public final Battle battle;

    private BattleHelper(ISettingsManager properties, IBattleManager battleManager, IHostManager hostManager, IRepositoryManager repositoryManager, ICpuManager cpuManager, BattleEventDispatcher eventDispatcher) {
        this.properties = properties;
        this.battleManager = battleManager;
        this.hostManager = hostManager;
        this.repositoryManager = repositoryManager;
        this.cpuManager = cpuManager;
        this.eventDispatcher = eventDispatcher;
        battle = new Battle(properties, battleManager, hostManager, repositoryManager, cpuManager, eventDispatcher);
    }
}
