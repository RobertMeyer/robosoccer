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


import robocode.robotinterfaces.*;
import robocode.peer.RobotStatics;

import java.io.Serializable;
import java.awt.*;


/**
 * A MessageEvent is sent to {@link TeamRobot#onMessageReceived(MessageEvent)
 * onMessageReceived()} when a teammate sends a message to your robot.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Mathew A. Nelson (original)
 */
public final class MessageEvent extends Event {
	private final String sender;
	private final Serializable message;

	/**
	 * Called by the game to create a new MessageEvent.
	 *
	 * @param sender  the name of the sending robot
	 * @param message the message for your robot
	 */
	public MessageEvent(String sender, Serializable message) {
		this.sender = sender;
		this.message = message;
	}

	/**
	 * Returns the name of the sending robot.
	 *
	 * @return the name of the sending robot
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Returns the message itself.
	 *
	 * @return the message
	 */
	public Serializable getMessage() {
		return message;
	}

	private static int classPriority = 75;

	@Override
	protected final int getClassPriorityImpl() {
		return classPriority;
	}

	@Override
	protected void setClassPriorityImpl(int priority) {
		classPriority = priority;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispatch(IBasicRobot robot, RobotStatics statics, Graphics2D graphics) {
		if (statics.isTeamRobot()) {
			ITeamEvents listener = ((ITeamRobot) robot).getTeamEventListener();

			if (listener != null) {
				listener.onMessageReceived(this);
			}
		}
	}
}
