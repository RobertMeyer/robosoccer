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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.sf.robocode.battle.peer.RobotPeer;
import robocode.BattleRules;
import robocode.control.RandomFactory;

/**
 *
 * @author lee
 */
public class DefaultSpawnController implements ISpawnController {

    private final List<ISpawnController> controllers = new ArrayList<ISpawnController>();
    private final Random random = RandomFactory.getRandom();

    public DefaultSpawnController() {
    }

    public boolean addController(ISpawnController e) {
        return controllers.add(e);
    }

    public boolean removeController(ISpawnController o) {
        return controllers.remove(o);
    }

    public void clearControllers() {
        controllers.clear();
    }

    @Override
    public double[] getSpawnLocation(RobotPeer r, Battle b) {
        BattleRules br = b.getBattleRules();
        for (int i = 0; i < controllers.size(); i++) {
            ISpawnController iSpawnController = controllers.get(i);
            double[] pos = iSpawnController.getSpawnLocation(r, b);
            if (pos != null && pos.length == 3) {
                return pos;
            }
        }
        // Havn't found a location, so default to random.
        double x = RobotPeer.WIDTH + random.nextDouble() * (br.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
        double y = RobotPeer.HEIGHT + random.nextDouble() * (br.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
        double heading = 2 * Math.PI * random.nextDouble();
        return new double[]{x, y, heading};
    }
}
