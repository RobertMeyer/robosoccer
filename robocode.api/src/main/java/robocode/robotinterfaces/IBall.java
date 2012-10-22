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
 *     Flemming N. Larsen
 *     - Javadocs
 *******************************************************************************/
package robocode.robotinterfaces;


/**
 * A robot interface for creating a ball type of robot.
 *
 * @author Carl Hattenfels - team-G1
 * @see robocode.AdvancedRobot
 * @see IBasicRobot
 * @see IJuniorRobot
 * @see IInteractiveRobot
 * @see ITeamRobot
 * @since 1.6
 */
public interface IBall extends IBasicRobot {

	/**
	 * This method is called by the game to notify this robot about advanced
	 * robot event. Hence, this method must be implemented so it returns your
	 * {@link IAdvancedEvents} listener.
	 *
	 * @return listener to advanced events or {@code null} if this robot should
	 *         not receive the notifications.
	 * @since 1.6
	 */
	IAdvancedEvents getAdvancedEventListener();
}
