package net.sf.robocode.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuListener;

import net.sf.robocode.ui.IWindowManager;
import net.sf.robocode.ui.WindowManager;

@SuppressWarnings("serial")
public class NewMinionsModeTab extends JPanel{
	
	private JPanel mainPanel;
	private JButton selectMinionAtk;
	private JButton selectMinionDef;
	private JButton selectMinionUtl;
	private IWindowManager window;
	private class buttonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			//Add button handling code here.
			//Check the caller's name etc.
			
			Object caller = ev.getSource();
			if(caller == selectMinionAtk) {
				//Create attack selection popup
				Dialog createPopup = new CustomDialog(window.getRobocodeFrame(),"Select minions",true,"ATTACK");
			}
			else if(caller == selectMinionDef) {
				//Create defence selection popup
				Dialog createPopup = new CustomDialog(window.getRobocodeFrame(),"Select minions",true,"DEFENCE");
			}
			else if(caller == selectMinionUtl) {
				//Create utility selection popup.
				Dialog createPopup = new CustomDialog(window.getRobocodeFrame(),"Select minions",true,"UTILITY");
			}
		}
		
	}
	class CustomDialog extends JDialog{
		CustomDialog(Frame parent, String title,boolean modal,String type){
			super(parent,title,modal);
			
			JList list = new JList();
			
			String[] minionRobots = { type , type , type , type , type};
			list.setVisibleRowCount(5);
			list.setAlignmentX(LEFT_ALIGNMENT);
			list.setListData(minionRobots);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			
			Panel p1 = new Panel(new FlowLayout(FlowLayout.LEFT));
			Label question = new Label("Select your minions");
			p1.add(question);
			
			Panel p2 = new Panel(new FlowLayout(FlowLayout.RIGHT));
			Button OK = new Button("OK");
			Button CANCEL = new Button("CANCEL");
			p2.add(OK);
			p2.add(CANCEL);
			
			add(p1, BorderLayout.NORTH);
			add(p2, BorderLayout.SOUTH);
			add(list,BorderLayout.CENTER);
			this.setSize(300,300);
			this.show();
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

			
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.weightx = 1;
			constraints.gridx = 0;
			constraints.gridy = 0;
			mainPanel.add(attackType(),constraints);
			
			GridBagConstraints constraints1 = new GridBagConstraints();
			constraints1.fill = GridBagConstraints.BOTH;
			constraints1.gridx = 1;
			constraints1.gridy = 0;
	
			mainPanel.add(defensiveType(), constraints1);
			
			GridBagConstraints constraints2 = new GridBagConstraints();
			constraints2.fill = GridBagConstraints.BOTH;
			constraints2.gridx = 0;
			constraints2.gridy = 1;
			mainPanel.add(ultilityType(),constraints2);
			
			GridBagConstraints constraints3 = new GridBagConstraints();
			constraints3.fill = GridBagConstraints.BOTH;
			constraints3.gridx = 1;
			constraints3.gridy = 1;
			mainPanel.add(globalSetting(),constraints3);
		}
		
		return mainPanel;
	}
	
	private Border getTitledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
	}
	
	private JPanel attackType(){
		
		JPanel attackType = new JPanel();
		TextField defaultSelection = new TextField("<Default>");
		defaultSelection.setEditable(false);
		selectMinionAtk = new JButton("Select Minion");
		selectMinionAtk.addActionListener(new buttonHandler());
		attackType.setBorder(getTitledBorder("Attack Minions"));
		attackType.add(defaultSelection);
		attackType.add(selectMinionAtk);
		

		return attackType;
		
	}
	
	private JPanel defensiveType(){
		
		JPanel defensiveType = new JPanel();
		TextField defaultSelection = new TextField("<Default>");
		defaultSelection.setEditable(false);
		selectMinionDef = new JButton("Select Minion");
		selectMinionDef.addActionListener(new buttonHandler());
		defensiveType.setBorder(getTitledBorder("Defense Minions"));
		defensiveType.add(defaultSelection);
		defensiveType.add(selectMinionDef);
		
		return defensiveType;
		
	}
	
	private JPanel ultilityType(){
		
		JPanel ultilityType = new JPanel();
		TextField defaultSelection = new TextField("<Default>");
		defaultSelection.setEditable(false);
		selectMinionUtl = new JButton("Select Minion");
		selectMinionUtl.addActionListener(new buttonHandler());
		ultilityType.setBorder(getTitledBorder("Ultility Minions"));
		ultilityType.add(defaultSelection);
		ultilityType.add(selectMinionUtl);
		
		return ultilityType;
		
	}
	
	private JPanel globalSetting(){
		
		JPanel globalSetting = new JPanel();
		JCheckBox enableMinion = new JCheckBox("Enabel Minion");
		enableMinion.setRolloverEnabled(true);
		globalSetting.setBorder(getTitledBorder("Global Settings"));
		globalSetting.add(enableMinion);
		
		return globalSetting;
		
	}
	
	
}
