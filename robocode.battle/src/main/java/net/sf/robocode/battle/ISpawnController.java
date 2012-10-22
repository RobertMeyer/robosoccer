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

/**
 *
 * @author Lee Symes 42636267
 */
public interface ISpawnController {

    /**
     *
     * Gets the spawn location for each robot to be spawned.
     *
     * @param r The robot that this method will return a location to spawn.
     * @return An array of 3 doubles, an `x`, `y` and `heading` in that order.
     *          Or null if this controller does not control or spawn this
     *          type of robot.
     */
    public double[] getSpawnLocation(RobotPeer r, Battle battle);

    /**
     * This method is called when the spawn location given in
     * {@link #getSpawnLocation(net.sf.robocode.battle.peer.RobotPeer,
     * net.sf.robocode.battle.Battle) } is invalid. This method should perform
     * what it can to ensure that the next call to
     * {@link #getSpawnLocation(net.sf.robocode.battle.peer.RobotPeer,
     * net.sf.robocode.battle.Battle) } will succeed.
     * @param r The RobotPeer whose positioning failed.
     * @param b The Battle that the peer belongs too.
     */
    public void resetSpawnLocation(RobotPeer r, Battle b);
}
