/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Updated Javadocs
 *******************************************************************************/
package robocode;


import robocode.peer.RobotStatics;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

import java.awt.*;


/**
 * This event is sent to {@link Robot#onWin(WinEvent) onWin()} when your robot
 * wins the round in a battle.
 *
 * @author Mathew A. Nelson (original)
 */
public final class WinEvent extends Event {

	/**
	 * Called by the game to create a new WinEvent.
	 */
	public WinEvent() {
		super();
	}

	private static int classPriority = 100; // System event -> cannot be changed!;

	@Override
	protected final int getClassPriorityImpl() {
		return classPriority;
	}

	@Override
	protected void setClassPriorityImpl(int priority) {
		// System event -> cannot be changed!;
		System.out.println("SYSTEM: You may not change the priority of WinEvent.  setPriority ignored.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispatch(IBasicRobot robot, RobotStatics statics, Graphics2D graphics) {
		IBasicEvents listener = robot.getBasicEventListener();

		if (listener != null) {
			listener.onWin(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isCriricalEvent() {
		return true;
	}

}
