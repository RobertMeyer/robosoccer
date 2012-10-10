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
 *     Flemming N. Larsen
 *     - Code cleanup
 *     Pavel Savara
 *     - number of rows limited
 *******************************************************************************/
package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.sf.robocode.battle.IBattleManager;

/**
 * Pane for Commander controls.
 * Check ConsoleScrollPane if you need to see what this is based off of.
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
    private final static String PAUSE = "PAUSE";
    private final static String ADVANCE = "ADVANCE";
    private final static String RETREAT = "RETREAT";
    private final static String ATTACK = "ATTACK";
    private final static String INCREASE = "INCREASE";
    private final static String DECREASE = "DECREASE";
    private final static String TAUNT = "TAUNT";
    
    // Selected robot details
    int robotIndex;
	
    public CommanderScrollPane(IBattleManager battleManager, int selectedRobot) {
        super();
        this.battleManager = battleManager;
        
        // Specify which robot was selected.
        robotIndex = selectedRobot;
        
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
    		pauseButton.setActionCommand(PAUSE);
    		pauseButton.addActionListener(new CommanderButtonListener());
    		pauseButton.setBackground(new Color(255, 0, 0));
    	}
    	return pauseButton;
    }
    
    public JButton getAdvanceButton() {
    	if (advanceButton == null) {
    		advanceButton = new JButton("ADVANCE");
    		advanceButton.setActionCommand(ADVANCE);
    		advanceButton.addActionListener(new CommanderButtonListener());
    		advanceButton.setBackground(new Color(0, 255, 0));
    	}
    	return advanceButton;
    }
    
    public JButton getRetreatButton() {
    	if (retreatButton == null) {
    		retreatButton = new JButton("RETREAT");
    		retreatButton.setActionCommand(RETREAT);
    		retreatButton.addActionListener(new CommanderButtonListener());
    		retreatButton.setBackground(new Color(255, 255, 0));
    	}
    	return retreatButton;
    }
    
    public JButton getAttackButton() {
    	if (attackButton == null) {
    		attackButton = new JButton("ATTACK");
    		attackButton.setActionCommand(ATTACK);
    		attackButton.addActionListener(new CommanderButtonListener());
    		attackButton.setBackground(new Color(0, 0, 255));
    	}
    	return attackButton;
    }
    
    public JButton getIncreasePowerButton() {
    	if (increasePowerButton == null) {
    		increasePowerButton = new JButton();
    		increasePowerButton.setActionCommand(INCREASE);
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
    		decreasePowerButton.setActionCommand(DECREASE);
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
    		tauntButton.setActionCommand(TAUNT);
    		tauntButton.addActionListener(new CommanderButtonListener());
    		tauntButton.setBackground(new Color(255, 0, 255));
    	}
    	return tauntButton;
    }
    
    public class CommanderButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals(PAUSE)) {
                killButtonActionPerformed();
				//set flag
				//call pause()
				
			} else if (arg0.getActionCommand().equals(ADVANCE)) {
				System.out.println("Advance button pressed.");
				//set flag
				//call scan()
				
			} else if (arg0.getActionCommand().equals(RETREAT)) {
				System.out.println("Retreat button pressed.");
				//set flag
				//call scan()
				
			} else if (arg0.getActionCommand().equals(ATTACK)) {
				System.out.println("Attack button pressed.");
				//set flag
				//call scan()
				
			} else if (arg0.getActionCommand().equals(INCREASE)) {
				System.out.println("Increase Power button pressed.");
				//set flag
				//call increasePower()
				
			} else if (arg0.getActionCommand().equals(DECREASE)) {
				System.out.println("Decrease Power button pressed.");
				//set flag
				//call decreasePower()
				
			} else if (arg0.getActionCommand().equals(TAUNT)) {
				System.out.println("Taunt button pressed.");
				//set flag
				//call taunt()
				
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
