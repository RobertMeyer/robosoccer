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
import net.sf.robocode.mode.IMode;

public abstract class IModeSpawnController implements ISpawnController {

    /**
     * The mode for which this Controller relates to. No overridable methods
     * ({@link #getSpawnLocation(net.sf.robocode.battle.peer.RobotPeer,
     * net.sf.robocode.battle.Battle) } and {@link #resetSpawnLocation(
     * net.sf.robocode.battle.peer.RobotPeer, net.sf.robocode.battle.Battle) })
     * in this class will be called if the current battle's mode is not this
     * mode.
     */
    private final Class<? extends IMode> mode;

    public IModeSpawnController(Class<? extends IMode> mode) {
        this.mode = mode;
    }

    /**
     * The mode for which this Controller relates to. No overridable methods
     * ({@link #getSpawnLocation(net.sf.robocode.battle.peer.RobotPeer,
     * net.sf.robocode.battle.Battle) } and {@link #resetSpawnLocation(
     * net.sf.robocode.battle.peer.RobotPeer, net.sf.robocode.battle.Battle) })
     * in this class will be called if the current battle's mode is not this
     * mode.
     */
    public final Class<? extends IMode> getMode() {
        return mode;
    }

    @Override
    public abstract double[] getSpawnLocation(RobotPeer r, Battle battle);

    @Override
    public abstract void resetSpawnLocation(RobotPeer r, Battle b);
}
