package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.TeleporterEnabler;

public class TeleporterCheckbox extends JCheckBox {
	public TeleporterCheckbox() {
		this.setText("Teleporters");
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				TeleporterCheckbox checkbox = (TeleporterCheckbox) action.getSource();
				TeleporterEnabler.enableTeleporters(checkbox.getModel().isSelected());
			}
		};
		this.addActionListener(listener);
	}
}