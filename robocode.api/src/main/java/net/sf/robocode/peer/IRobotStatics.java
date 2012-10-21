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
package net.sf.robocode.peer;

/**
 * @author Pavel Savara (original)
 */
public interface IRobotStatics {

    boolean isInteractiveRobot();

    boolean isPaintRobot();

    boolean isAdvancedRobot();

    boolean isTeamRobot();
    
    /**
     * @author House Robot Team
     * @author Laurence McLean 42373414
     * @author Jack Reichelt 42338271
     * @author Lee Symes 42636267
	 * @return true if this is a house robot, false otherwise.
	 */
    boolean isHouseRobot();
}
