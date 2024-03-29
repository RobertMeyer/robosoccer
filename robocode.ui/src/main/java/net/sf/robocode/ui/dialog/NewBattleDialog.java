/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Matthew Reeder
 *     - Added keyboard mnemonics to buttons and tabs
 *     Flemming N. Larsen
 *     - Code cleanup
 *     - Replaced FileSpecificationVector with plain Vector
 *     - Changed the F5 key press for refreshing the list of available robots
 *       into 'modifier key' + R to comply with other OSes like e.g. Mac OS
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *******************************************************************************/
package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import robocode.equipment.EquipmentSet;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.mode.*;
import net.sf.robocode.repository.IRepositoryItem;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IWindowManager;
import static net.sf.robocode.ui.util.ShortcutUtil.MENU_SHORTCUT_KEY_MASK;

/**
 * @author Mathew A. Nelson (original)
 * @author Matthew Reeder (contributor)
 * @author Flemming N. Larsen (contributor)
 * @author Robert D. Maupin (contributor)
 * @author CSSE2003 Team Forkbomb (equipment)
 */
@SuppressWarnings("serial")
public class NewBattleDialog extends JDialog implements WizardListener {

	private final static int MAX_ROBOTS = 256; // 64;
	private final static int MIN_ROBOTS = 1;
	private final EventHandler eventHandler = new EventHandler();
	private JPanel newBattleDialogContentPane;
	private BattleProperties battleProperties;
	private boolean isOpeningBattle;
	private WizardController wizardController;
	private final IBattleManager battleManager;
	private final ISettingsManager settingsManager;

	private WizardTabbedPane tabbedPane;
	private RobotSelectionPanel robotSelectionPanel;
	private NewBattleBattleFieldTab battleFieldTab;
	private NewBattleRulesTab rulesTab;
	private NewBattleModeTab modeTab;
	private NewMinionsModeTab minionTab;
	private EquipmentPanel equipmentTab = new EquipmentPanel();

	private IWindowManager window;

	public NewBattleDialog(IWindowManager windowManager,
			IBattleManager battleManager, ISettingsManager settingsManager) {
		super(windowManager.getRobocodeFrame(), true);
		this.battleManager = battleManager;
		this.settingsManager = settingsManager;
		this.window = windowManager;
	}

	public void setup(BattleProperties battleProperties, boolean openBattle) {
		this.battleProperties = battleProperties;
		this.isOpeningBattle = openBattle;
		robotSelectionPanel = null;
		initialize();
	}

	@Override
	public void cancelButtonActionPerformed() {
		dispose();
	}

