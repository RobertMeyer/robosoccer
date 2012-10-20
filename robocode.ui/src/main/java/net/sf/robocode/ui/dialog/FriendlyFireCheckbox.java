package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.FriendlyFireTracker;

public class FriendlyFireCheckbox extends JCheckBox {
	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public FriendlyFireCheckbox(BattleProperties bp){
		this.bp = bp;
        this.setText("Team Friendly Fire");
        ActionListener actionListener = new ActionListener(){
        	public void actionPerformed(ActionEvent actionEvent){
        		FriendlyFireCheckbox box = (FriendlyFireCheckbox) actionEvent.getSource();
        		FriendlyFireTracker.enableFriendlyFire(box.getModel().isSelected());
        	}
        };
        this.addActionListener(actionListener);
  }
}
