package net.sf.robocode.ui.dialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.robocode.ui.IWindowManager;

/**
 * A dialog box which inherits the selected robot list from the original 
 * UI dialog, triggered after players press the Start Game button. Users
 * then have to split the original list of robots into two even teams of
 * robots.
 * @author Robert Meyer
 *
 */
@SuppressWarnings("serial")
public class SoccerTeamSelectDialog extends JDialog implements WizardListener{
	
	private final EventHandler eventHandler = new EventHandler();
	
	private List<AvailableRobotsPanel.ItemWrapper> robotsList;
	private List<AvailableRobotsPanel.ItemWrapper> team1RobotsList = 
			new ArrayList<AvailableRobotsPanel.ItemWrapper>();
	private List<AvailableRobotsPanel.ItemWrapper> team2RobotsList = 
			new ArrayList<AvailableRobotsPanel.ItemWrapper>();
	
	private JPanel mainPanel;
	private JPanel selectionPanel;
	private JPanel buttonPanel;
	
	private JList allRobotsList;
	private JList team1List;
	private JList team2List;
	
	private JButton resetButton;
	private JButton startButton;
	private JButton addT1Button;
	private JButton addT2Button;
	private JButton removeT1Button;
	private JButton removeT2Button;
	private JButton quickStartButton;
	
	private boolean isReadyToBattle;

	public SoccerTeamSelectDialog(IWindowManager windowManager) {
		super(windowManager.getRobocodeFrame(), true);
	}

	public void setup(List<AvailableRobotsPanel.ItemWrapper> robots) {
		this.robotsList = adjustAvailableRobotsList(robots);
		initialize();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Initialise the dialog window, setting the minimum window size.
	 */
	private void initialize() {
		setTitle("Select Teams");
		this.setMinimumSize(new Dimension(700, 550));
		this.setResizable(true);
		this.add(getMainPanel());
		this.isReadyToBattle = false;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * JList containing all of the selected robots from the previous
	 * selection step (original selection dialog implementation).
	 * @return JList object for all available robots.
	 */
	private JList getAllRobotsList() {
		if (allRobotsList == null) {
			allRobotsList = new JList();
			allRobotsList.setPrototypeCellValue("Prototype Value 12345");
			allRobotsList.setModel(new AllRobotsModel());
			allRobotsList.setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			MouseListener mouseListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			};

			allRobotsList.addMouseListener(mouseListener);
		}
		return allRobotsList;
	}
	
	/**
	 * Team 1 robot list.
	 * @return JList object for team 1.
	 */
	private JList getTeam1List() {
		if (team1List == null) {
			team1List = new JList();
			team1List.setPreferredSize(new Dimension(100, 100));
			team1List.setModel(new Team1Model());
			team1List.setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			MouseListener mouseListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			};

			team1List.addMouseListener(mouseListener);
		}
		return team1List;
	}
	
