package net.sf.robocode.mode;

import java.awt.FlowLayout;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.robocode.battle.TrackField;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.BattleResultsTableModel;
import net.sf.robocode.core.ContainerBase;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRepositoryManagerBase;
import robocode.control.RobotSpecification;

public class RaceMode extends ClassicMode{

	//private class variables
	private int noLaps;
	private BattleResultsTableModel resultsTable;
	//private List<RacePeer> robots;

	private RacePanel racePanel;
	
	private ArrayList<TrackField> tracks;
	
	private JTextField description;
	private JList trackList;
	
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
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void scoreTurnPoints() {
    		
    }

	@SuppressWarnings("serial")
	private class RacePanel extends JPanel {
		
		public RacePanel() {
			initialise();
			updateTrackList();
		}
		
		private void initialise() {
			description = new JTextField();
			trackList = new JList();
			
			FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
			this.setLayout(layout);
			
			this.add(trackList);
			this.add(description);
		}
		
		private void updateTrackList() {
			URL file = RaceMode.class.getResource("resources/net/sf/robocode/tracks/");
			File[] trackFiles = FileUtil.getFileList(file, ".bmp"); 
			if (trackFiles == null) {
				return;
			}
			for (File f: trackFiles) {
				tracks.add(new TrackField(f));
			}
				trackList.setListData(trackFiles);
		}
	}
	
	public JPanel getRulesPanel() {
    	if( this.racePanel == null) {
    		this.racePanel = new RacePanel();
    	}
    	return this.racePanel;
	}
    
	/**
     * Set Results Table at the end of the round for RaceMode.
     * 
     */
    public void setCustomResultsTable() {
    	if (resultsTable == null) {
   			resultsTable = new BattleResultsTableModel();
   		}
    	/*
       	resultsTable.showOverallRank(true);
       	resultsTable.showRobotName(true);
       	resultsTable.showTotalScore(true);
       	*/
       	resultsTable.showRaceScore(true);
       	
       	resultsTable.setTitle("Race Results");     
    }
        
    /**
    * {@inheritDoc}
    */
    public BattleResultsTableModel getCustomResultsTable() {
      	return resultsTable;
    }
    
    @Override
    public boolean isRoundOver(int endTimer, int time) {
		return (endTimer > 5 * time);
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
	
	@Override
	public boolean allowsOneRobot() {//disable annoying message box
		return true;
	}
}
