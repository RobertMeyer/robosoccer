package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimerMode extends ClassicMode {
	
	private JTextField setTime; 
	private setTimePanel modePanel;
	private final String title = "Timer Mode";
    private final String description = "This mode ends each match with the given time";

    
    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    public JPanel getRulesPanel(){
    	if(modePanel == null){
    		modePanel = new setTimePanel();
    	}
    	return modePanel;
    }
    
	private class setTimePanel extends JPanel {
		public setTimePanel() {
			setTime = new JTextField(2);
			add(new JLabel("Input Time (in seconds):"), BorderLayout.NORTH);
			add(setTime);
		}
	}
	
	public int getTime() {
		int time = Integer.parseInt(setTime.getText());
		return time;
	}
	
	
	
}
