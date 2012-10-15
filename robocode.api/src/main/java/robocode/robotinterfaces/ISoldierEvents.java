/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     Flemming N. Larsen
 *     - Javadocs
 *******************************************************************************/
package robocode.robotinterfaces;

import robocode.*;

public interface ISoldierEvents extends IBasicRobot {
    void onWin(WinEvent event);
    
    void receiveCommand(CommanderEvent e);
    
    void pause();
    void advance(ScannedRobotEvent e);
    void retreat(ScannedRobotEvent e);
    void attack(ScannedRobotEvent e);
    void increasePower();
    void decreasePower();
    void taunt();
}
