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
 *     Flemming N. Larsen
 *     - Javadocs
 *******************************************************************************/
package robocode;


import robocode.peer.RobotStatics;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.IInteractiveEvents;
import robocode.robotinterfaces.IInteractiveRobot;

import java.awt.*;


/**
 * A KeyPressedEvent is sent to {@link Robot#onKeyPressed(java.awt.event.KeyEvent)
 * onKeyPressed()} when a key has been pressed on the keyboard.
 *
 * @author Pavel Savara (original)
 * @see KeyReleasedEvent
 * @see KeyTypedEvent
 * @since 1.6.1
 */
public final class KeyPressedEvent extends KeyEvent {

	/**
	 * Called by the game to create a new KeyPressedEvent.
	 *
	 * @param source the source key event originating from the AWT.
	 */
	public KeyPressedEvent(java.awt.event.KeyEvent source) {
		super(source);
	}

	private static int classPriority = 98;

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
		if (statics.isInteractiveRobot()) {
			IInteractiveEvents listener = ((IInteractiveRobot) robot).getInteractiveEventListener();

			if (listener != null) {
				listener.onKeyPressed(getSourceEvent());
			}
		}
	}

}
