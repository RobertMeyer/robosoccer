package net.sf.robocode.mode;

import java.util.Hashtable;
import java.util.List;
import javax.swing.JPanel;
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
	 * Increments the score specific to the different modes
	 */
	public void scorePoints();
	
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
}
