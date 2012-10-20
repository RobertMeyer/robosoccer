package net.sf.robocode.battle.snapshot;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import robocode.control.snapshot.IRenderableSnapshot;
import robocode.control.snapshot.RenderableType;

import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.RenderObject;
import net.sf.robocode.battle.RenderString;
import net.sf.robocode.battle.RenderAnim;

/**
 * This implements the IRenderableSnapshot interface.
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
	private double rotation;
	private RenderableType type;
	private Color colour;
	private int spritewidth = 0;
	private int spriteheight = 0;
	private int rows = 0;
	private int cols = 0;
	private int loops = 0;

	/**
	 * Default Constructor
	 * 
	 * do nothing?
	 */
	public RenderableSnapshot() {

	}

	/**
	 * Construct a RenderableSnapshot with given parameters.
	 * 
	 * @param co - CustomObject used to create a snapshot
	 */
	public RenderableSnapshot(IRenderable co) {
		this.at = new AffineTransform();
		this.at = co.getAffineTransform();
		this.name = co.getName();
		this.hide = co.getHide();
		this.alpha = co.getAlpha();
		this.rotation = co.getRotationRadian();
		this.type = co.getType();
		this.colour = co.getColour();
		if (this.type == RenderableType.SPRITE) {
			this.filename = ((RenderObject)co).getFilename();
		} else if (this.type == RenderableType.SPRITE_ANIMATION) {
			RenderAnim c = (RenderAnim)co;
			this.filename = c.getFilename();
			this.spritewidth = c.getSpriteWidth();
			this.spriteheight = c.getSpriteHeight();
			this.rows = c.getRows();
			this.cols = c.getCols();
			this.loops = c.getLoops();
		} else if (this.type == RenderableType.SPRITE_STRING) {
			this.filename = ((RenderString)co).getText();
		}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getX() {
		return this.at.getTranslateX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getY() {
		return this.at.getTranslateY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getRotation() {
		return this.rotation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getShearX() {
		return this.at.getShearX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getShearY() {
		return this.at.getShearY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getScaleX() {
		return this.at.getScaleX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getScaleY() {
		return this.at.getScaleY();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSpriteWidth() {
		return spritewidth;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSpriteHeight() {
		return spriteheight;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRows() {
		return rows;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCols() {
		return cols;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLoops() {
		return loops;
	}
}
