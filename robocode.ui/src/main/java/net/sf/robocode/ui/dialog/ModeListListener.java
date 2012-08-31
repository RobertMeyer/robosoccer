package net.sf.robocode.ui.dialog;

import java.awt.Container;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.ListModel;

import net.sf.robocode.mode.IMode;

public class ModeListListener implements ListSelectionListener {
	
	private IMode mode;

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		ModeList list = (ModeList) e.getSource();
		list.getLabel().setText(((IMode) list.getSelectedValues()[0]).getDescription());
		
	}

}
