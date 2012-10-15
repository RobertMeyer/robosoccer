package net.sf.robocode.mode;

import java.util.Hashtable;
import javax.swing.JPanel;
import java.util.HashMap;

import net.sf.robocode.battle.BattleResultsTableModel;

import robocode.BattleResults;
import robocode.BattleRules;

/**
 * This interface defines all the methods in every Mode in Robocode.
 */
public interface IMode {

	/**
	 * Modifies the velocity of Robots in a battle
	 * @param velocityIncrement
	 * @return a modified Velocity
	 */
	public double modifyVelocity(double velocityIncrement, BattleRules rules);
	
	/**
	 * Returns a string representation of the current Mode
	 * @return string representation of a Mode
	 */
	public String toString();
	
	/**
	 * Returns a string representing the Mode's description.
	 * @return String description
	 */
	public String getDescription();
	
	/**
	 * Add's mode specific robots to the list of selected robots.
	 * 
	 * @param current list of selected robots in the form: 
	 * "robots.myRobot*,robots.yourRobot*"...
	 * @return list of selected robots plus any modeSpecific robots appended.
	 * The rules panel to be displayed in the Battle dialog / Modes tab.
	 * @return JPanel the panel for this mode's rules
	 */
	public JPanel getRulesPanel();
	
	/**
	 * A dictionary of mode's rules as key= > value pairs.
	 * @return Hashtable the mode's rules
	 */
	public Hashtable<String, Object> getRulesPanelValues();
	
	/**
	 * Returns true if robots are to respawn instantly on death
	 * @return boolean representing respawns on or off
	 */
	public boolean respawnsOn();
	
	/**
	 * Returns the turn number each round will end on.  Automatically enabled
	 * when respawns are.  Default set to 9000 turns (5 mins at 30 TPS)
	 * Note: You do not have to implement this if respawns are on unless
	 * you want to change the turn limit.
	 * @return the turn number that each round will end on if respawns are
	 * enabled
	 */
	public int turnLimit();

	/**
	 * Create items for the specific mode
	 */
	public void setItems();

 	/** Add's mode specific robots to the list of selected robots.
	 * 
	 * @param current list of selected robots in the form: 
	 * "robots.myRobot*,robots.yourRobot*"...
	 * @return list of selected robots plus any modeSpecific robots appended.
	 */
	public String addModeRobots(String selectedRobots);

	/**
	 * Initialises the GuiOptions object with the visibility options
	 * applicable to this mode.
	 */
	public void setGuiOptions();
	
	/**
	 * Getter method for the GuiOptions object associated with this
	 * mode.
	 * @return GuiOptions object associated with this mode.
	 */
	public GuiOptions getGuiOptions();

	/**
	 * Get the mode's custom results table format
	 * @return Table model of the mode's results
	 */
	public BattleResultsTableModel getCustomResultsTable();
	
	/**
	 * Set the Mode's custom results table format
	 */
	public void setCustomResultsTable();
	
	public BattleResults[] getFinalResults();
	
	/**
	 * For disabling the dialogue that says "You have only selected one robot"
	 * @return
	 */
	public boolean allowsOneRobot();
}
