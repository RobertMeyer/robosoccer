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
        this.add(getPauseButton());
        this.add(getAdvanceButton());
        this.add(getRetreatButton());
        this.add(getAttackButton());
        this.add(getIncreasePowerButton());
        this.add(getDecreasePowerButton());
        this.add(getTauntButton());        
    }
        
    public JButton getPauseButton() {
    	if (pauseButton == null) {
    		pauseButton = new JButton("PAUSE");
    		//set button properties here
    	}
    	return pauseButton;
    }
    
    public JButton getAdvanceButton() {
    	if (advanceButton == null) {
    		advanceButton = new JButton("ADVANCE");
    		//set button properties here
    	}
    	return advanceButton;
    }
    
    public JButton getRetreatButton() {
    	if (retreatButton == null) {
    		retreatButton = new JButton("RETREAT");
    		//set button properties here
    	}
    	return retreatButton;
    }
    
    public JButton getAttackButton() {
    	if (attackButton == null) {
    		attackButton = new JButton("ATTACK");
    		//set button properties here
    	}
    	return attackButton;
    }
    
    public JButton getIncreasePowerButton() {
    	if (increasePowerButton == null) {
    		increasePowerButton = new JButton("INCREASE POWER");
    		//set button properties here
    	}
    	return increasePowerButton;
    }
    
    public JButton getDecreasePowerButton() {
    	if (decreasePowerButton == null) {
    		decreasePowerButton = new JButton("DECREASE POWER");
    		//set button properties here
    	}
    	return decreasePowerButton;
    }
    
    public JButton getTauntButton() {
    	if (tauntButton == null) {
    		tauntButton = new JButton("TAUNT");
    		//set button properties here
    	}
    	return tauntButton;
    }
    
}
