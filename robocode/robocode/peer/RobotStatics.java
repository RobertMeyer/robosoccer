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
package robocode.peer;


import robocode.BattleRules;
import robocode.manager.NameManager;
import robocode.repository.RobotFileSpecification;

import java.util.List;


/**
 * @author Pavel Savara (original)
 */
public final class RobotStatics {
	private final boolean isJuniorRobot;
	private final boolean isInteractiveRobot;
	private final boolean isPaintRobot;
	private final boolean isAdvancedRobot;
	private final boolean isTeamRobot;
	private final boolean isTeamLeader;
	private final boolean isDroid;
	private final String name;
	private final String shortName;
	private final String veryShortName;
	private final String nonVersionedName;
	private final String shortClassName;
	private final BattleRules battleRules;
	private final String[] teammates;
	private final String teamName;
	private final int index;
	private final int contestantIndex;

	public RobotStatics(RobotFileSpecification spec, int duplicate, boolean isLeader, BattleRules rules, TeamPeer team, int index, int contestantIndex) {
		NameManager cnm = spec.getNameManager();

		if (duplicate >= 0) {
			String countString = " (" + (duplicate + 1) + ')';

			name = cnm.getFullClassNameWithVersion() + countString;
			shortName = cnm.getUniqueShortClassNameWithVersion() + countString;
			veryShortName = cnm.getUniqueVeryShortClassNameWithVersion() + countString;
			nonVersionedName = cnm.getFullClassName() + countString;
		} else {
			name = cnm.getFullClassNameWithVersion();
			shortName = cnm.getUniqueShortClassNameWithVersion();
			veryShortName = cnm.getUniqueVeryShortClassNameWithVersion();
			nonVersionedName = cnm.getFullClassName();
		}
		shortClassName = spec.getNameManager().getShortClassName();
		isJuniorRobot = spec.isJuniorRobot();
		isInteractiveRobot = spec.isInteractiveRobot();
		isPaintRobot = spec.isPaintRobot();
		isAdvancedRobot = spec.isAdvancedRobot();
		isTeamRobot = spec.isTeamRobot();
		isDroid = spec.isDroid();
		isTeamLeader = isLeader;
		battleRules = rules;
		this.index = index;
		this.contestantIndex = contestantIndex;

		if (team != null) {
			List<String> memberNames = team.getMemberNames();

			teammates = new String[memberNames.size() - 1];
			int i = 0;

			for (String mate : memberNames) {
				if (!name.equals(mate)) {
					teammates[i++] = mate;
				}
			}
			teamName = team.getName();
		} else {
			teammates = new String[0];
			teamName = name;
		}
	}

	public boolean isJuniorRobot() {
		return isJuniorRobot;
	}

	public boolean isInteractiveRobot() {
		return isInteractiveRobot;
	}

	public boolean isPaintRobot() {
		return isPaintRobot;
	}

	public boolean isAdvancedRobot() {
		return isAdvancedRobot;
	}

	public boolean isTeamRobot() {
		return isTeamRobot;
	}

	public boolean isTeamLeader() {
		return isTeamLeader;
	}

	public boolean isDroid() {
		return isDroid;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getVeryShortName() {
		return veryShortName;
	}

	public String getNonVersionedName() {
		return nonVersionedName;
	}

	public String getShortClassName() {
		return shortClassName;
	}

	public BattleRules getBattleRules() {
		return battleRules;
	}

	public String[] getTeammates() {
		return teammates.clone();
	}

	public String getTeamName() {
		return teamName;
	}

	public int getIndex() {
		return index;
	}

	public int getContestIndex() {
		return contestantIndex;
	}
}
