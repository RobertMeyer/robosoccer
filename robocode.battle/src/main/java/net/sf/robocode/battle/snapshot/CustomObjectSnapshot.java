package net.sf.robocode.battle.snapshot;

import java.awt.geom.AffineTransform;

import robocode.control.snapshot.ICustomObjectSnapshot;

import net.sf.robocode.battle.CustomObject;

/**
 * This implements the ICustomObjectSnapshot interface.
 * 
 * This is used to parse object information onto the renderer.
 * 
 * @author Benjamin Evenson @ Team-G1
 *
 */
public final class CustomObjectSnapshot implements ICustomObjectSnapshot {
	private AffineTransform at;
	private String name;
	private String filename;
	private boolean hide;
	private float alpha;

	/**
	 * Default Constructor
	 * 
	 * do nothing?
	 */
	public CustomObjectSnapshot() {

	}

	/**
	 * Construct a CustomObjectSnapshot with given parameters.
	 * 
	 * @param co - CustomObject used to create a snapshot
	 */
	public CustomObjectSnapshot(CustomObject co) {
		this.at = new AffineTransform();
		this.at = co.getAffineTransform();
		this.name = co.getName();
		this.filename = co.getFilename();
		this.hide = co.getHide();
		this.alpha = co.getAlpha();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getHide() {
		return this.hide;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFilename() {
		return this.filename;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AffineTransform getAffineTransform() {
		return this.at;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAlpha() {
		return this.alpha;
	}

}
