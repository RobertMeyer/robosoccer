package net.sf.robocode.ui.dialog;

import javax.swing.*;

import net.sf.robocode.mode.*;
import java.awt.*;

public class NewBattleModeTab extends JPanel {
	
	private JList modeList;
	
	private DefaultListModel modeListModel;
	
	private IMode modes[] = { 
		
		// ----- Add your mode here! ------
		new ClassicMode(), 
		new SlowMode()
		
	};
	
	public NewBattleModeTab() {
		super();
		initialize();
	}
	
	public void setup() {
		initialize();
	}
	
	public IMode getSelectedMode() {
		Object[] selected = modeList.getSelectedValues();
		if(selected.length > 0){
			return (IMode) selected[0];
		}
		return null;
	}
	
	private void initialize() {
		JPanel j = new JPanel();

		j.setLayout(new GridLayout(3, 2, 5, 5));
		j.setBorder(BorderFactory.createEtchedBorder());
		j.add(getModeList());
		
		add(j);
	}
	
	private JList getModeList() {
		if (modeList == null) {
			modeList = new JList();
			modeList.setModel(modeListModel());
			modeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return modeList;
	}
	
	private ListModel modeListModel() {
		modeListModel = new DefaultListModel();
		int index = 0;
		for(IMode mode : modes) {
			modeListModel.add(index++, mode);
		}
		return modeListModel;
	}
	
	
}
