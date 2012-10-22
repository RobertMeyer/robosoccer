package tested.robots;

import java.awt.Color;

import robocode.SoccerRobot;

public class SoccerType extends SoccerRobot {
	public void run() {
		this.setAllColors(Color.BLUE);
		this.setAhead(100000);
	}	
}
