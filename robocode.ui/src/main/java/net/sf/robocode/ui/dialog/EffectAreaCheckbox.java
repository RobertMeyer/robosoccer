package net.sf.robocode.ui.dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import net.sf.robocode.battle.BattleProperties;

public class EffectAreaCheckbox extends JCheckBox{

	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public EffectAreaCheckbox(BattleProperties bp) {
        this.setText("Effect areas");
        this.bp = bp;
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
              EffectAreaCheckbox box = (EffectAreaCheckbox) actionEvent.getSource();
              box.bp.setEffectArea(box.getModel().isSelected());
            }
          };
        this.addActionListener(actionListener);   
    }
	
	
	
}
