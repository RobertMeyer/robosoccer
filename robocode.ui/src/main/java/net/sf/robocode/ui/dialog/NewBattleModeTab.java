package net.sf.robocode.ui.dialog;

import javax.swing.*;

import net.sf.robocode.mode.*;
import java.awt.*;
import java.util.Hashtable;
/**
 * 
 * add javadoc here! eventually
 *
 */
public class NewBattleModeTab extends JPanel {
	
	//list of available modes
	private ModeList modeList;
	//description of each mode
	private JLabel description = new JLabel("");
	
	private DefaultListModel modeListModel;
	
	private JPanel currentModeRulesPanel;
	
	private IMode modes[] = { 
		
		// ----- Add your mode here! ------
		new ClassicMode(), 
		new SlowMode(), 
		new DifferentWeapons(), 
		new RaceMode(),
		new SoccerMode(),
		new ZombieMode(),
		new FlagMode(),
		new RicochetMode(),
		new ItemMode(),
		new ObstacleMode()
	};
	
	public NewBattleModeTab() {
		super();
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
		
		modeList = new ModeList(this);
		modeList.setModel(modeListModel());
		modeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		currentModeRulesPanel = new JPanel();
		
		setLayout(new GridLayout(3,1,5,5));
		setBorder(BorderFactory.createEtchedBorder());
		add(getModeList());
		add(getDescriptionLabel());
		
	}
	
	private JList getModeList() {
		return modeList;
	}
	
	public JLabel getDescriptionLabel() {
		return description;
	}
	
	public void updateModePanel() {
		description.setText(getSelectedMode().getDescription());
		if(currentModeRulesPanel != null){
			remove(currentModeRulesPanel);
		}
		
		currentModeRulesPanel = getSelectedMode().getRulesPanel();
		
		if(currentModeRulesPanel != null){
			add(currentModeRulesPanel);
		}
		repaint();
	}
	
	public Hashtable<String, Object> getSelectedModeRulesValues() {
		return getSelectedMode().getRulesPanelValues();
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
