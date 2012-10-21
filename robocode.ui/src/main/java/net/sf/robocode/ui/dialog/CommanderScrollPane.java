/*******************************************************************************
 * Copyright (c) 2012 Team The Fightin' Mongooses
 *
 * Contributors:
 *  Paul Wade
 *  Chris Irving
 *  Jesse Claven
 *******************************************************************************/
package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import robocode.CommanderEvent;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Pane for Commander controls.
 * Check ConsoleScrollPane if you need to see what this is based off of.
 *
 * @author The Fightin' Mongooses
 */
@SuppressWarnings("serial")
public class CommanderScrollPane extends JPanel {
	
    private final IBattleManager battleManager;
	
	private JButton pauseButton;
    private JButton advanceButton;
    private JButton retreatButton;
    private JButton attackButton;
    private JButton increasePowerButton;
    private JButton decreasePowerButton;
    private JButton tauntButton;
    
    //ActionCommands
    private final static String PAUSE_STRING = "PAUSE";
    private final static String ADVANCE_STRING = "ADVANCE";
    private final static String RETREAT_STRING = "RETREAT";
    private final static String ATTACK_STRING = "ATTACK";
    private final static String INCREASE_STRING = "INCREASE";
    private final static String DECREASE_STRING = "DECREASE";
    private final static String TAUNT_STRING = "TAUNT";
    
    private final static int PAUSE = 1;
    private final static int ADVANCE = 2;
    private final static int RETREAT = 3;
    private final static int ATTACK = 4;
    private final static int INCREASE = 5;
    private final static int DECREASE = 6;
    private final static int TAUNT = 7;
    
    // Index number of the robot that was selected by the user.
    int robotIndex;
    
    // Current battle is passed in in order to be able to manipulate the
    // selected robot.
    Battle battle;
	
    public CommanderScrollPane(IBattleManager battleManager, int selectedRobot) {
        super();
        this.battleManager = battleManager;
        
        // Specify which robot was selected.
        robotIndex = selectedRobot;
        
        // Battle
        this.battle = battleManager.getBattle();
        
        //Set up layout
        this.setLayout(new GridLayout(3, 4, 10, 10));
        
        this.add(new JLabel("   Movement"));
        this.add(getPauseButton());
        this.add(getRetreatButton());
        this.add(getAdvanceButton());
        
        this.add(new JLabel("   Gun Controls"));
        this.add(getDecreasePowerButton());
        this.add(getAttackButton());
        this.add(getIncreasePowerButton());
        
        this.add(new JLabel("   Funsies"));
        this.add(new JPanel());
        this.add(getTauntButton());
        this.add(new JPanel());
    }
        
    public JButton getPauseButton() {
    	if (pauseButton == null) {
    		pauseButton = new JButton("PAUSE");
    		pauseButton.setActionCommand(PAUSE_STRING);
    		pauseButton.addActionListener(new CommanderButtonListener());
    		pauseButton.setBackground(new Color(255, 0, 0));
    	}
    	return pauseButton;
    }
    
    public JButton getAdvanceButton() {
    	if (advanceButton == null) {
    		advanceButton = new JButton("ADVANCE");
    		advanceButton.setActionCommand(ADVANCE_STRING);
    		advanceButton.addActionListener(new CommanderButtonListener());
    		advanceButton.setBackground(new Color(0, 255, 0));
    	}
    	return advanceButton;
    }
    
    public JButton getRetreatButton() {
    	if (retreatButton == null) {
    		retreatButton = new JButton("RETREAT");
    		retreatButton.setActionCommand(RETREAT_STRING);
    		retreatButton.addActionListener(new CommanderButtonListener());
    		retreatButton.setBackground(new Color(255, 255, 0));
    	}
    	return retreatButton;
    }
    
    public JButton getAttackButton() {
    	if (attackButton == null) {
    		attackButton = new JButton("ATTACK");
    		attackButton.setActionCommand(ATTACK_STRING);
    		attackButton.addActionListener(new CommanderButtonListener());
    		attackButton.setBackground(new Color(0, 0, 255));
    	}
    	return attackButton;
    }
    
    public JButton getIncreasePowerButton() {
    	if (increasePowerButton == null) {
    		increasePowerButton = new JButton();
    		increasePowerButton.setActionCommand(INCREASE_STRING);
    		increasePowerButton.addActionListener(new CommanderButtonListener());
    		increasePowerButton.setText("<html><center>INCREASE<br>POWER"
    				+ "</center></html>");
    		increasePowerButton.setBackground(new Color(0, 0, 255));
    	}
    	return increasePowerButton;
    }
    
    public JButton getDecreasePowerButton() {
    	if (decreasePowerButton == null) {
    		decreasePowerButton = new JButton();
    		decreasePowerButton.setActionCommand(DECREASE_STRING);
    		decreasePowerButton.addActionListener(new CommanderButtonListener());
    		decreasePowerButton.setText("<html><center>DECREASE<br>POWER"
    	    		+ "</center></html>");
    		decreasePowerButton.setBackground(new Color(0, 0, 255));
    	}
    	return decreasePowerButton;
    }
    
    public JButton getTauntButton() {
    	if (tauntButton == null) {
    		tauntButton = new JButton("TAUNT");
    		tauntButton.setActionCommand(TAUNT_STRING);
    		tauntButton.addActionListener(new CommanderButtonListener());
    		tauntButton.setBackground(new Color(255, 0, 255));
    	}
    	return tauntButton;
    }
    
    public class CommanderButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			List<RobotPeer> robotList = battle.getRobotList();
			RobotPeer robot = robotList.get(robotIndex);
			
			if (arg0.getActionCommand().equals(PAUSE_STRING)) {
				System.out.println("Pause button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), PAUSE);
				robot.addEvent(event);
				
			} else if (arg0.getActionCommand().equals(ADVANCE_STRING)) {
				System.out.println("Advance button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), ADVANCE);
				robot.addEvent(event);
				
			} else if (arg0.getActionCommand().equals(RETREAT_STRING)) {
				System.out.println("Retreat button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), RETREAT);
				robot.addEvent(event);
				
			} else if (arg0.getActionCommand().equals(ATTACK_STRING)) {
				System.out.println("Attack button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), ATTACK);
				robot.addEvent(event);
				
			} else if (arg0.getActionCommand().equals(INCREASE_STRING)) {
				System.out.println("Increase Power button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), INCREASE);
				robot.addEvent(event);
				
			} else if (arg0.getActionCommand().equals(DECREASE_STRING)) {
				System.out.println("Decrease Power button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), DECREASE);
				robot.addEvent(event);
				
			} else if (arg0.getActionCommand().equals(TAUNT_STRING)) {
				System.out.println("Taunt button pressed.");
				final CommanderEvent event = new CommanderEvent(robot.getName(), TAUNT);
				robot.addEvent(event);
				
			}
		}
    }
    
    /**
     * Is called when the Kill button has been activated
     */
    private void killButtonActionPerformed() {
        battleManager.killRobot(robotIndex);
    }
}
