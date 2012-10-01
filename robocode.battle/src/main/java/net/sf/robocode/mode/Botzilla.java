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
	private JTextField setBotzillaModifier;
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
			add(new JLabel("Turn to spawn Botzilla (Default: 750) :"), BorderLayout.NORTH);
			add(setBotzillaSpawn);
			setBotzillaModifier = new JTextField(2);
			add(new JLabel("Amount of turns to wait multiplied by robot count:"), BorderLayout.SOUTH);
			add(setBotzillaModifier);
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