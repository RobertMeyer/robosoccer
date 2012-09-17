package net.sf.robocode.battle;

import java.awt.geom.AffineTransform;

public class CustomObject implements ICustomObject {
	private AffineTransform at;
	private double rotation;
	private float alpha;
	private String name;
	private String filename;
	private boolean hide;

	public CustomObject(String name, String filename) {
		this.at = new AffineTransform();
		this.name = name;
		this.filename = filename;
		this.hide = false;
		this.alpha = 1.0f;
		this.rotation = 0.0f;
	}

	public CustomObject(String name, String filename, double x, double y) {
		this.name = name;
		this.filename = filename;
		this.at = new AffineTransform();
		setTranslate(x, y);
		this.hide = false;
		this.alpha = 1.0f;
		this.rotation = 0.0f;
	}

	public CustomObject(String name, String filename, AffineTransform at) {
		this.name = name;
		this.filename = filename;
		this.at = at;
		this.alpha = 1.0f;
		this.hide = false;
		this.rotation = 0.0f;
	}

	public String getName() {
		return name;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public void setTranslate(double x, double y) {
		this.at.translate(x, y);
	}

	@Override
	public void setRotation(double degree) {
		this.rotation = degree;
		this.at.rotate(Math.toRadians(degree));
	}

	@Override
	public AffineTransform getTranslate() {
		return AffineTransform.getTranslateInstance(this.at.getTranslateX(),
				this.at.getTranslateY());
	}

	@Override
	public AffineTransform getRotation() {
		return AffineTransform.getRotateInstance(Math.toRadians(this.rotation));
	}
	
	public double getRotationDegree() {
		return this.rotation;
	}

	@Override
	public void setHide() {
		this.hide ^= true;
	}

	@Override
	public boolean getHide() {
		return this.hide;
	}

	@Override
	public void setAlpha(float value) {
		this.alpha = value;
	}

	@Override
	public float getAlpha() {
		return this.alpha;
	}

	@Override
	public void setScale(double x, double y) {
		this.at.scale(x , y);
	}

	@Override
	public AffineTransform getScale() {
		return AffineTransform.getScaleInstance(this.at.getScaleX(),
				this.at.getScaleY());
	}

	@Override
	public void setShear(double x, double y) {
		this.at.shear(x, y);

	}

	@Override
	public AffineTransform getShear() {
		return AffineTransform.getShearInstance(this.at.getShearX(),
				this.at.getShearY());
	}

	@Override
	public AffineTransform getAffineTransform() {
		return at;
	}
}
