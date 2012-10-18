package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
		private JTextField cellWidth, cellHeight, wallWidth, wallHeight;
		public MazeModeRulesPanel() {
			super();
			
			add(new JLabel("Width of Passages:"), BorderLayout.NORTH);
			cellWidth = new JTextField("64", 5);
			add(cellWidth);
			

			add(new JLabel("Height of Passages:"), BorderLayout.SOUTH);
			cellHeight = new JTextField("64", 5);
			add(cellHeight);

			add(new JLabel("Width of Walls:"), BorderLayout.NORTH);
			wallWidth = new JTextField("10", 5);
			add(wallWidth);
			
			add(new JLabel("Height of Walls:"), BorderLayout.NORTH);
			wallHeight = new JTextField("10", 5);
			add(wallHeight);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("cellWidth", cellWidth.getText());
			values.put("cellHeight", cellHeight.getText());
			values.put("wallWidth", wallWidth.getText());
			values.put("wallHeight", wallHeight.getText());
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
