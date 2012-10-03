package net.sf.robocode.battle;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import robocode.control.snapshot.RenderableType;

public class RenderString implements IRenderable {
	private AffineTransform at;
	private double rotation;
	private float alpha;
	private String name;
	private String text;
	private boolean hide;
	private RenderableType type;
	private Color colour;
	
	/**
	 * Constructs a new RenderString with given parameters.
	 * 
	 * @param name - Key used for hashmap(Must be Unique).
	 * @param text - text that will be rendered.
	 */
	public RenderString(String name, String text) {
		this.at = new AffineTransform();
		this.name = name;
		this.text = text;
		this.hide = false;
		this.alpha = 1.0f;
		this.rotation = 0.0f;
		this.colour = Color.WHITE;
		this.type = RenderableType.SPRITE_STRING;
	}
	
	/**
	 * Constructs a new RenderString with given parameters.
	 * 
	 * @param name - Key used for hashmap(Must be Unique).
	 * @param text - text that will be rendered.
	 * @param x - x position.
	 * @param y - y position.
	 */
	public RenderString(String name, String text, double x, double y) {
		this.at = new AffineTransform();
		setTranslate(x, y);
		this.name = name;
		this.text = text;
		this.hide = false;
		this.alpha = 1.0f;
		this.rotation = 0.0f;
		this.colour = Color.WHITE;
		this.type = RenderableType.SPRITE_STRING;
	}
	
	/**
	 * Gets the key of object, used in HashMap.
	 * 
	 * @return a string of object key.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the file path of object(image)
	 * 
	 * @return a string to file.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Set text to be rendered to screen.
	 * 
	 * @param text - a string of text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTranslate(double x, double y) {
		this.at.setToTranslation(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRotation(double degree) {
		this.rotation = degree;
		this.at.rotate(Math.toRadians(degree));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AffineTransform getTranslate() {
		return AffineTransform.getTranslateInstance(this.at.getTranslateX(),
				this.at.getTranslateY());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getTranslateX() {
		return this.at.getTranslateX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getTranslateY() {
		return this.at.getTranslateY();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AffineTransform getRotation() {
		return AffineTransform.getRotateInstance(Math.toRadians(this.rotation));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getRotationDegree() {
		return this.rotation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getRotationRadian() {
		return Math.toRadians(this.rotation);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleHide() {
		this.hide ^= true;
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
	public void setAlpha(float value) {
		this.alpha = value;
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
	public void setScale(double x, double y) {
		this.at.scale(x , y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AffineTransform getScale() {
		return AffineTransform.getScaleInstance(this.at.getScaleX(),
				this.at.getScaleY());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setShear(double x, double y) {
		this.at.shear(x, y);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AffineTransform getShear() {
		return AffineTransform.getShearInstance(this.at.getShearX(),
				this.at.getShearY());
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
	public String toString() {
		return  "Key: " + this.name + " " +
				"Text: " + this.text + " " +
				"Position: " + getTranslate().getTranslateX() + " "
				+ getTranslate().getTranslateY();
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
	public void setColour(Color col) {
		this.colour = col;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return this.colour;
	}
}
