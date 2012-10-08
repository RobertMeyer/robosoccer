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

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.robocode.battle.peer.RobotPeer;
import robocode.control.RandomFactory;

/**
 *
 * @author lee
 */
public class HouseRobotSpawnController implements ISpawnController {

    private static final Random random = RandomFactory.getRandom();
    private static final HashMap<WeakReference<Battle>, HouseRobotBattlePositions> battlePositions =
            new HashMap<WeakReference<Battle>, HouseRobotBattlePositions>();

    public HouseRobotSpawnController() {
    }

    @Override
    public double[] getSpawnLocation(RobotPeer r, Battle battle) {
        if (r.isHouseRobot()) {
            for (Map.Entry<WeakReference<Battle>, HouseRobotBattlePositions> entry : battlePositions.entrySet()) {
                WeakReference<Battle> pr = entry.getKey();
                if (pr.get() == null) {
                    battlePositions.remove(pr);
                    continue;
                } else if (pr.get().equals(battle)) {
                    HouseRobotSpawnController.HouseRobotBattlePositions pos = entry.getValue();
                    return pos.getPosition(r, battle);
                }
            }
        } else {
            return null;
        }
    }

    private static class HouseRobotBattlePositions {

        /**
         * Corner order:
         * Top Left, Top Right, Bottom Left, Bottom Right.
         */
        private RobotPeer[] corners = new RobotPeer[4];
        private List<Integer> unallocatedCorners = new ArrayList<Integer>(corners.length);

        private HouseRobotBattlePositions() {
            for (int i = 0; i < corners.length; i++) {
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
                return width - cornerBuffer;
            }
        }
    }
}
