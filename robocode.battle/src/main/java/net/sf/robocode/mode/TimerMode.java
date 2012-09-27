package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimerMode extends ClassicMode {
	
	private JTextField setTime; 
	private setTimePanel modePanel;
	private final String title = "Timer Mode";
    private final String description = "This mode ends each match with the given time (Default - 30 Seconds)";

    
    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
	private class setTimePanel extends JPanel {
		public setTimePanel() {
			setTime = new JTextField(2);
			add(new JLabel("Input Time (in seconds):"), BorderLayout.NORTH);
			add(setTime);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher match = pattern.matcher(setTime.getText());
			boolean isInt = match.matches();
			if (isInt) {
				values.put("timer", setTime.getText());
			}else{
				values.put("timer", "30");
			}
			return values;
		}
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return modePanel.getValues();
	}
	
    public JPanel getRulesPanel(){
    	if(modePanel == null){
    		modePanel = new setTimePanel();
    	}
    	return modePanel;
    }
}
