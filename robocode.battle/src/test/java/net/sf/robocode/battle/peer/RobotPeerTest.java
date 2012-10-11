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

import java.util.Arrays;
import java.util.List;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleHelper;
import net.sf.robocode.battle.ISpawnController;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import robocode.BattleRulesForTest;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;
import robocode.control.snapshot.RobotState;
import robocode.exception.DisabledException;
import robocode.util.Utils;

/**
 *
 * @author lee
 */
public class RobotPeerTest {

    RobotPeer peer;
    List<RobotPeer> peerList;

    public RobotPeerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        BattleRulesForTest rules = new BattleRulesForTest();
        rules.setBattlefieldWidth(1000);
        rules.setBattlefieldHeight(1000);
        Battle battle = BattleHelper.setBattleRules(BattleHelper.createBasicBattle(), rules);
        battle.getBattleRules().getBattlefieldWidth();
        peer = RobotPeerHelper.createMockPassthroughPeer(battle);
        peer.battleRules = battle.getBattleRules();
        peerList = Arrays.asList(new RobotPeer[]{peer});
        doReturn(false).when(peer).isBotzilla();
        doNothing().when(peer).updateBoundingBox();
        // The next line is a `I'm Tired so let's cheat`.
        // After the position of the robot happens, die with an exception that
        // the function would not throw.
        doThrow(new DisabledException()).when(peer).setState(RobotState.ACTIVE);
    }

    @After
    public void tearDown() {
        peer = null;
        peerList = null;
    }

    @Test
    public void testInitialPosition() {
        ISpawnController c = peer.battle.getSpawnController();
        assertNotNull(c);
        RandomFactory.resetDeterministic(0);
        try {
            peer.initializeRound(peerList, null);
        } catch (DisabledException e) {
            //The expected values are values generated from the `old` method. My method will mirror these.
            assertEquals("x Coordinate Doesn't match", 712.4903643865244, peer.x, Utils.NEAR_DELTA);
            assertEquals("y Coordinate Doesn't match", 261.293502417767, peer.y, Utils.NEAR_DELTA);
            assertEquals("heading Doesn't match", 4.005011801500041, peer.bodyHeading, Utils.NEAR_DELTA);
        }
    }
}