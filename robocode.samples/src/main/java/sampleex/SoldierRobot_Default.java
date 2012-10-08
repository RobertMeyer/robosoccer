package sampleex;

import robocode.SoldierRobot;
import java.awt.Color;

/**
 * SoldierRobot with no implemented methods.
 * @author The Fightin' Mongooses
 */
public class SoldierRobot_Default extends SoldierRobot {
	@Override
	public void run() {
		setColors(new Color(120, 134, 107), new Color(237, 201, 175), new Color(237, 201, 175));
	}
}
