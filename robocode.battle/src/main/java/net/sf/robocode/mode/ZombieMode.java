package net.sf.robocode.mode;

import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * 
 * This class models "Zombie Mode", a Robocode mode in which a single robot is pit against a swarm of
 * zombie robots. 
 *
 */
public class ZombieMode extends ClassicMode {

    private final String description = "This mode pits a robot against "
            + "a swarm of zombie enemies. Survive as long as you can!";
    
    private final RobocodeEngine engine = new RobocodeEngine();

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
    
    public void addRobots(int currentTurn, BattlePeers peers){
    	if(currentTurn % 50 == 0) {
	    	RobotSpecification[] specs = engine.getLocalRepository("sampleex.NormalZombie");
	    	
	    	RobotPeer zombie = new RobotPeer(peers.getBattle(),
					peers.getHostManager(),
					specs[0],
					0,
					null,
					peers.getBattle().getRobotsCount());
	    	
	    	peers.addRobot(zombie);
	    	zombie.initializeRound(peers.getRobots(), null);
	    	zombie.startRound(0, 0);
    	}
    }
}
