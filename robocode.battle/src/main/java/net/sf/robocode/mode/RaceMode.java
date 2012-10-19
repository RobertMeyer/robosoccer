package net.sf.robocode.mode;

import net.sf.robocode.battle.BattlePeers;
//import net.sf.robocode.battle.RaceBattlePeers;
//import net.sf.robocode.battle.peer.RacePeer;
import net.sf.robocode.core.ContainerBase;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRepositoryManagerBase;
import robocode.control.RobotSpecification;

/**
 * Mode class used to implement Racing Mode functional enhancement. See ticket #32
 * 
 * @author Team - GoGoRobotRacer
 * @author s4203648
 * @author s42008024
 */

public class RaceMode extends ClassicMode{

	//private class variables
	private int noLaps;
	//private List<RacePeer> robots;
	
	final IRepositoryManagerBase repository = ContainerBase.getComponent(IRepositoryManagerBase.class);
	
	/**
	 * Called from the GUI to set the number of laps in the race.
	 * <p>
	 * @param laps Number of laps in the race. 0 < noLaps.
	 * 
	 */
	public void setLaps(int laps){
		if(0 <= laps){
			throw new IllegalArgumentException("Number of laps must be positive");
		}else{
			this.noLaps = laps;
		}
		return;
	}
	
	/**
	 * 
	 * @return noLaps Number of laps in the race.
	 */
	public int getLaps(){
		return this.noLaps;
	}
	
	public void execute(){
		System.out.println("Race Mode.");
	}
	
	public void createPeers(BattlePeers peers, RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			IRepositoryManager repositoryManager) {
		peers.createPeers(battlingRobotsList);
	}
	
	@Override
	public String toString(){
		//overwriting toString() method so our "mode" name is now returned
		return "Race Mode";
	}
	
	/**
	 * Returns the description of RaceMODE.
	 * 
	 * @return Returns the description of RaceMODE.
	 */
	public String getDescription(){
		return new String("Race mode allows you to race your robot to be first across the finish line.");
	}
}
