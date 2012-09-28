package net.sf.robocode.ui.dialog;

import javax.swing.*;
import javax.swing.border.Border;

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
	
	private JPanel currentModePanel;
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
		new Botzilla(),
		new TimerMode(),
		new EliminationMode()
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
		JPanel modePanel = new JPanel();
		JPanel descriptionPanel = new JPanel();
		
		modeList = new ModeList(this);
		modeList.setModel(modeListModel());
		modeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		modeList.setSelectedIndex(0);
		modeList.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		currentModePanel = new JPanel();
		currentModePanel.setLayout(new GridLayout(2, 1, 5, 5));
		currentModeRulesPanel = new JPanel();
		
		modePanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		modePanel.setBorder(getTitledBorder("Available Modes"));
		modePanel.add(getModeList());
		currentModePanel.add(getDescriptionLabel());
		descriptionPanel.setBorder(getTitledBorder("Mode Description"));
		descriptionPanel.add(getCurrentModePanel());
		
		add(modePanel);
		add(descriptionPanel);
	}
	
	private JList getModeList() {
		return modeList;
	}
	
	private Border getTitledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
	}
	
	private JPanel getCurrentModePanel() {
		return currentModePanel;
	}
	
	public JLabel getDescriptionLabel() {
		return description;
	}
	
	public void updateModePanel() {
		description.setText(getSelectedMode().getDescription());
		if(currentModeRulesPanel != null){
			currentModePanel.remove(currentModeRulesPanel);
		}
		
		currentModeRulesPanel = getSelectedMode().getRulesPanel();
		
		if(currentModeRulesPanel != null){
			currentModePanel.add(currentModeRulesPanel);
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
