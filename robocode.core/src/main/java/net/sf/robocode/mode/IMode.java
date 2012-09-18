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
	 * Returns a list of String of the item to 
	 * spawn in the beginning of the round
	 * @return list of items
	 */
	public List<String> getItems();
	
	/**
	 * Create a list of Strings representing the items to
	 * spawn in the beginning of the round
	 */
	public void setItems();
	
	/**
	 * Increments the score specific to the different modes
	 */
	public void scorePoints();

 	/* Add's mode specific robots to the list of selected robots.
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
}
