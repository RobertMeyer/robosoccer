package net.sf.robocode.battle.snapshot;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import robocode.control.snapshot.IRenderableSnapshot;
import robocode.control.snapshot.RenderableType;

import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.RenderImage;
import net.sf.robocode.battle.RenderString;

/**
 * This implements the ICustomObjectSnapshot interface.
 * 
 * This is used to parse object information onto the renderer.
 * 
 * @author Benjamin Evenson @ Team-G1
 *
 */
public final class RenderableSnapshot implements IRenderableSnapshot {
	private AffineTransform at;
	private String name;
	private String filename;
	private boolean hide;
	private float alpha;
	private RenderableType type;
	private Color colour;

	/**
	 * Default Constructor
	 * 
	 * do nothing?
	 */
	public RenderableSnapshot() {

	}

	/**
	 * Construct a CustomObjectSnapshot with given parameters.
	 * 
	 * @param co - CustomObject used to create a snapshot
	 */
	public RenderableSnapshot(IRenderable co) {
		this.at = new AffineTransform();
		this.at = co.getAffineTransform();
		this.name = co.getName();
		this.hide = co.getHide();
		this.alpha = co.getAlpha();
		this.type = co.getType();
		this.colour = co.getColor();
		if (this.type == RenderableType.SPRITE)
			this.filename = ((RenderImage)co).getFilename();
		else
			this.filename = ((RenderString)co).getName();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RenderableType getType() {
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColour() {
		return this.colour;
	}

}
