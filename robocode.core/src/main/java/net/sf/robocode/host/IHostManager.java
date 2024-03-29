/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.host;

import java.io.PrintStream;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.repository.RobotType;
import robocode.control.RobotSpecification;

/**
 * @author Pavel Savara (original)
 */
public interface IHostManager {

    void initSecurity();

    long getRobotFilesystemQuota();

    void resetThreadManager();

    void addSafeThread(Thread safeThread);

    void removeSafeThread(Thread safeThread);

    PrintStream getRobotOutputStream();

    Object createRobotProxy(RobotSpecification robotSpecification, RobotStatics statics, IRobotPeer peer);

    void cleanup();

    String[] getReferencedClasses(IRobotRepositoryItem robotRepositoryItem);

    RobotType getRobotType(IRobotRepositoryItem robotRepositoryItem, boolean resolve, boolean message);

	int getMinionType(IRobotRepositoryItem robotRepositoryItem, boolean message);
}
