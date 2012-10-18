package robocode;

import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IDispenser;

/**
 * A special kind of defensive robot. It's purpose is to heal other robots
 * with an area of effect and healing projectiles.
 * @author The Fightin' Mongooses
 */
public class Dispenser extends AdvancedRobot implements IDispenser, IAdvancedEvents{
	
	public Dispenser() {
		
	}
}
