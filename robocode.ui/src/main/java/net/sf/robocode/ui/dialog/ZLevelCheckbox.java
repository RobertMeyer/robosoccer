package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.ZLevelsEnabler;

public class ZLevelCheckbox extends JCheckBox {
	
	public ZLevelCheckbox() {
		this.setText("Z-Level");

		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZLevelCheckbox box = (ZLevelCheckbox) e.getSource();
				ZLevelsEnabler.enableZRand(box.getModel().isSelected());
			}
		};
		this.addActionListener(l);
	}
}
