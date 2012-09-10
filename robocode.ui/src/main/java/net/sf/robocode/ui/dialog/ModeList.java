package net.sf.robocode.ui.dialog;

import javax.swing.JLabel;
import javax.swing.JList;

public class ModeList extends JList {
	
	private NewBattleModeTab modeTab;
	
	public ModeList(NewBattleModeTab modeTab) {
		super();
		this.modeTab = modeTab;
		this.addListSelectionListener(new ModeListListener());
	}
	
	protected NewBattleModeTab getModePanel() {
		return modeTab;
	}
}