/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package robocode.peer.proxies;


import robocode.manager.ThreadManager;
import robocode.peer.RobotStatics;
import robocode.peer.RobotCommands;
import robocode.peer.robot.RobotFileSystemManager;
import robocode.peer.robot.RobotOutputStream;
import robocode.StatusEvent;
import robocode.RobotStatus;


/**
 * @author Pavel Savara (original)
 */
public interface IHostingRobotProxy extends Runnable {
	RobotOutputStream getOut();

	String getRootPackageDirectory();
	String getClassDirectory();
	RobotFileSystemManager getRobotFileSystemManager();
	RobotStatics getStatics();

	void initializeRound(RobotCommands commands, RobotStatus status);
	void startThread(ThreadManager tm);
	void forceStopThread();
	void waitForStopThread();
	boolean unsafeLoadRound(ThreadManager threadManager);

	void setSetCallCount(int setCallCount); // TODO move to exec
	void setGetCallCount(int getCallCount);
	void cleanup();

}
