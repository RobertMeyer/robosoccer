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
import static net.sf.robocode.battle.BattleHelper.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        System.gc();
        HouseRobotSpawnController.cleanMap();
    }
    /**
     *
        controllers.add(new BotzillaSpawnController(Botzilla.class));
        controllers.add(new HouseRobotSpawnController(HouseR));
     */

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testGetSpawnLocationForNonHouseRobot() {
        // Setup phase
        when(r.isHouseRobot()).thenReturn(Boolean.FALSE);

        // Start Testing
        System.out.println("getSpawnLocation");
        HouseRobotSpawnController instance = createSpawner();
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
        double w = 2000, h = 2000;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = createSpawner();
        // Testing
        System.out.println("getSpawnLocation");
        double[] result = instance.getSpawnLocation(r, battle);
        assertCornerAndReturn(result, w, h);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotReturnsValidCornerSmallBoard() {
        // Setup
        when(r.isHouseRobot()).thenReturn(Boolean.TRUE);
        double w = 200, h = 200;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = createSpawner();
        // Testing
        System.out.println("getSpawnLocation");
        double[] result = instance.getSpawnLocation(r, battle);
        assertCornerAndReturn(result, w, h);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotsReturnsDifferentCorners() {
        // Setup
        RobotPeer[] peers = {r, mock(RobotPeer.class),
                             mock(RobotPeer.class), mock(RobotPeer.class)};
        List<Boolean> corners = Arrays.asList(new Boolean[4]);
        Collections.fill(corners, Boolean.FALSE);
        double w = 200, h = 200;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = createSpawner();
        // Testing
        System.out.println("getSpawnLocation");
        for (int i = 0; i < peers.length; i++) {
            RobotPeer robotPeer = peers[i];
            when(robotPeer.isHouseRobot()).thenReturn(Boolean.TRUE);
            double[] result = instance.getSpawnLocation(robotPeer, battle);
            int corner = assertCornerAndReturn(result, w, h, "Loop Index " + i);
            corners.set(corner, Boolean.TRUE);
        }
        int lastBad = corners.indexOf(Boolean.FALSE);
        assertEquals("There is an unallocated corner", -1, lastBad);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotsFailsToAdd5Robots() {
        // Setup
        RobotPeer[] peers = {r, mock(RobotPeer.class),
                             mock(RobotPeer.class), mock(RobotPeer.class)};
        List<Boolean> corners = new ArrayList<Boolean>(Arrays.asList(new Boolean[4]));
        Collections.fill(corners, Boolean.FALSE);
        double w = 200, h = 200;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = createSpawner();
        // Testing
        System.out.println("getSpawnLocation");
        for (int i = 0; i < peers.length; i++) {
            RobotPeer robotPeer = peers[i];
            when(robotPeer.isHouseRobot()).thenReturn(Boolean.TRUE);
            double[] result = instance.getSpawnLocation(robotPeer, battle);
            int corner = assertCornerAndReturn(result, w, h, "Loop Index " + i);
            corners.set(corner, Boolean.TRUE);
        }
        int lastBad = corners.indexOf(Boolean.FALSE);
        assertEquals("There is an unallocated corner(" + corners.toString() + ")", -1, lastBad);
        RobotPeer rp = mock(RobotPeer.class);
        when(rp.isHouseRobot()).thenReturn(Boolean.TRUE);
        double[] spawnRobot5 = instance.getSpawnLocation(rp, battle);
        assertNull("The Spawner thinks we can add 5 house robots", spawnRobot5);
    }

    /**
     * Test of getSpawnLocation method, of class HouseRobotSpawnController.
     */
    @Test
    public void testSpawnHouseRobotReturnsSameCorner() {
        // Setup
        when(r.isHouseRobot()).thenReturn(Boolean.TRUE);
        double w = 200, h = 200;
        rules.setBattlefieldHeight((int) h);
        rules.setBattlefieldWidth((int) w);
        setBattleRules(battle, rules);
        HouseRobotSpawnController instance = createSpawner();
        // Testing
        System.out.println("getSpawnLocation");
        double[] result = instance.getSpawnLocation(r, battle);
        int corner1 = assertCornerAndReturn(result, w, h, "SameCorner - 1");
        result = instance.getSpawnLocation(r, battle);
        int corner2 = assertCornerAndReturn(result, w, h, "SameCorner - 2");
        assertEquals("The same robot get's different corners!", corner1, corner2);
    }

    private int assertCornerAndReturn(double[] result, double w, double h) {
        return assertCornerAndReturn(result, w, h, "");
    }

    private int assertCornerAndReturn(double[] result, double w, double h, String robotDesc) {
        assertNotNull("The HouseRobotSpawnController thinks it can't place a house robot(" + robotDesc + ")", result);
        int corner;
        double corner_w = max(50.0, w * 0.05);
        assertTrue("Invalid Test Input - Width", (corner_w * 2) < w);
        double corner_h = max(50.0, h * 0.05);
        assertTrue("Invalid Test Input - Height", (corner_h * 2) < h);
        double deg = toDegrees(result[2]);
        if (deg > 180) { // Normalise the angle so that the range: [180,-180]
            deg -= 360;
        }
        if (result[0] < w / 2) { //LEFT
            if (result[1] < h / 2) {// BOTTOM
                corner = 2;
                assertEquals("Did not calculate corner x correctly(" + robotDesc + ")", corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly(" + robotDesc + ")", corner_h, result[1], 1);
                assertEquals("Bottom Left Corner should point North East(" + robotDesc + ")", 45.0, deg, 1.0);
            } else { //TOP
                corner = 0;
                assertEquals("Did not calculate corner x correctly(" + robotDesc + ")", corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly(" + robotDesc + ")", h - corner_h, result[1], 1);
                assertEquals("Top Left Corner should point South East(" + robotDesc + ")", 135.0, deg, 1.0);
            }
        } else { // Right
            if (result[1] < h / 2) {// BOTTOM
                corner = 3;
                assertEquals("Did not calculate corner x correctly(" + robotDesc + ")", w - corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly(" + robotDesc + ")", corner_h, result[1], 1);
                assertEquals("Bottom Right Corner should point North West(" + robotDesc + ")", -45.0, deg, 1.0);
            } else { // TOP
                corner = 1;
                assertEquals("Did not calculate corner x correctly(" + robotDesc + ")", w - corner_w, result[0], 1);
                assertEquals("Did not calculate corner y correctly(" + robotDesc + ")", h - corner_h, result[1], 1);
                assertEquals("Top Right Corner should point South West(" + robotDesc + ")", -135.0, deg, 1.0);
            }
        }
        return corner;
    }

    private HouseRobotSpawnController createSpawner() {
        HouseRobotSpawnController instance = new HouseRobotSpawnController();
        return instance;
    }
}
