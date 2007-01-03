/*******************************************************************************
 * Copyright (c) 2001-2007 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.robocode.net/license/CPLv1.0.html
 * 
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *******************************************************************************/
package robocode.battle.record;


import java.util.*;

import robocode.battlefield.BattleField;
import robocode.peer.RobotPeer;


/**
 * Battle record used for replaying battles containing size of battle field,
 * names of the participating robots, and all the record of all round including
 * the result of each round.
 *
 * @author Flemming N. Larsen
 */
public class BattleRecord {

	// Battle field size
	public int battleFieldWidth;
	public int battleFieldHeight;

	// Robots (index of robot is index used for robot identification)
	public String[] robotNames;

	// List of rounds
	public List<RoundRecord> rounds = new ArrayList<RoundRecord>();

	/**
	 * Constructs a new battle record.
	 *
	 * @param battleField size of battle field
	 * @param robots robots participating in the battle
	 */
	public BattleRecord(BattleField battleField, Vector<RobotPeer> robots) {
		battleFieldWidth = battleField.getWidth();
		battleFieldHeight = battleField.getHeight();
		
		robotNames = new String[robots.size()];
		int i = 0;

		for (RobotPeer r : robots) {
			robotNames[i++] = r.getName();
		}
	}
}
