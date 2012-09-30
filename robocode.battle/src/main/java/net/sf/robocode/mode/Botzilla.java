package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Botzilla extends ClassicMode {
	
	private JTextField setBotzillaSpawn; 
	private setBotzillaPanel modePanel;
	
	@Override
	public String toString() {
		return "Botzilla Mode";
	}
	
	@Override
	public String getDescription() {
		return "A mode that brings in an undefeatable enemy to speed up the finish";
	}

	private class setBotzillaPanel extends JPanel {
		public setBotzillaPanel() {
			setBotzillaSpawn = new JTextField(2);
			add(new JLabel("Turn to spawn Botzilla(Default: 40) :"), BorderLayout.NORTH);
			add(setBotzillaSpawn);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			Pattern pattern = Pattern.compile("^\\d+$");
			Matcher match = pattern.matcher(setBotzillaSpawn.getText());
			boolean isInt = match.matches();
			if (isInt) {
				values.put("botzillaSpawn", setBotzillaSpawn.getText());
			}else{
				values.put("botzillaSpawn", "40");
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