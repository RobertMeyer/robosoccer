package net.sf.robocode.mode;

import java.util.ArrayList;

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
    
<<<<<<< HEAD
    private final RobocodeEngine engine = new RobocodeEngine();
=======
    final IRepositoryManagerBase repository = ContainerBase.getComponent(IRepositoryManagerBase.class);
    private BattlePeers peers;
>>>>>>> origin/master

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
    	if (peers != null){
    		this.peers = peers;
    	}
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
<<<<<<< HEAD
=======
    
	@Override
	public boolean allowsOneRobot() {
		return true;
	}
	
	@Override
	public boolean isRoundOver(int endTimer, int time) {
		boolean roundOver = false;
		if (peers != null){
			roundOver = true;
			for (RobotPeer robotPeer : peers.getRobots()) {
				if (!robotPeer.isZombie() && !robotPeer.isDead()){
					roundOver = false;
				}
			}
			if(roundOver){
				peers.removeRobots(getZombies(peers));
			}
		}
		return endTimer > time*5;
	}
	
	private ArrayList<RobotPeer> getZombies(BattlePeers peers){
		ArrayList<RobotPeer> zombies = new ArrayList<RobotPeer>();
		for (RobotPeer peer : peers.getRobots()){
			if(peer.isZombie()){
				zombies.add(peer);
			}
		}
		return zombies;
	}
>>>>>>> origin/master
}
