package net.sf.robocode.ui.dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.KillstreakTracker;

public class KillstreakCheckbox extends JCheckBox{

	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public KillstreakCheckbox(BattleProperties bp) {
        this.setText("Killstreaks");
        this.bp = bp;
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
              KillstreakCheckbox box = (KillstreakCheckbox) actionEvent.getSource();
              KillstreakTracker.enableKillstreaks(box.getModel().isSelected());
            }
          };
        this.addActionListener(actionListener);   
    }
	
	
	
}
