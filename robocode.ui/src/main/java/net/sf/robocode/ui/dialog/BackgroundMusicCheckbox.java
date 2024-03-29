package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.BattleProperties;

//UNCOMMENT THE CODE BELOW WHEN U NEED TO RUN BACKGROUND MUSIC
//import net.sf.robocode.sound.BackgroundMusicTracker;  //PREVIOUSLY BROKE THE MVN INSTALL

/**
 * Background music check box will shows on the main frame
 * When User click on the check box, the music will start playing
 * when user uncheck the check box, the background music will stop.
 * 
 * Every time user click on the check box, the action listener will 
 * be trigger, and a background music tracker will be called to
 * play the background music.
 * 
 * @author Eric
 *
 */


public class BackgroundMusicCheckbox extends JCheckBox{
	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public BackgroundMusicCheckbox(BattleProperties bp) {
		this.bp = bp;
        this.setText("Background Music");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            
            	//UNCOMMENT THE CODE BELOW WHEN YOU NEED TO RUN BACKGROUND MUSIC
            	
              //BackgroundMusicCheckbox mbox = (BackgroundMusicCheckbox) actionEvent.getSource(); 
              //BackgroundMusicTracker.enableMusic(mbox.getModel().isSelected());
              //BackgroundMusicTracker.updateStatus();
            }
          };	
        this.addActionListener(actionListener);   
    }

}
