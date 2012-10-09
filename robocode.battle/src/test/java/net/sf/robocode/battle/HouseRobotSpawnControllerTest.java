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

import net.sf.robocode.battle.peer.RobotPeer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static net.sf.robocode.battle.BattleCreatorForTest.*;
import static java.lang.Math.*;
import robocode.BattleRulesForTest;

/**
 *
 * @author lee
 */
public class HouseRobotSpawnControllerTest {

    private RobotPeer r;
    private Battle battle;
    private BattleRulesForTest rules;

    public HouseRobotSpawnControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        r = mock(RobotPeer.class);
        battle = createBasicBattle();
        rules = new BattleRulesForTest();
    }

    @After
    public void tearDown() {
        r = null;
        battle = null;
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testGetSpawnLocationForNonHouseRobot() {
        // Setup phase
        when(r.isHouseRobot()).thenReturn(Boolean.FALSE);


        // Start Testing
        System.out.println("getSpawnLocation");
        HouseRobotSpawnController instance = new HouseRobotSpawnController();
        double[] result = instance.getSpawnLocation(r, battle);
        assertNull("The HouseRobotSpawnController thinks it can place a non house robot", result);
        verify(r).isHouseRobot(); // Check that It checked for house robot.
        // Check that no other methods were called.
        verifyNoMoreInteractions(r);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotReturnsValidCorner() {
        // Setup
        when(r.isHouseRobot()).thenReturn(Boolean.TRUE);
        double w = 1000, h = 1000;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = new HouseRobotSpawnController();
        // Testing
        System.out.println("getSpawnLocation");
        double[] result = instance.getSpawnLocation(r, battle);
        assertCornerAndReturn(result, w, h);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotReturnsValidCorner2() {
        // Setup
        when(r.isHouseRobot()).thenReturn(Boolean.TRUE);
        double w = 100, h = 100;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = new HouseRobotSpawnController();
        // Testing
        System.out.println("getSpawnLocation");
        double[] result = instance.getSpawnLocation(r, battle);
        assertCornerAndReturn(result, w, h);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotReturnsSameCorner() {
        // Setup
        when(r.isHouseRobot()).thenReturn(Boolean.TRUE);
        double w = 100, h = 100;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = new HouseRobotSpawnController();
        // Testing
        System.out.println("getSpawnLocation");
        double[] result = instance.getSpawnLocation(r, battle);
        int corner1 = assertCornerAndReturn(result, w, h);
        result = instance.getSpawnLocation(r, battle);
        int corner2 = assertCornerAndReturn(result, w, h);
        assertEquals("The same robot get's different corners!", corner1, corner2);
    }

    private int assertCornerAndReturn(double[] result, double w, double h) {
        assertNotNull("The HouseRobotSpawnController thinks it can't place a house robot", result);
        int corner;
        double corner_w = max(50.0, w * 0.05);
        double corner_h = max(50.0, h * 0.05);
        assertEquals("Did not calculate corner x correctly", corner_w, min(result[0], abs(result[0] - w)), 1);
        assertEquals("Did not calculate corner y correctly", corner_h, min(result[1], abs(result[1] - h)), 1);
        double deg = toDegrees(result[2]);
        if (deg > 180) { // Normalise the angle so that the range: [180,-180]
            deg -= 360;
        }
        if (result[0] < w / 2) { //LEFT
            if (result[1] < h / 2) {// BOTTOM
                corner = 2;
                assertEquals("Did not calculate corner x correctly", corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly", corner_h, result[1], 1);
                assertEquals("Bottom Left Corner should point North East", 45.0, deg, 1.0);
            } else { //TOP
                corner = 0;
                assertEquals("Did not calculate corner x correctly", corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly", h - corner_h, result[1], 1);
                assertEquals("Top Left Corner should point South East", -45.0, deg, 1.0);
            }
        } else { // Right
            if (result[1] < h / 2) {// BOTTOM
                corner = 3;
                assertEquals("Did not calculate corner x correctly", w - corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly", corner_h, result[1], 1);
                assertEquals("Bottom Right Corner should point North West", 135.0, deg, 1.0);
            } else { // TOP
                corner = 1;
                assertEquals("Did not calculate corner x correctly", w - corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly", h - corner_h, result[1], 1);
                assertEquals("Top Right Corner should point South West", -135.0, deg, 1.0);
            }
        }
        return corner;
    }
}
