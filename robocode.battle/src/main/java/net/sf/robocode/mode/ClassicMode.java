package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import javax.swing.JPanel;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.ItemDrop;
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
	 * Returns a list of ItemDrop's to 
	 * spawn in the beginning of the round
	 * @return List of items
	 */
	public List<? extends ItemDrop> getItems() {
		return new ArrayList<ItemDrop>();
	}
	
	/**
	 * Create a list of ItemDrop's to
	 * spawn in the beginning of the round
	 * @param battle The Battle to add the items to
	 */
	public void setItems(Battle battle) {
		/* No items needed for Classic Mode */
	}

	@Override
	public void scorePoints() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean respawnsOn() {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int turnLimit() {
		return 5*30*60; // 9000 turns is the default
	}
}
