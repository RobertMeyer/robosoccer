package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.BattleProperties;
//import net.sf.robocode.sound.BackgroundMusicTracker; //BROKEN IMPORT WAS KILLING MVN INSTALL

public class BackgroundMusicCheckbox extends JCheckBox{
	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public BackgroundMusicCheckbox(BattleProperties bp) {
		this.bp = bp;
        this.setText("Background Music");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
              BackgroundMusicCheckbox mbox = (BackgroundMusicCheckbox) actionEvent.getSource();
              //BackgroundMusicTracker b = new BackgroundMusicTracker(); 
              //BackgroundMusicTracker.enableMusic(mbox.getModel().isSelected());
              //BackgroundMusicTracker.updateStatus();
            }
          };	
        this.addActionListener(actionListener);   
    }

}