	@Override
	public void finishButtonActionPerformed() {
		if (robotSelectionPanel.getSelectedRobotsCount() > 24) {
			if (JOptionPane.showConfirmDialog(this,
					"Warning:  The battle you are about to start ("
							+ robotSelectionPanel.getSelectedRobotsCount()
							+ " robots) is very large and will consume a lot "
							+ "of CPU and memory.  Do you wish to proceed?",
					"Large Battle Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
				return;
			}
		}

		IMode selectedMode = getBattleModeTab().getSelectedMode();
		if (selectedMode == null) {
			selectedMode = new ClassicMode();
		}

		if (robotSelectionPanel.getSelectedRobotsCount() == 1
				&& !selectedMode.allowsOneRobot()) {
			if (JOptionPane.showConfirmDialog(this,
					"You have only selected one robot.  For normal battles "
							+ "you should select at least 2.\nDo you wish to "
							+ "proceed anyway?", "Just one robot?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
				return;
			}
		}

		battleProperties.setSelectedRobots(getRobotSelectionPanel()
				.getSelectedRobotsAsString());
		battleProperties.setBattlefieldWidth(getBattleFieldTab()
				.getBattleFieldWidth());
		battleProperties.setBattlefieldHeight(getBattleFieldTab()
				.getBattleFieldHeight());
		battleProperties.setNumRounds(getRobotSelectionPanel().getNumRounds());
		battleProperties.setGunCoolingRate(getRulesTab().getGunCoolingRate());
		battleProperties.setInactivityTime(getRulesTab().getInactivityTime());
		battleProperties.setHideEnemyNames(getRulesTab().getHideEnemyNames());
		battleProperties.setInitialPositions(SetRobotPositionString());

		battleProperties.setEquipmentFile(equipmentTab.getFile());

		battleProperties.setModeRules(selectedMode.getRulesPanelValues());
		battleProperties.setBattleMode(selectedMode);

		// Display soccer mode team selection dialog
		if (selectedMode instanceof SoccerMode) {
			if (getRobotSelectionPanel().getSelectedRobots().size() < 2) {
				JOptionPane.showMessageDialog(this,
						"You must select at least two robots "
								+ "for Soccer Mode.", "Invalid selection.",
						JOptionPane.WARNING_MESSAGE);
			} else {
				final SoccerTeamSelectDialog teamSelect = new SoccerTeamSelectDialog(
						window);

				teamSelect.setup(getRobotSelectionPanel().getRobotsList());

				if (teamSelect.checkReadyToBattle() == true) {
					battleProperties.setSelectedRobots(teamSelect
							.getSelectedRobotsAsString());

					dispose();

					battleManager
							.startNewBattle(battleProperties, false, false);
				}
			}

		} else {

			// Dispose this dialog before starting the battle due to
			// pause/resume battle state
			dispose();

			// Start new battle after the dialog has been disposed and hence has
			// called resumeBattle()
			battleManager.startNewBattle(battleProperties, false, false);
		}
	}

	public List<IRepositoryItem> getSelectedRobots() {
		return getRobotSelectionPanel().getSelectedRobots();
	}

	/**
	 * Initialize the class.
	 */
	private void initialize() {
		setTitle("New Battle");
		setPreferredSize(new Dimension(850, 650));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setContentPane(getNewBattleDialogContentPane());
		addCancelByEscapeKey();

		processBattleProperties();
	}

	/**
	 * get the input positions then build the been well formated position string
	 * when user restart a new battle , all robot's start off position will be
	 * cleared
	 * 
	 * @return Position List.toString()
	 */
	private String SetRobotPositionString() {
		StringBuilder setRobotPositionString = new StringBuilder();
		// clear the position information from previous battle
		if (battleProperties.getInitialPositions() != null) {
			battleProperties.setInitialPositionToNull();
		}
		if (robotSelectionPanel.GetPositions() != null) {
			for (int i = 0; i < robotSelectionPanel.GetPositions().size(); i++) {
				setRobotPositionString.append(robotSelectionPanel
						.GetPositions().get(i) + ",");
			}
			return setRobotPositionString.toString();
		} else {
			return null;
		}
	}

	/**
	 * Return the newBattleDialogContentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getNewBattleDialogContentPane() {
		if (newBattleDialogContentPane == null) {
			newBattleDialogContentPane = new JPanel();
			newBattleDialogContentPane.setLayout(new BorderLayout());
			newBattleDialogContentPane.add(getWizardController(),
					BorderLayout.SOUTH);
			newBattleDialogContentPane
					.add(getTabbedPane(), BorderLayout.CENTER);
			newBattleDialogContentPane.registerKeyboardAction(eventHandler,
					"Refresh", KeyStroke.getKeyStroke(KeyEvent.VK_R,
							MENU_SHORTCUT_KEY_MASK),
					JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		}
		return newBattleDialogContentPane;
	}

	/**
	 * Loads the battle's properties into the dialog window.
	 */
	private void processBattleProperties() {
		if (battleProperties == null) {
			return;
		}
		getBattleFieldTab().setBattleFieldWidth(
				battleProperties.getBattlefieldWidth());
		getBattleFieldTab().setBattleFieldHeight(
				battleProperties.getBattlefieldHeight());
		getRulesTab().setGunCoolingRate(battleProperties.getGunCoolingRate());
		getRulesTab().setInactivityTime(battleProperties.getInactivityTime());
		getRulesTab().setHideEnemyNames(battleProperties.getHideEnemyNames());

		// When opening a battle, we use the 'number of rounds' from the battle
		// properties.
		// When starting a new battle, we use the 'number of rounds' from the
		// settings manager instead.
		int numRounds = isOpeningBattle ? battleProperties.getNumRounds()
				: settingsManager.getNumberOfRounds();

		getRobotSelectionPanel().setNumRounds(numRounds);

		equipmentTab.setFile(battleProperties.getEquipmentFile());
	}

	private void addCancelByEscapeKey() {
		String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
		int noModifiers = 0;
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				noModifiers, false);
		InputMap inputMap = getRootPane().getInputMap(
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		inputMap.put(escapeKey, CANCEL_ACTION_KEY);
		AbstractAction cancelAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonActionPerformed();
			}
		};

		getRootPane().getActionMap().put(CANCEL_ACTION_KEY, cancelAction);
	}

	/**
	 * Return the wizardController
	 * 
	 * @return JButton
	 */
	private WizardController getWizardController() {
		if (wizardController == null) {
			wizardController = getTabbedPane().getWizardController();
			wizardController.setFinishButtonTextAndMnemonic("Start Battle",
					'S', 0);
			wizardController.setFocusOnEnabled(true);
		}
		return wizardController;
	}

	/**
	 * Return the tabbedPane.
	 * 
	 * @return JTabbedPane
	 */
	private WizardTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new WizardTabbedPane(this);

