package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {
	
	private final String description = "Original robocode mode.";
	
	/**
	 * {@inheritDoc}
	 */
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
	}
	
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
		return description;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> getItemIds() {
		return new ArrayList<Integer>();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setItemIds() {
		return;
	}
}
