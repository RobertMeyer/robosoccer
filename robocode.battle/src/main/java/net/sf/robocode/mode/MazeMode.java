package net.sf.robocode.mode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.maze.Maze;
import net.sf.robocode.battle.peer.ObstaclePeer;
import robocode.BattleRules;

/**
 * Creates a game mode with obstacles placed on the battlefield
 * 
 * @author Team a-MAZE-ing
 *
 */
public class MazeMode extends ClassicMode {
	
	private MazeModeRulesPanel rulesPanel;
	
    @Override
    public String toString() {
        return "Maze Mode";
    }

    @Override
    public String getDescription() {
        return "A mode with a maze that robots have to navigate.";
    }
    
    @Override
    public int setCellWidth(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("cellWidth"));
	}
    
    @Override
    public int setCellHeight(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("cellHeight"));
	}
    
    @Override
    public int setWallWidth(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("wallWidth"));
	}
    
    @Override
    public int setWallHeight(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("wallHeight"));
	}
    
    @Override
    public boolean dWallSetting(BattleRules rules) {
    	return (Boolean) rules.getModeRules().get("dWall");
    }
    
    public JPanel getRulesPanel(){
		if(rulesPanel == null){
			rulesPanel = new MazeModeRulesPanel();
		}
		return rulesPanel;
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return rulesPanel.getValues();
	}
	
	@SuppressWarnings("serial")
	private class MazeModeRulesPanel extends JPanel {
		private JTextField minPassW, minPassH, minWallW, minWallH;
		private JCheckBox dWall;
		public MazeModeRulesPanel() {
			super();
			GridBagConstraints c = new GridBagConstraints();
	        setLayout(new GridBagLayout());
	        
	        JLabel minPassWLbl = new JLabel("Minimum Width of Passages:");
	        minPassW = new JTextField("50", 5);
	        JLabel minPassHLbl = new JLabel("Minimum Height of Passages:");
	        minPassH = new JTextField("50", 5);
	        JLabel minWallWLbl = new JLabel("Minimum Width of Walls:");
	        minWallW = new JTextField("1", 5);
	        JLabel minWallHLbl = new JLabel("Minimum Height of Walls:");
	        minWallH = new JTextField("1", 5);
	        JLabel dWallLbl = new JLabel("Enable Destructable Walls:");
	        dWall = new JCheckBox();
	        dWall.setSelected(false);
	        
	        c.gridx = 0;
	        c.gridy = 0;
	        c.weightx = 0.1;
	        c.anchor = GridBagConstraints.LINE_START;
	        add(minPassWLbl, c);
	        c.gridy = 1;
	        add(minPassHLbl, c);
	        c.gridy = 2;
	        add(minWallWLbl, c);
	        c.gridy = 3;
	        add(minWallHLbl, c);
	        c.gridy = 4;
	        add(dWallLbl, c);
	        
	        c.gridy = 0;
	        c.anchor = GridBagConstraints.LINE_END;
	        add(minPassW, c);
	        c.gridy = 1;
	        add(minPassH, c);
	        c.gridy = 2;
	        add(minWallW, c);
	        c.gridy = 3;
	        add(minWallH, c);
	        c.gridy = 4;
	        add(dWall, c);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("cellWidth", minPassW.getText());
			values.put("cellHeight", minPassH.getText());
			values.put("wallWidth", minWallW.getText());
			values.put("wallHeight", minWallH.getText());
			values.put("dWall", dWall.isSelected());
			return values;
		}
	}
	
	public static List<ObstaclePeer> generateMaze(BattleProperties bp, BattleRules battleRules, Battle battle,
			int cellWidth, int cellHeight, int wallWidth, int wallHeight) {
		List<ObstaclePeer> obstacles = new ArrayList<ObstaclePeer>();
		Maze maze = new Maze(bp, cellWidth, cellHeight, wallWidth, wallHeight);
		obstacles = maze.wallList(battleRules, battle);
		return obstacles;
	}
}
