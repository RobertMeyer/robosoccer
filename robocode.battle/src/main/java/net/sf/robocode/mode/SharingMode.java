package net.sf.robocode.mode;

/**
 * Mode Class used for Team Energy Sharing Mode. 
 * 
 * @author Team - MCJJ
 * @author Jake Ching Leong Ong
 */

public class SharingMode extends ClassicMode {
	
	private final String title = "Energy Sharing Mode";
    private final String description = "This mode allows sharing of energy among teammates (Activate only in Team)";

    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
}
