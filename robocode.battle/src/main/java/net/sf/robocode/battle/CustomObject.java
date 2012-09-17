package net.sf.robocode.battle;

import java.awt.geom.AffineTransform;

import robocode.util.Utils;

public class CustomObject implements ICustomObject {
	private AffineTransform at;
	private double rotation;
	private String name;
	private String filename;

	public CustomObject(String name, String filename) {
		this.name = name;
		this.filename = filename;
	}

	public CustomObject(String name, String filename, double x, double y) {
		this.name = name;
		this.filename = filename;
		setTranslate(x, y);
	}

	public CustomObject(String name, String filename, AffineTransform at) {
		this.name = name;
		this.filename = filename;
		setTranslate(at);
	}
	
	public String getName() {
		return name;
	}
	
	public String getFilename() {
		return filename;
	}

	@Override
	public void setTranslate(double x, double y) {
		at = AffineTransform.getTranslateInstance(x, y);
	}

	@Override
	public void setTranslate(AffineTransform at) {
		this.at.setTransform(AffineTransform.getTranslateInstance(
				at.getTranslateX(), at.getTranslateY()));

	}

	@Override
	public void setRotation(double degree) {
		rotation = degree;
		at.rotate(Math.toRadians(degree));
	}

	@Override
	public double getTranslateX() {
		return at.getTranslateX();
	}
	
	@Override
	public double getTranslateY() {
		return at.getTranslateY();
	}

	@Override
	public double getRotation() {
		return rotation;
	}
}
