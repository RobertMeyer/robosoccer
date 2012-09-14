package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import javax.swing.JPanel;
import robocode.BattleRules;

/**
 *
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Classic Mode";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return "Original robocode mode.";
	}
	
	public JPanel getRulesPanel() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Hashtable<String, Object> getRulesPanelValues() {
		return null;
	}
	
	// ----- Mode-specific methods below this line ------
	
	/**
	 * {@inheritDoc}
	 */
	public double modifyVelocity(double velocityIncrement, BattleRules rules) {
		return modifyVelocity(velocityIncrement);
	}
	
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<String> getItems() {
		return new ArrayList<String>();
	}
	
	@Override
	public void setItems() {
		/* No items needed for Classic Mode */
	}

	@Override
	public void scorePoints() {
		// TODO Auto-generated method stub
		
	}
	
	public void spawnZombie(int turn) {
		//No need to spawn zombies in Classic Mode
	}
}
