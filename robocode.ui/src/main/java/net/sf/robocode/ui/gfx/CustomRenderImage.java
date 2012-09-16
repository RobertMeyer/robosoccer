package net.sf.robocode.ui.gfx;

import java.awt.Image;
import java.awt.geom.AffineTransform;

public class CustomRenderImage extends RenderImage {
	private AffineTransform at;
	
	public CustomRenderImage(RenderImage image, double x, double y) {
		super(image);
		this.at = AffineTransform.getTranslateInstance(x, y);
	}
	
	public void setPosition(double x, double y) {
		this.at = AffineTransform.getTranslateInstance(x, y);
	}
	
	public AffineTransform getPosition() {
		return this.at;
	}
	
	public void setRoation(double rotate) {
		this.at.setToRotation(rotate);
	}
	
	public double getX() {
		return at.getTranslateX();
	}
	
	public double getY() {
		return at.getTranslateY();
	}

}
