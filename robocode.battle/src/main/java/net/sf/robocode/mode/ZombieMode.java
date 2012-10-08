package net.sf.robocode.mode;
/**
 * 
 * This class models "Zombie Mode", a Robocode mode in which a single robot is pit against a swarm of
 * zombie robots. 
 *
 */
public class ZombieMode extends ClassicMode {

    private final String description = "This mode pits a robot against "
            + "a swarm of zombie enemies. Survive as long as you can!";

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Zombie Mode";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
