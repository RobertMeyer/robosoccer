/******************************************************************************
 * Copyright (c) 2012 Team The Fightin' Mongooses
 *
 * Contributors:
 *  Paul Wade
 *  Chris Irving
 *  Jesse Claven
 *****************************************************************************/

package net.sf.robocode.mode;

import java.awt.GridLayout;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Implementation of a mode with conditions for spawning Botzilla.
 *
 * @author The Fightin' Mongooses
 */
public class BotzillaMode extends ClassicMode {
	
	private JTextField setBotzillaSpawn; 
	private JTextField setBotzillaModifier;
	private setBotzillaPanel modePanel;
	
	@Override
	public String toString() {
		return "Botzilla Mode";
	}
	
	@Override
	public String getDescription() {
		return "A mode that brings in an undefeatable enemy to speed up the finish.";
	}

  /**
   * A selection pane for the user to decide after how many turns Botzilla
   * spawn whether an exact round number or a multiplier of how many robots
   * are in the battle.
   *
   * @author  The Fightin' Mongooses
   */
	private class setBotzillaPanel extends JPanel {
		
		public setBotzillaPanel() {
			JPanel rowOne = new JPanel();
			JPanel rowTwo = new JPanel();
			
			setLayout(new GridLayout(2, 1, 0, 5));
			this.add(rowOne);
			this.add(rowTwo);
						
			setBotzillaSpawn = new JTextField(5);
			rowOne.add(new JLabel("Turn to spawn Botzilla (Default: 750) :"));
			rowOne.add(setBotzillaSpawn);
			
			setBotzillaModifier = new JTextField(5);
			rowTwo.add(new JLabel("Amount of turns to wait multiplied by robot count:"));
			rowTwo.add(setBotzillaModifier);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			Pattern pattern = Pattern.compile("^\\d+$");
			Matcher matchSpawn = pattern.matcher(setBotzillaSpawn.getText());
			boolean isInt = matchSpawn.matches();
			if (isInt) {
				values.put("botzillaSpawn", setBotzillaSpawn.getText());
			}else{
				values.put("botzillaSpawn", "0");
			}
			Matcher matchModifier = pattern.matcher(setBotzillaModifier.getText());
			isInt = matchModifier.matches();
			if (isInt) {
				values.put("botzillaModifier", setBotzillaModifier.getText());
			}else{
				values.put("botzillaModifier", "0");
			}
			return values;
		}
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return modePanel.getValues();
	}
	
    public JPanel getRulesPanel(){
    	if(modePanel == null){
    		modePanel = new setBotzillaPanel();
    	}
    	return modePanel;
    }
}
