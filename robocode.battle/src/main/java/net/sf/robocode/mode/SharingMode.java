package net.sf.robocode.mode;

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
