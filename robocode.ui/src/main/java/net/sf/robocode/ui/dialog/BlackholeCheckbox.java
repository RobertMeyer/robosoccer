package net.sf.robocode.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sf.robocode.battle.TeleporterEnabler;

public class BlackholeCheckbox extends JCheckBox {
	public BlackholeCheckbox() {
		this.setText("Blackholes");

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				BlackholeCheckbox checkbox = (BlackholeCheckbox) action.getSource();
				TeleporterEnabler.enableBlackholes(checkbox.getModel().isSelected());
			}
		};
		this.addActionListener(listener);
	}
}