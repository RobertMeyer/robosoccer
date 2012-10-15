package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Mode Class used for Elimination Mode. 
 * 
 * @author Team - MCJJ
 * @author Jake Ching Leong Ong
 */

public class EliminationMode extends ClassicMode {
	
	private JTextField setTime; 
	private setEliminationPanel modePanel;
	private final String title = "Elimination Mode";
    private final String description = "This mode eliminates the weakest robot every few seconds (Default - 5 Seconds)";

    
    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
	private class setEliminationPanel extends JPanel {
		public setEliminationPanel() {
			setTime = new JTextField(2);
			add(new JLabel("Input Elimination Time (in seconds):"), BorderLayout.NORTH);
			add(setTime);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			Pattern pattern = Pattern.compile("^\\d+$");
			Matcher match = pattern.matcher(setTime.getText());
			boolean isInt = match.matches();
			//If user input is not an integer, put timer to 5 seconds.
			if (isInt) {
				values.put("eliminate", setTime.getText());
			} else {
				values.put("eliminate", "5");
			}
			return values;
		}
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return modePanel.getValues();
	}
	
    public JPanel getRulesPanel(){
    	if(modePanel == null){
    		modePanel = new setEliminationPanel();
    	}
    	return modePanel;
    }
}
