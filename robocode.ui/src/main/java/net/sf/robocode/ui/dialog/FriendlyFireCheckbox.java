/**
 * @author Jonathan W
 * FriendlyFireCheckBox deals with the 'listening' to the condition of the Friendly Fire Checkbox.
 * There is a tracker in it to tracker the condition and it will be send to bulletpeer to activate the function.
 */

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
