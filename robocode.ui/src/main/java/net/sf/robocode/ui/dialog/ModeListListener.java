package net.sf.robocode.ui.dialog;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ModeListListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		ModeList list = (ModeList) e.getSource();
		list.getModePanel().updateModePanel();
	}

}
