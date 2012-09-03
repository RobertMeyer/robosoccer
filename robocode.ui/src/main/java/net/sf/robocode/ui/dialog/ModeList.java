package net.sf.robocode.ui.dialog;

import javax.swing.JLabel;
import javax.swing.JList;

public class ModeList extends JList {
	
	private JLabel label;
	
	public ModeList(JLabel label) {
		super();
		this.label = label;
		this.addListSelectionListener(new ModeListListener());
	}
	
	protected JLabel getLabel() {
		return label;
	}
}