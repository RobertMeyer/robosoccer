package net.sf.robocode.battle;

import java.awt.geom.AffineTransform;

public interface ICustomObject {
	
	public void setTranslate(double x, double y);
	
	public AffineTransform getTranslate();
	
	public void setRotation(double degree);
	
	public double getRotationDegree();
	
	public AffineTransform getRotation();
	
	public void setHide();
	
	public boolean getHide();
	
	public void setAlpha(float value);
	
	public float getAlpha();
	
	public void setScale(double x, double y);
	
	public AffineTransform getScale();
	
	public void setShear(double x, double y);
	
	public AffineTransform getShear();
	
	public AffineTransform getAffineTransform();
}
