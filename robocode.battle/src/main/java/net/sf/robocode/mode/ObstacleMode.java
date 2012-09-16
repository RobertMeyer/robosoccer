package net.sf.robocode.mode;

/**
 * Creates a game mode with obstacles placed on the battlefield
 * 
 * @author Team a-MAZE-ing
 *
 */
public class ObstacleMode extends ClassicMode {

    @Override
    public String toString() {
        return "Obstacle Mode";
    }

    @Override
    public String getDescription() {
        return "A mode with obstacles that robots have to avoid.";
    }
}
