package net.sf.robocode.mode;

import robocode.Rules;


public class LowVision extends ClassicMode{
	
	private final String title = "Low Vision Mode";
    private final String description = "This mode will reduce the vision of the Robots by 1/4";

    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
     
    public double modifyVision(double VisionDecrease)
    {
    	return Rules.RADAR_SCAN_RADIUS/4;
    }

}
