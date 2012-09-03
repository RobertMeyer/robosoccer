package net.sf.robocode.ui.dialog;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.robocode.mode.IMode;

public class ModeListListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		ModeList<IMode> list = (ModeList) e.getSource();
		list.getLabel().setText(((IMode) list.getSelectedValues()[0]).getDescription());
	}

}
