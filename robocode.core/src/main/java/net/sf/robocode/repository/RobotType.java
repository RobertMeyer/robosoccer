/**
 * ****************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 * <p/>
 * Contributors:
 * Pavel Savara
 * - Initial implementation
 * *****************************************************************************
 */
package net.sf.robocode.repository;

import java.io.Serializable;

public class RobotType implements Serializable {

	private static final long serialVersionUID = 1L;

	transient public static final RobotType INVALID = new RobotType(0);
	transient public static final RobotType JUNIOR = new RobotType(1);
	transient public static final RobotType STANDARD = new RobotType(2);
	transient public static final RobotType ADVANCED = new RobotType(4);
	transient public static final RobotType TEAM = new RobotType(8);
	transient public static final RobotType DROID = new RobotType(16);
	transient public static final RobotType INTERACTIVE = new RobotType(32);
	transient public static final RobotType PAINTING = new RobotType(64);
	transient public static final RobotType HOUSE = new RobotType(128);
	transient public static final RobotType BALL = new RobotType(256);
	transient public static final RobotType BOTZILLA = new RobotType(512);
	transient public static final RobotType ZOMBIE = new RobotType(1024);
	transient public static final RobotType DISPENSER = new RobotType(2048);
	transient public static final RobotType FREEZE = new RobotType(4096);
	transient public static final RobotType MINION = new RobotType(8192);
	transient public static final RobotType HEAT = new RobotType(16384);
	transient public static final RobotType SOCCER = new RobotType(32768);	

	
	private int code;

	public RobotType(int code) {
		this.code = code;
	}

	public RobotType(
			boolean isJuniorRobot,
			boolean isStandardRobot,
			boolean isInteractiveRobot,
			boolean isPaintRobot,
			boolean isAdvancedRobot,
			boolean isTeamRobot,
			boolean isDroid,
			boolean isHouse,
			boolean isFreeze,
			boolean isBall,
			boolean isSoccerRobot,
			boolean isBotzilla,
			boolean isZombie,
			boolean isDispenser,
			boolean isMinion,
			boolean isHeat
			) {
		this.code = 0;
		if (isJuniorRobot) {
			code += JUNIOR.getCode();
		}
		if (isStandardRobot) {
			code += STANDARD.getCode();
		}
		if (isInteractiveRobot) {
			code += INTERACTIVE.getCode();
		}
		if (isPaintRobot) {
			code += PAINTING.getCode();
		}
		if (isAdvancedRobot) {
			code += ADVANCED.getCode();
		}
		if (isTeamRobot) {
			code += TEAM.getCode();
		}
		if (isDroid) {
			code += DROID.getCode();
		}
		if (isHouse) {
			code += HOUSE.getCode();
		}
		if (isFreeze) {
			code += FREEZE.getCode();
		}
		if (isBall) {
			code += BALL.getCode();
		}

		if (isSoccerRobot) {
			code += SOCCER.getCode();
		}

		if (isBotzilla) {
	    	code += BOTZILLA.getCode();
	    }
		if (isZombie) {
			code += ZOMBIE.getCode();
		}
		if (isDispenser) {
			code += DISPENSER.getCode();
		}
		if (isMinion) {
			code += MINION.getCode();
		}
		if (isHeat) {
			code += HEAT.getCode();
		}
	}

	public int getCode() {
		return code;
	}

	public boolean isValid() {
		return isJuniorRobot() || isStandardRobot() || isAdvancedRobot();
	}

	public boolean isDroid() {
		return (code & DROID.code) != 0;
	}

	public boolean isTeamRobot() {
		return (code & TEAM.code) != 0;
	}

	public boolean isAdvancedRobot() {
		return (code & ADVANCED.code) != 0;
	}

	public boolean isStandardRobot() {
		return (code & STANDARD.code) != 0;
	}

	public boolean isInteractiveRobot() {
		return (code & INTERACTIVE.code) != 0;
	}

	public boolean isPaintRobot() {
		return (code & PAINTING.code) != 0;
	}

	public boolean isJuniorRobot() {
		return (code & JUNIOR.code) != 0;
	}
	
	public boolean isHouseRobot() {
		return (code & HOUSE.code) != 0;
	}
	
	public boolean isFreezeRobot() {
		return (code & FREEZE.code) != 0;
    }

	public boolean isBall() {
		return (code & BALL.code) != 0;
	}
	
	public boolean isSoccerRobot() {
		return (code & SOCCER.code) != 0;
	}

	public boolean isBotzillaBot() {
		return (code & BOTZILLA.code) != 0;
	}
	
	public boolean isZombie() {
		return (code & ZOMBIE.code) != 0;
	}
	
	public boolean isDispenser() {
		return (code & DISPENSER.code) != 0;
	}
	
	public boolean isMinion() {
		return (code & MINION.code) != 0; 
	}

	public boolean isHeatRobot(){
		return (code & HEAT.code) !=0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + code;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RobotType other = (RobotType) obj;

		if (code != other.code) {
			return false;
		}
		return true;
	}
}
