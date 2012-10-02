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
import javax.swing.*;

/**
 * Pane for Commander controls.
 * Check ConsoleScrollPane if you need to see what this is based off of.
 * @author The Fightin' Mongooses
 */
@SuppressWarnings("serial")
public class CommanderScrollPane extends JPanel {
	
	private JButton pauseButton;
    private JButton advanceButton;
    private JButton retreatButton;
    private JButton attackButton;
    private JButton increasePowerButton;
    private JButton decreasePowerButton;
    private JButton tauntButton;
	
    public CommanderScrollPane() {
        super();
        this.setLayout(new GridLayout(3, 4, 10, 10));
        
        this.add(new JTextField("Movement"));
        this.add(getPauseButton());
        this.add(getRetreatButton());
        this.add(getAdvanceButton());
        
        this.add(new JTextField("Gun Controls"));
        this.add(getDecreasePowerButton());
        this.add(getAttackButton());
        this.add(getIncreasePowerButton());
        
        this.add(new JTextField("Fun"));
        this.add(new JPanel());
        this.add(getTauntButton());
        this.add(new JPanel());
    }
        
    public JButton getPauseButton() {
    	if (pauseButton == null) {
    		pauseButton = new JButton("PAUSE");
    		pauseButton.setBackground(new Color(255, 0, 0));
    		setButtonDefaults(pauseButton);
    	}
    	return pauseButton;
    }
    
    public JButton getAdvanceButton() {
    	if (advanceButton == null) {
    		advanceButton = new JButton("ADVANCE");
    		setButtonDefaults(advanceButton);
    		advanceButton.setBackground(new Color(0, 255, 0));
    	}
    	return advanceButton;
    }
    
    public JButton getRetreatButton() {
    	if (retreatButton == null) {
    		retreatButton = new JButton("RETREAT");
    		retreatButton.setBackground(new Color(255, 255, 0));
    		setButtonDefaults(retreatButton);
    	}
    	return retreatButton;
    }
    
    public JButton getAttackButton() {
    	if (attackButton == null) {
    		attackButton = new JButton("ATTACK");
    		attackButton.setBackground(new Color(0, 0, 255));
    		setButtonDefaults(attackButton);
    	}
    	return attackButton;
    }
    
    public JButton getIncreasePowerButton() {
    	if (increasePowerButton == null) {
    		increasePowerButton = new JButton();
    		//Using HTML because line breaks hate me
    		increasePowerButton.setText("<html><center>INCREASE<br>POWER"
    		+ "</center></html>");
    		increasePowerButton.setBackground(new Color(0, 0, 255));
    		setButtonDefaults(increasePowerButton);
    	}
    	return increasePowerButton;
    }
    
    public JButton getDecreasePowerButton() {
    	if (decreasePowerButton == null) {
    		decreasePowerButton = new JButton();
    		//Using HTML because line breaks hate me
    		decreasePowerButton.setText("<html><center>DECREASE<br>POWER"
    	    		+ "</center></html>");
    		decreasePowerButton.setBackground(new Color(0, 0, 255));
    		setButtonDefaults(decreasePowerButton);
    	}
    	return decreasePowerButton;
    }
    
    public JButton getTauntButton() {
    	if (tauntButton == null) {
    		tauntButton = new JButton("TAUNT");
    		tauntButton.setBackground(new Color(255, 0, 255));
    		setButtonDefaults(tauntButton);
    	}
    	return tauntButton;
    }
    
    private void setButtonDefaults(JButton button) {
    	//button.setPreferredSize(new Dimension(80, 40));
    }
    
}
