package net.sf.robocode.mode;

import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import net.sf.robocode.battle.TrackField;
import net.sf.robocode.io.FileUtil;

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
	
	private RacePanel racePanel;
	
	private ArrayList<TrackField> tracks;
	
	private JTextField description;
	private Canvas preview;
	private JList<TrackField> trackList;
	
	
	
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
	
	
	@Override
	public String toString(){
		//overwriting toString() method so our "mode" name is now returned
		return new String("Race Mode");
	}
	
	/**
	 * Returns the description of RaceMODE.
	 * 
	 * @return Returns the description of RaceMODE.
	 */
	public String getDescription(){
		return new String("Race mode allows you to race your robot to be first across the finish line.");
	}
	
	@SuppressWarnings("serial")
	private class RacePanel extends JPanel {
		
		public RacePanel() {
			initialise();
			updateTrackList();
		}
		
		private void initialise() {
			description = new JTextField();
			preview = new Canvas();
			trackList = new JList<TrackField>();
			
			preview.setSize(200, 150);
			
			FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
			this.setLayout(layout);
			
			this.add(trackList);
			this.add(preview);
			this.add(description);
		}
		
		private void updateTrackList() {
			System.out.println(FileUtil.getCwd());
			
			File file = new File(FileUtil.getCwd(), 
					"../robocode.ui/src/main/resources/net/sf/robocode/ui/images/tracks");
			
			File[] trackFiles = FileUtil.getFileList(file, ".bmp"); 
			
			for (File f: trackFiles) {
				tracks.add(new TrackField(f));
				loadTrackImage(f);
			}
			
			if (tracks != null) {
				trackList.setListData((TrackField[])tracks.toArray());
			} 
		}
		
		private void loadTrackImage(File trackFile) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image track = toolkit.getImage(trackFile.getPath());
			Graphics g = null;
			g.drawImage(track, 0, 0, 200, 150, preview);
		}
		
	}
	
	public JPanel getRulesPanel() {
    	if( this.racePanel == null) {
    		this.racePanel = new RacePanel();
    	}
    	return this.racePanel;
	}
}
