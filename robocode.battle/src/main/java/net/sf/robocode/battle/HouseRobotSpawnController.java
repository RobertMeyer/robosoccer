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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.robocode.battle.peer.RobotPeer;
import robocode.control.RandomFactory;

/**
 *
 * @author lee
 * @author Laurence McLean 42373414 (fixed parts)
 */
public class HouseRobotSpawnController implements ISpawnController {

    private static final Random random = RandomFactory.getRandom();
    private static final HashMap<WeakReference<Battle>, HouseRobotBattlePositions> battlePositions =
            new HashMap<WeakReference<Battle>, HouseRobotBattlePositions>();

    public static void cleanMap() {
        for (Iterator<WeakReference<Battle>> it = battlePositions.keySet().iterator(); it.hasNext();) {
            WeakReference<Battle> pr = it.next();
            if (pr.get() == null) {
                it.remove();
            }
        }
    }

    @Override
    public synchronized double[] getSpawnLocation(RobotPeer r, Battle battle) {
        if (r.isHouseRobot()) {
            cleanMap();
            for (Map.Entry<WeakReference<Battle>, HouseRobotBattlePositions> entry : battlePositions.entrySet()) {
                WeakReference<Battle> pr = entry.getKey();
                if (pr.get() != null && pr.get().equals(battle)) {
                    HouseRobotSpawnController.HouseRobotBattlePositions pos = entry.getValue();
                    return pos.getPosition(r, battle);
                }
            }
            HouseRobotBattlePositions hrbp = new HouseRobotBattlePositions();
            double[] position = hrbp.getPosition(r, battle);
            battlePositions.put(new WeakReference<Battle>(battle), hrbp);
            return position;
        } else {
            return null;
        }
    }

    @Override
    public synchronized void resetSpawnLocation(RobotPeer r, Battle b) {
        cleanMap();
        for (Map.Entry<WeakReference<Battle>, HouseRobotBattlePositions> entry : battlePositions.entrySet()) {
            WeakReference<Battle> pr = entry.getKey();
            if (pr.get() != null && pr.get().equals(b)) {
                entry.getValue().remove(r);
                return;
            }
        }
    }

    private static class HouseRobotBattlePositions {

        /**
         * Corner order:
         * Top Left, Top Right, Bottom Left, Bottom Right.
         */
        private List<RobotPeer> corners = new ArrayList<RobotPeer>(Arrays.asList(new RobotPeer[4]));
        private List<Integer> unallocatedCorners = new ArrayList<Integer>(corners.size());

        private HouseRobotBattlePositions() {
            for (int i = 0; i < corners.size(); i++) {
                unallocatedCorners.add(i);
            }
            Collections.shuffle(unallocatedCorners, random);
        }

        /**
         * Provides an X with max(5% of width, 50 units) buffer.
         * @param corner
         * @param width
         * @return
         */
        private double getX(int corner, double width) {
            double cornerBuffer = Math.max(width * 0.05, 50);
            if (corner % 2 == 0) {
                // Left
                return 0 + cornerBuffer;
            } else {
                // Right
                return width - cornerBuffer;
            }
        }

        /**
         * Provides an Y with max(5% of width, 50 units) buffer.
         * @param corner
         * @param height
         * @return
         */
        private double getY(int corner, double height) {
            double cornerBuffer = Math.max(height * 0.05, 50);
            if (corner / 2 != 0) {
                // Bottom
                return 0 + cornerBuffer;
            } else {
                // Top
                return height - cornerBuffer;
            }
        }

        private double[] getPosition_(int corner, double width, double height) {
            double x, y, c_x, c_y;
            x = getX(corner, width);
            y = getY(corner, height);
            c_x = (corner % 2 == 0 ? 0 : width);
            c_y = (corner / 2 != 0 ? 0 : height);

            double heading = (Math.PI / 2) - Math.atan2(y - c_y, x - c_x);
            return new double[]{x, y, heading};
        }

        private double[] getPosition(RobotPeer r, Battle battle) {
            int corner;
            if (corners.contains(r)) {
                corner = corners.indexOf(r);
            } else {
                if (unallocatedCorners.isEmpty()) {
                    return null;
                }
                corner = unallocatedCorners.remove(0);
                if (corners.get(corner) != null) {
                    // We've picked a corner that has a robot in already.
                    // So We'll just update the list from the array
                    refreshUnallocatedCorners();
                    if (unallocatedCorners.isEmpty()) {
                        return null;
                    } else {
                        corner = unallocatedCorners.remove(0);
                    }

                }
                corners.set(corner, r);
            } // TODO cache the results.
            return getPosition_(corner, battle.getBattleRules().getBattlefieldWidth(), battle.getBattleRules().getBattlefieldHeight());
        }

        private void refreshUnallocatedCorners() {
            unallocatedCorners.clear();
            for (int i = 0; i < corners.size(); i++) {
                if (corners.get(i) == null) {
                    unallocatedCorners.add(i);
                }
            }
            Collections.shuffle(unallocatedCorners, random);
        }

        private void remove(RobotPeer r) {
            corners.remove(r);
            refreshUnallocatedCorners();
        }
    }
}
