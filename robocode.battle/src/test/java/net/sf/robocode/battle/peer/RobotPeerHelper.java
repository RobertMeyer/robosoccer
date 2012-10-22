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
package net.sf.robocode.battle.peer;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleHelper;
import net.sf.robocode.host.IHostManager;
import org.mockito.Mockito;
import robocode.control.RobotSpecification;

/**
 *
 * @author lee
 */
public class RobotPeerHelper {

    private RobotPeerHelper() {
    }

    public static RobotPeer createRealPeer() {
        BattleHelper helper = BattleHelper.createBasicBattleWithHelper();
        IHostManager manager = helper.hostManager;
        RobotSpecification specification = Mockito.mock(RobotSpecification.class);
        RobotPeer p = new RobotPeer(helper.battle, manager, specification, 0, null, 0, null);
        return p;
    }

    public static RobotPeer createMockPassthroughPeer() {
        return createMockPassthroughPeer(BattleHelper.createBasicBattle());
    }

    public static RobotPeer createMockPassthroughPeer(Battle b) {
        RobotPeer mock = Mockito.mock(RobotPeer.class, Mockito.CALLS_REAL_METHODS);
        mock.battle = b;
        return mock;
    }

    public static RobotPeer createMockPeer() {
        return Mockito.mock(RobotPeer.class);
    }
    public static RobotPeer createMockPeer(Battle b) {
        RobotPeer peer =  Mockito.mock(RobotPeer.class);
        peer.battle = b;
        return peer;
    }
}
