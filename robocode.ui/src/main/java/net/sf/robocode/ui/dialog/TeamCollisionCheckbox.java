/**
 * @author Team MCJJ
 */
package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.TeamCollisionTracker;

public class TeamCollisionCheckbox extends JCheckBox{
	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public TeamCollisionCheckbox(BattleProperties bp){
		this.bp = bp;
        this.setText("Team Collision");
        ActionListener actionListener = new ActionListener(){
        	public void actionPerformed(ActionEvent actionEvent){
        		TeamCollisionCheckbox box = (TeamCollisionCheckbox) actionEvent.getSource();
        		TeamCollisionTracker.enableFriendlyFire(box.getModel().isSelected());
        	}
        };
        this.addActionListener(actionListener);
  }
	

}
