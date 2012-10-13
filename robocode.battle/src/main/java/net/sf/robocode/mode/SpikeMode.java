package net.sf.robocode.mode;

public class SpikeMode extends ClassicMode {
	
	private final String title = "Spike Mode";
    private final String description = "This mode activate spike onto the Battlefield and robot get kill instantly when it touches the spike.";

    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
}