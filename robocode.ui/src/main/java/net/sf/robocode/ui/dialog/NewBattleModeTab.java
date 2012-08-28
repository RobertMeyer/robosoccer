package net.sf.robocode.ui.dialog;

import javax.swing.*;

import net.sf.robocode.mode.*;
import java.awt.*;

public class NewBattleModeTab extends JPanel {
	
	private ModeList modeList;
	private JLabel description;
	
	private DefaultListModel modeListModel;
	
	private IMode modes[] = { 
		
		// ----- Add your mode here! ------
		new ClassicMode(), 
		new SlowMode(),
		new DifferentWeapons(),
		new RaceMode()
	};
	
	public NewBattleModeTab() {
		super();
		
		description = new JLabel("");
		
		modeList = new ModeList(description);
		modeList.setModel(modeListModel());
		modeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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

		j.setLayout(new FlowLayout());
		j.setBorder(BorderFactory.createEtchedBorder());
		j.add(getModeList());
		j.add(getDescriptionLabel());
		add(j);
	}
	
	private JList getModeList() {
		return modeList;
	}
	
	public JLabel getDescriptionLabel() {
		return description;
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