	/**
	 * Team 2 robot list.
	 * @return JList object for team 2.
	 */
	private JList getTeam2List() {
		if (team2List == null) {
			team2List = new JList();
			team2List.setPreferredSize(new Dimension(100, 100));
			team2List.setModel(new Team2Model());
			team2List.setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			MouseListener mouseListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			};

			team2List.addMouseListener(mouseListener);
		}
		return team2List;
	}
	
	/**
	 * Main JPanel containing all of the ui elements.
	 * @return Main JPanel object.
	 */
	private JPanel getMainPanel() {
		if(mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;					
			mainPanel.add(getSelectionPanel(), c);

			c.gridy = 1;
			mainPanel.add(getButtonPanel(), c);
		}
		return mainPanel;
	}
	
	/**
	 * JPanel containing the JList objects from which 
	 * users can select robots to be added to either team.
	 * @return Select JPanel object.
	 */
	private JPanel getSelectionPanel() {
		if(selectionPanel == null) {
			selectionPanel = new JPanel();
			selectionPanel.setLayout(new GridLayout(1, 3));
			selectionPanel.setMinimumSize(new 
					Dimension((this.getMinimumSize().width - 20), 400));
			
			// List for current team 1 robot selection.
			selectionPanel.add(createListPane("Team 1", getTeam1List()));
			
			// List of all robots.
			selectionPanel.add(createListPane("Available Robots", 
					getAllRobotsList()));
			
			// List for current team 2 robot selection.
			selectionPanel.add(createListPane("Team 2", getTeam2List()));
		}
		return selectionPanel;
	}
	
	/**
	 * JPanel containing all of the ui buttons.
	 * @return Container for JButton objects.
	 */
	private JPanel getButtonPanel() {
		if(buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(3, 3, 5, 5));
			buttonPanel.add(getAddT1Button());
			buttonPanel.add(getResetButton());
			buttonPanel.add(getAddT2Button());
			buttonPanel.add(getRemoveT1Button());
			buttonPanel.add(getStartButton());
			buttonPanel.add(getRemoveT2Button());
			buttonPanel.add(new JLabel(""));
			buttonPanel.add(getQuickStartButton());
		}
		return buttonPanel;
	}
	
	/**
	 * Resets the current team lists, returning their entries to
	 * the all robots list.
	 * @return Reset button.
	 */
	private JButton getResetButton() {
		if(resetButton == null) {
			resetButton = new JButton();
			resetButton.setText("Reset Teams");
			resetButton.addActionListener(eventHandler);
			resetButton.setEnabled(false);
			resetButton.setToolTipText("Resets the current " +
					"team selections.");
		}
		return resetButton;
	}
	
	/**
	 * Checks that the all robots list is empty and
	 * that both team lists are of equal size. If this is true
	 * the dialog window closes and the game begins, otherwise
	 * a warning dialog appears asking the user to create
	 * two even teams.
	 * @return Start button.
	 */
	private JButton getStartButton() {
		if(startButton == null) {
			startButton = new JButton();
			startButton.setText("Start Game");
			startButton.addActionListener(eventHandler);
			startButton.setEnabled(false);
			startButton.setToolTipText("Start the battle. " +
					"Teams must be of equal size and the " +
					"original list of robots must be empty " +
					"for the battle to begin.");
		}
		return startButton;
	}
	
	/**
	 * Adds the current selection of robots from the all
	 * robots list to the team 1 list.
	 * @return Add robots to team 1 button.
	 */
	private JButton getAddT1Button() {
		if(addT1Button == null) {
			addT1Button = new JButton();
			addT1Button.setText("Add Robot to Team 1");
			addT1Button.addActionListener(eventHandler);
			addT1Button.setToolTipText("Add the selected " +
					"robots to team 1.");
		}
		return addT1Button;
	}
	
	/**
	 * Removes the current selection of robots from the 
	 * team 1 list and adds them back to the all robots
	 * list.
	 * @return Remove robots from team 1 button.
	 */
	private JButton getRemoveT1Button() {
		if(removeT1Button == null) {
			removeT1Button = new JButton();
			removeT1Button.setText("Remove Robot from Team 1");
			removeT1Button.addActionListener(eventHandler);
			removeT1Button.setToolTipText("Remove the selected " +
					"robots from team 1.");
		}
		return removeT1Button;
	}
	
	/**
	 * Adds the current selection of robots from the all
	 * robots list to the team 2 list.
	 * @return Add robots to team 2 button.
	 */
	private JButton getAddT2Button() {
		if(addT2Button == null) {
			addT2Button = new JButton();
			addT2Button.setText("Add Robot to Team 2");
			addT2Button.addActionListener(eventHandler);
			addT2Button.setToolTipText("Add the selected " +
					"robots to team 2.");
		}
		return addT2Button;
	}
	
	/**
	 * Removes the current selection of robots from the 
	 * team 2 list and adds them back to the all robots
	 * list.
	 * @return Remove robots from team 2 button.
	 */
	private JButton getRemoveT2Button() {
		if(removeT2Button == null) {
			removeT2Button = new JButton();
			removeT2Button.setText("Remove Robot from Team 2");
			removeT2Button.addActionListener(eventHandler);
			removeT2Button.setToolTipText("Remove the selected " +
					"robots from team 2.");
		}
		return removeT2Button;
	}
	
	/**
	 * Starts a quick match using the first half of the 
	 * all robots list as team 1 and the second half of 
	 * the list as team 2.
	 * @return Quick start button.
	 */
	private JButton getQuickStartButton() {
		if (quickStartButton == null) {
			quickStartButton = new JButton();
			quickStartButton.setText("Quick Start");
			quickStartButton.addActionListener(eventHandler);
			quickStartButton.setToolTipText("Splits the given " +
					"list of robots into two even teams. The " +
					"first half of the list becomes team 1 and " +
					"the second half becomes team 2.");
		}
		return quickStartButton;
	}
			
	/**
	 * Creates a new JScrollPane containing the given JList. A titled
	 * border is created using the given title string.
	 * @param title Titled border string.
	 * @param list JList object to be added to the JScrollPane.
	 * @return A JScrollPane containing the given JList.
	 */
	private JScrollPane createListPane(String title, JList list) {
		TitledBorder border = new TitledBorder(title);
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(list);
		pane.setBorder(border);
		return pane;
	}
	
	
	// ############### List Models ###############
	
	/**
	 * JList model for all selected Robots.
	 */
	class AllRobotsModel extends AbstractListModel {

		public void changed() {
			fireContentsChanged(this, 0, getSize());
		}

		@Override
		public int getSize() {
			return robotsList.size();
		}

		@Override
		public Object getElementAt(int which) {
			return robotsList.get(which);
		}
	}
	
	/**
	 * JList model for selected Team 1 Robots.
	 */
	class Team1Model extends AllRobotsModel {

		public void changed() {
			fireContentsChanged(this, 0, getSize());
		}

		@Override
		public int getSize() {
			return team1RobotsList.size();
		}

		@Override
		public Object getElementAt(int which) {
			return team1RobotsList.get(which);
		}
		
	}
	
	/**
	 * JList model for selected Team 2 Robots.
	 */
	class Team2Model extends AllRobotsModel {

		public void changed() {
			fireContentsChanged(this, 0, getSize());
		}

		@Override
		public int getSize() {
			return team2RobotsList.size();
		}

		@Override
		public Object getElementAt(int which) {
			return team2RobotsList.get(which);
		}
	}
	
	// ################# Button Logic #################
	
	/**
	 * Event handler for all button clicks.
	 */
	private class EventHandler implements ActionListener, ListSelectionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == getResetButton()) {
				resetRobotLists();
			} else if (e.getSource() == getStartButton()) {
				finishButtonActionPerformed();
			} else if (e.getSource() == getAddT1Button()) {
				removeSelectedRobots(getAllRobotsList(), robotsList, 
						getTeam1List(), team1RobotsList);
			} else if (e.getSource() == getAddT2Button()) {
				removeSelectedRobots(getAllRobotsList(), robotsList, 
						getTeam2List(), team2RobotsList);
			} else if (e.getSource() == getRemoveT1Button()) {
				removeSelectedRobots(getTeam1List(), team1RobotsList, 
						getAllRobotsList(), robotsList);
			} else if (e.getSource() == getRemoveT2Button()) {
				removeSelectedRobots(getTeam2List(), team2RobotsList, 
						getAllRobotsList(), robotsList);
			} else if (e.getSource() == getQuickStartButton()) {
				setQuickStartTeams();
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			
		}
	}
	
	/**
	 * Closes the window when the cancel action is triggered.
	 */
	@Override
	public void cancelButtonActionPerformed() {
		dispose();
	}

	/**
	 * If the teams aren't of equal size and if the original 
	 * list of robots isn't empty, a warning dialog is displayed.
	 * Otherwise, the dialog window is closed and NewBattleDialog
	 * is free to call getSelectedRobotsAsString() to update
	 * battleProperties, after which a new game is created.
	 */
	@Override
	public void finishButtonActionPerformed() {
		if(robotsList.isEmpty() && (team1List.getModel().getSize() == 
				team2List.getModel().getSize())) {
			this.isReadyToBattle = true;
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, 
				"Unable to start the game. Teams must be of equal size.",
				"Team Size Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Shifts robots between lists during team creation. Robots are removed
	 * from the source list and added to the destination list. The 
	 * JList objects are updated and the Start Game and Reset buttons 
	 * are enabled/disabled as required.
	 * @param sourceList Source JList.
	 * @param sourceData List of data for the source JList.
	 * @param destList Destination JList.
	 * @param destData List of data for the destination JList.
	 */
	private void removeSelectedRobots(JList sourceList, 
			List<AvailableRobotsPanel.ItemWrapper> sourceData, 
			JList destList,
			List<AvailableRobotsPanel.ItemWrapper> destData) {
		int sel[] = sourceList.getSelectedIndices();
		AllRobotsModel sourceModel = (AllRobotsModel) sourceList.getModel();
		AllRobotsModel destModel = (AllRobotsModel) destList.getModel();
		
		for(int i = 0; i < sel.length; i++) {
			destData.add(sourceData.get(sel[i] - i));
			sourceData.remove(sel[i] - i);
		}
		sourceList.clearSelection();
		sourceModel.changed();
		destModel.changed();
		checkButtonState();
	}
	
	/**
	 * Checks the state of team lists and sets the start and reset
	 * button states as required.
	 */
	private void checkButtonState() {
		if(robotsList.isEmpty()) {
			getStartButton().setEnabled(true);
		} else {
			getStartButton().setEnabled(false);
		}
		
		if(team1RobotsList.isEmpty() && team2RobotsList.isEmpty()) {
			getResetButton().setEnabled(false);
		} else {
			getResetButton().setEnabled(true);
		}
	}
	
	/**
	 * Clears both team lists and shifts all robots they contained
	 * back to the center selection list.
	 */
	private void resetRobotLists() {
		if(!team1RobotsList.isEmpty()) {
			getTeam1List().clearSelection();
			getTeam1List().setSelectionInterval(0, 
					getTeam1List().getModel().getSize() - 1);
			removeSelectedRobots(getTeam1List(), team1RobotsList, 
					getAllRobotsList(), robotsList);
		}
		
		if(!team2RobotsList.isEmpty()) {
			getTeam2List().clearSelection();
			getTeam2List().setSelectionInterval(0, 
					getTeam2List().getModel().getSize() - 1);
			removeSelectedRobots(getTeam2List(), team2RobotsList, 
					getAllRobotsList(), robotsList);
		}
	}
	
	/**
	 * Creates a string from the names of robots in both team 1 and
	 * team 2 robot lists. Used to update battleProperties with a 
	 * list of selected robots.
	 * @return A string of the selected robots ordered such that
	 * team 1's robots are listed as the first half of the list of
	 * names.
	 */
	public String getSelectedRobotsAsString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < team1RobotsList.size(); i++) {
			if (i != 0) {
				sb.append(',');
			}
			sb.append(team1RobotsList.get(i).getItem()
					.getUniqueFullClassNameWithVersion());
		}
		
		for (int i = 0; i < team2RobotsList.size(); i++) {
			sb.append(',');
			sb.append(team2RobotsList.get(i).getItem()
					.getUniqueFullClassNameWithVersion());
		}
		return sb.toString();
	}
	
	/**
	 * Checks that the given list of robots contains an even number of
	 * robots. If not, the last robot on the list is stripped from the
	 * list and the updated list is returned. Otherwise, the original
	 * list is returned.
	 * @param robots The original list of robots.
	 * @return The updated list of robots, containing an even number
	 * of robots.
	 */
	private List<AvailableRobotsPanel.ItemWrapper> adjustAvailableRobotsList(
			List<AvailableRobotsPanel.ItemWrapper> robots) {
		if(robots.size() % 2 == 1) {
			List<AvailableRobotsPanel.ItemWrapper> adjustedList;
			adjustedList = robots;
			adjustedList.remove(adjustedList.size() - 1);
			return adjustedList;
		}
		return robots;
	}
	
	/**
	 * Used to check if the selection dialog was closed via the close button
	 * or the start button. The start button will set isReadyToBattle to true
	 * allowing the battle to commence. If isReadyToBattle is false nothing
	 * will happen when this dialog is closed.
	 * @return isReadyToBattle
	 */
	public boolean checkReadyToBattle() {
		return isReadyToBattle;
	}
	
	/**
	 * Begins a quick start battle in which the list of robots
	 * is split evenly into two teams. The first half of the list
	 * becomes team 1 and the second half becomes team 2.
	 */
	private void setQuickStartTeams() {
		// Team 2 setup. First half of the robot list.
		getAllRobotsList().clearSelection();
		getAllRobotsList().setSelectionInterval(0, 
				(Math.max(1, getAllRobotsList().getModel().getSize() / 2) - 1));
		removeSelectedRobots(getAllRobotsList(), robotsList, 
				getTeam1List(), team1RobotsList);
		
		// Team 2 setup. Second half of the robot list.
		getAllRobotsList().clearSelection();
		getAllRobotsList().setSelectionInterval(0, 
				(getAllRobotsList().getModel().getSize() - 1));
		removeSelectedRobots(getAllRobotsList(), robotsList, 
				getTeam2List(), team2RobotsList);
		
		// Begin the battle.
		this.isReadyToBattle = true;
		dispose();
	}
}
