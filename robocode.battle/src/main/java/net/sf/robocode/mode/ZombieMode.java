package net.sf.robocode.mode;

import java.util.ArrayList;

import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.BattleResultsTableModel;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.core.ContainerBase;
import net.sf.robocode.repository.IRepositoryManagerBase;
import robocode.control.RobotSpecification;

/**
 * 
 * This class models "Zombie Mode", a Robocode mode in which a single robot is pit against a swarm of
 * zombie robots. 
 *
 */
public class ZombieMode extends ClassicMode {

    private final String description = "This mode pits a robot against "
            + "a swarm of zombie enemies. Survive as long as you can!";
    
    final IRepositoryManagerBase repository = ContainerBase.getComponent(IRepositoryManagerBase.class);
    private BattlePeers peers;

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
	    	RobotSpecification[] specs = repository.loadSelectedRobots("sampleex.NormalZombie");
	    	
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
	
	@Override
	public void robotKill(RobotPeer owner, RobotPeer otherRobot) {
		if(!owner.isZombie()){
			owner.getRobotStatistics().scoreKill();
		}
	}
	
    /**
     * Setup for FlagMode to just display the rank, the name, the total score
     * and the flag points
     */
    public void setCustomResultsTable() {
    	if (resultsTable == null) {
			resultsTable = new BattleResultsTableModel();
		}
    	
    	resultsTable.showOverallRank(true);
    	resultsTable.showRobotName(true);
    	resultsTable.showKills(true, "Kills");
    	
    	resultsTable.setTitle("Zombie Results");
    }
    
    /**
     * {@inheritDoc}
     */
    public BattleResultsTableModel getCustomResultsTable() {
    	return resultsTable;
    }
}