			tabbedPane.insertTab("Robots", null, getRobotSelectionPanel(),
					null, 0);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_R);
			tabbedPane.setDisplayedMnemonicIndexAt(0, 0);

			tabbedPane.insertTab("BattleField", null, getBattleFieldTab(),
					null, 1);
			tabbedPane.setMnemonicAt(1, KeyEvent.VK_F);
			tabbedPane.setDisplayedMnemonicIndexAt(1, 6);

			tabbedPane.insertTab("Rules", null, getRulesTab(), null, 2);
			tabbedPane.setMnemonicAt(2, KeyEvent.VK_U);
			tabbedPane.setDisplayedMnemonicIndexAt(2, 1);

			tabbedPane.insertTab("Modes", null, getBattleModeTab(), null, 3);
			tabbedPane.setMnemonicAt(3, KeyEvent.VK_M);
			tabbedPane.setDisplayedMnemonicIndexAt(3, 0);

			tabbedPane.insertTab("Minions", null, getMinionsModeTab(), null, 4);

			tabbedPane.insertTab("Equipment", null, equipmentTab, null, 5);
			tabbedPane.setMnemonicAt(5, KeyEvent.VK_E);
			tabbedPane.setDisplayedMnemonicIndexAt(5, 0);
		}
		return tabbedPane;
	}

	/**
	 * Return the Page property value.
	 * 
	 * @return JPanel
	 */
	private RobotSelectionPanel getRobotSelectionPanel() {
		if (robotSelectionPanel == null) {
			String selectedRobots = "";

			if (battleProperties != null) {
				selectedRobots = battleProperties.getSelectedRobots();
			}
			robotSelectionPanel = net.sf.robocode.core.Container
					.createComponent(RobotSelectionPanel.class);
			final boolean ignoreTeamRobots = false; // TODO do we really want to
													// have this
													// !properties.getOptionsTeamShowTeamRobots();

			robotSelectionPanel.setup(MIN_ROBOTS, MAX_ROBOTS, true,
					"Select robots for the battle", false, false, false, false,
					false, ignoreTeamRobots, selectedRobots);
		}
		return robotSelectionPanel;
	}

	/**
	 * Return the battleFieldTab
	 * 
	 * @return JPanel
	 */
	private NewBattleBattleFieldTab getBattleFieldTab() {
		if (battleFieldTab == null) {
			battleFieldTab = new NewBattleBattleFieldTab();
		}
		return battleFieldTab;
	}

	/**
	 * Return the rulesTab property value.
	 * 
	 * @return robocode.dialog.NewBattleRulesTab
	 */
	private NewBattleRulesTab getRulesTab() {
		if (rulesTab == null) {
			rulesTab = new net.sf.robocode.ui.dialog.NewBattleRulesTab();
		}
		return rulesTab;
	}

	/**
	 * Return the modeTab
	 * 
	 * @return JPanel
	 */
	private NewBattleModeTab getBattleModeTab() {
		if (modeTab == null) {
			modeTab = new NewBattleModeTab();

			// modeTab.setup(); // this was calling
			// NewBattleModeTab.initialise() twice (once already in the
			// constructor).
			// which was making
			// the UI bugged, by adding the label twice. if we want to use the
			// setup() in the future,
			// go to NewBattleModeTab and add whatever u want, but remove
			// initialise() from it.

		}
		return modeTab;
	}

	private NewMinionsModeTab getMinionsModeTab() {
		if (minionTab == null) {
			minionTab = new NewMinionsModeTab(window);
		}
		return minionTab;
	}

	private class EventHandler extends WindowAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Refresh")) {
				getRobotSelectionPanel().refreshRobotList(true);
			}
		}
	}

	// TODO: make the other tabs inline classes.

	/**
	 * It makes much more sense to define these tabs as public inner classes.
	 * 
	 * @author CSSE2003 Team Forkbomb
	 */
	public class EquipmentPanel extends JPanel {
		JFileChooser fileChooser = new JFileChooser();
		JButton openButton = new JButton("Open");
		JTextArea textArea = new JTextArea();
		JScrollPane textAreaScrollPane = new JScrollPane(textArea);

		private File file;

		EquipmentPanel() {
			super();
			textArea.setText(EquipmentSet.defaultDefinitions());
			textArea.setEditable(false);
			setLayout(new BorderLayout());
			add(openButton, BorderLayout.NORTH);
			add(textAreaScrollPane, BorderLayout.CENTER);
			openButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == openButton) {
						int rv = fileChooser
								.showOpenDialog(EquipmentPanel.this);
						if (rv == JFileChooser.APPROVE_OPTION) {
							setFile(fileChooser.getSelectedFile());
						}
					}
				}
			});
		}

		/**
		 * Sets the selected file and loads its contents into the text area.
		 * 
		 * @param file
		 *            the file to be selected.
		 */
		void setFile(File file) {
			if (file == null) {
				return;
			}
			try {
				textArea.setText(FileUtil.readFileToString(file));
				this.file = null;
			} catch (FileNotFoundException e1) {
			}
		}

		/**
		 * @return the file selected by the user.
		 */
		File getFile() {
			return file;
		}
	}
}
