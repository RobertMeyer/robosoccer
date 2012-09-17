package net.sf.robocode.battle;

import java.awt.geom.AffineTransform;

public interface ICustomObject {
	
	public void setTranslate(double x, double y);
	
	public void setTranslate(AffineTransform at);
	
	public double getTranslateX();
	
	public double getTranslateY();
	
	public void setRotation(double degree);
	
	public double getRotation();
}
