package net.sf.robocode.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import net.sf.robocode.ui.IWindowManager;
import robocode._RobotBase;

@SuppressWarnings("serial")
public class NewMinionsModeTab extends JPanel{
	
	private JPanel mainPanel;
	private JButton selectMinionAtk = new JButton();
	private JButton selectMinionDef = new JButton();
	private JButton selectMinionUtl = new JButton();
	private JTextField minionAtkTxt = new JTextField();
	private JTextField minionDefTxt = new JTextField();
	private JTextField minionUtlTxt = new JTextField();
	private JButton dlgOkBtn = new JButton();
	private JButton dlgCancelBtn = new JButton();
	private IWindowManager window;
	private CustomDialog selectMinion;
	private int currentSelecting = -1;
	private int currentIndex = -1;
	private String currentSelection = "";
	
	private class buttonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			//Add button handling code here.
			//Check the caller's name etc.

			Object caller = ev.getSource();
			if(caller == selectMinionAtk) {
				//Create attack selection popup
				currentSelecting = _RobotBase.MINION_TYPE_ATK;
				selectMinion = new CustomDialog(window.getRobocodeFrame(),"Select minions",true,"ATTACK");
			}
			else if(caller == selectMinionDef) {
				//Create defence selection popup
				currentSelecting = _RobotBase.MINION_TYPE_DEF;
				selectMinion = new CustomDialog(window.getRobocodeFrame(),"Select minions",true,"DEFENCE");
			}
			else if(caller == selectMinionUtl) {
				//Create utility selection popup.
				currentSelecting = _RobotBase.MINION_TYPE_UTL;
				selectMinion = new CustomDialog(window.getRobocodeFrame(),"Select minions",true,"UTILITY");
			}
			currentIndex = selectMinion.minionList.getSelectedIndex();
			currentSelection = selectMinion.minionList.getSelectedValue().toString();
			if(currentIndex >= 0) {
				switch(currentSelecting){
				case _RobotBase.MINION_TYPE_ATK:
					minionAtkTxt.setText(currentSelection);
					break;
				case _RobotBase.MINION_TYPE_DEF:
					minionDefTxt.setText(currentSelection);
					break;
				case _RobotBase.MINION_TYPE_UTL:
					minionUtlTxt.setText(currentSelection);
					break;
				}
			}
		}
	}
	public class CustomDialog extends JDialog{
		private class buttonHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ev) {
				CustomDialog.this.setVisible(false);
			}
		}

		JList minionList = new JList();
		
		CustomDialog(Frame parent, String title,boolean modal,String type){
			super(parent,title,modal);
			int width = 300, height = 300;

			
			String[] minionRobots = { type , type , type , type , type};
			
			minionList.setVisibleRowCount(5);
			minionList.setAlignmentX(LEFT_ALIGNMENT);
			minionList.setListData(minionRobots);
			minionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			Panel p1 = new Panel(new FlowLayout(FlowLayout.LEFT));
			Label question = new Label("Select your minions");
			p1.add(question);
			
			Panel p2 = new Panel(new FlowLayout(FlowLayout.RIGHT));
			dlgOkBtn.setText("Ok");
			dlgOkBtn.addActionListener(new buttonHandler());
			dlgCancelBtn.setText("Cancel");
			dlgCancelBtn.addActionListener(new buttonHandler());
			p2.add(dlgOkBtn);
			p2.add(dlgCancelBtn);
			
			add(p1, BorderLayout.NORTH);
			add(p2, BorderLayout.SOUTH);
			add(minionList,BorderLayout.CENTER);
			
			this.setSize(width,height);
			this.setLocationRelativeTo(parent);
			this.setVisible(true);
			
		}
	}
	
	
	public NewMinionsModeTab(IWindowManager window) {
		super();
		this.window = window;
		runTab();
	}
	
	public void runTab(){
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout());
		add(mainPanel());
		setVisible(true);		
	}
	
	private JPanel mainPanel(){
		if(mainPanel == null){
			
			mainPanel = new JPanel();
			GridBagLayout layout = new GridBagLayout();
			layout.columnWidths = new int[]{0, 0};
			layout.rowHeights = new int[]{0, 0};
			layout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			layout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};

			mainPanel.setLayout(layout);
			
			createMinionPanel(0, 0, "Attack", minionAtkTxt, selectMinionAtk);
			
			createMinionPanel(1, 0, "Defence", minionDefTxt, selectMinionDef);
			
			createMinionPanel(0, 1, "Utility", minionUtlTxt, selectMinionUtl);
			
			createGlobalSettings();
		}
		
		return mainPanel;
	}

	private void createGlobalSettings() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		//Create panel
		JPanel globalSetting = new JPanel();
		JCheckBox enableMinion = new JCheckBox("Enable Minions");
		enableMinion.setRolloverEnabled(true);
		globalSetting.setBorder(getTitledBorder("Global Settings"));
		globalSetting.add(enableMinion);
		mainPanel.add(globalSetting,constraints);
	}

	private void createMinionPanel(int gridx, int gridy, String panelStr, JTextField field, JButton btn) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		mainPanel.add(minionSelectionPanel(panelStr, field, btn), constraints);
	}
	
	private Border getTitledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
	}
	
	private JPanel minionSelectionPanel(String panelStr, JTextField field, JButton btn) {
		JPanel minionSelect = new JPanel();
		field.setText("<Default>");
		field.setEditable(false);
		btn.setText("Select Minion");
		btn.addActionListener(new buttonHandler());
		minionSelect.setBorder(getTitledBorder(panelStr + " Minions"));
		minionSelect.add(field);
		minionSelect.add(btn);
		return minionSelect;
	}	
}
