/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Ported to Java 5
 *******************************************************************************/
package net.sf.robocode.battle.peer;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public interface ContestantPeer extends Comparable<ContestantPeer> {

    String getName();

    int getRobotIndex();

    int getTeamIndex();

    int getContestantIndex();

    ContestantStatistics getStatistics();

    @Override
    String toString();
}
