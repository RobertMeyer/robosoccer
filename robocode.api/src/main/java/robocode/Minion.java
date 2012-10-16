package robocode;

import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IMinionRobot;

public abstract class Minion extends AdvancedRobot implements IMinionRobot, IAdvancedEvents {
	public abstract int getMinionType();
}