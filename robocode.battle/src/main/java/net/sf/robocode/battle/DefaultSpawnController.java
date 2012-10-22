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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.mode.BotzillaMode;
import robocode.BattleRules;
import robocode.control.RandomFactory;

/**
 * Controlls the spawn of robots on the field. Contains other controllers for
 * when those controllers control certain robots in certain circumstances.
 * @author Lee Symes 42636267
 * @author Laurence McLean 42373414 (documentation)
 */
public final class DefaultSpawnController implements ISpawnController {

    private final List<ISpawnController> controllers = new ArrayList<ISpawnController>();

    /**
     * Creates a DefaultSpawnController, adding the BotzillaSpawnContrller by default.
     */
    public DefaultSpawnController() {
        controllers.add(new BotzillaSpawnController(BotzillaMode.class));
    }

    /**
     * Add a controller into the list of controllers
     * @param e the controller to add
     * @return true if added, false otherwise.
     */
    public boolean addController(ISpawnController e) {
        return controllers.add(e);
    }

    /**
     * Remove a controller from the list of controllers
     * @param e the controller to remove
     * @return true if remove, false otherwise.
     */
    public boolean removeController(ISpawnController o) {
        return controllers.remove(o);
    }

    /**
     * Removes all controllers from the list of controllers and will result
     * in the default random spawn.
     */
    public void clearControllers() {
        controllers.clear();
    }

    @Override
    /**
     * Gets the spawn location of a robot. Will check for other spawn controllers
     * being able to do this robot first, otherwise we will set the spawn location
     * of the robot at random.
     * @param r The RobotPeer we are determining the spawn location of
     * @param b the Battle we are currently in
     * @return An array of length 3 with x, y, heading
     */
    public double[] getSpawnLocation(RobotPeer r, Battle b) {
        Random random = RandomFactory.getRandom();
        BattleRules battleRules = b.getBattleRules();
        for (int i = 0; i < controllers.size(); i++) {
            ISpawnController iSpawnController = controllers.get(i);
            if (iSpawnController == null) {
                controllers.remove(i);
                i--;
                continue;
            }
            if (iSpawnController instanceof IModeSpawnController) {
                IModeSpawnController controller = (IModeSpawnController) iSpawnController;
                if (!b.getBattleMode().getClass().equals(controller.getMode())) {
                    continue;
                }
            }
            double[] pos = iSpawnController.getSpawnLocation(r, b);
            if (pos != null && pos.length == 3) {
                return pos; // A different spawn controller is handling this robot.
            }
        }
        // Havn't found a location, so default to random.
        double x = RobotPeer.WIDTH + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
        double y = RobotPeer.HEIGHT + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
        double heading = 2 * Math.PI * random.nextDouble();
        return new double[]{x, y, heading};
    }

    /**
     * Resets the spawn location of a robot. Will delegate to spawn controllers
     * in the list to let them determine what to do.
     * @param r The RobotPeer we are resetting the spawn location of.
     * @param b The Battle we are currently in.
     */
    @Override
    public void resetSpawnLocation(RobotPeer r, Battle b) {
        for (Iterator<ISpawnController> it = controllers.iterator(); it.hasNext();) {
            it.next().resetSpawnLocation(r, b);
        }
    }
}
