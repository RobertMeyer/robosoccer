package net.sf.robocode.battle;

import java.awt.geom.AffineTransform;

/**
 * This implements the ICustomObject interface, which represents
 * a custom object within robocode.
 * 
 * @author Benjamin Evenson @ Team-G1
 *
 */
public class CustomObject implements ICustomObject {
	private AffineTransform at;
	private double rotation;
	private float alpha;
	private String name;
	private String filename;
	private boolean hide;

	/**
	 * Constructs a new CustomObject with given parameters.
	 * 
	 * @param name - Key used for hashmap(Must be Unique).
	 * @param filename - Path to image file.
	 */
	public CustomObject(String name, String filename) {
		this.at = new AffineTransform();
		this.name = name;
		this.filename = filename;
		this.hide = false;
		this.alpha = 1.0f;
		this.rotation = 0.0f;
	}

	/**
	 * Constructs a new CustomObject with given parameters.
	 * 
	 * @param name - Key used for hashmap(Must be Unique).
	 * @param filename - Path to image file.
	 * @param x - x position.
	 * @param y - y position.
	 */
	public CustomObject(String name, String filename, double x, double y) {
		this.name = name;
		this.filename = filename;
		this.at = new AffineTransform();
		setTranslate(x, y);
		this.hide = false;
		this.alpha = 1.0f;
		this.rotation = 0.0f;
	}

	/**
	 * Constructs a new CustomObject with given parameters.
	 * 
	 * @param name - Key used for hashmap(Must be Unique).
	 * @param filename - Path to image file.
	 * @param at - AffineTransform for object.
	 */
	public CustomObject(String name, String filename, AffineTransform at) {
		this.name = name;
		this.filename = filename;
		this.at = at;
		this.alpha = 1.0f;
		this.hide = false;
		this.rotation = 0.0f;
	}

	/**
	 * Gets the key of object, used in HashMap.
	 * 
	 * @return a string of object key.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the file path of object(image)
	 * 
	 * @return a string to file.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTranslate(double x, double y) {
		this.at.translate(x, y);
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
		return at;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return  "Key: " + this.name + " " +
				"File Path: " + this.filename + " " +
				"Position: " + getTranslate().getTranslateX() + " "
				+ getTranslate().getTranslateY();
	}
}
