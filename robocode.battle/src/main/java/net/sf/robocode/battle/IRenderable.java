package net.sf.robocode.battle;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import robocode.control.snapshot.RenderableType;

/**
 * This interface defines all the methods in a CustomObject.
 * 
 * @author Benjamin Evenson @ Team-G1
 */
public interface IRenderable {
	/**
	 * Modifies the position of the object.
	 * 
	 * @param x - X position on screen.
	 * @param y - Y position on screen.
	 */
	public void setTranslate(double x, double y);
	
	/**
	 * Returns the AffineTransform of the translation.
	 * 
	 * To get X,Y uses the getTranslateX(), getTranslateY()
	 * on the AffineTransform.
	 * 
	 * @return AffineTransform of the objects translation.
	 */
	public AffineTransform getTranslate();
	
	/**
	 * Modifies the rotation of the object.
	 * 
	 * In Degrees.
	 * 
	 * @param degree - Set rotation to given degree.
	 */
	public void setRotation(double degree);
	
	/**
	 * Returns the current rotation in degrees.
	 * 
	 * @return a degree representation of the angle.
	 */
	public double getRotationDegree();
	
	/**
	 * Returns the current AffineTransform of rotation.
	 * 
	 * @return a AffineTransform of current rotation.
	 */
	public AffineTransform getRotation();
	
	/**
	 * Toggles the object to render or not.
	 */
	public void toggleHide();
	
	/**
	 * Returns the current hide state.
	 * 
	 * @return a boolean of current state.
	 */
	public boolean getHide();
	
	/**
	 * Set the alpha blending(fading) of the object.
	 * 
	 * example setAlpha(0.1f) would be nearly transparent.
	 * 		   setAlpha(1.0f) is no transparency.
	 * 
	 * @param value - level of blending
	 */
	public void setAlpha(float value);
	
	/**
	 * Returns the current blend level.
	 * 
	 * @return a float of current alpha level.
	 */
	public float getAlpha();
	
	/**
	 * Sets the scale of object.
	 * 
	 * @param x - Scale on x axis.
	 * @param y - Scale on y axis.
	 */
	public void setScale(double x, double y);
	
	/**
	 * Returns the current scale in a AffineTransform.
	 * 
	 * @return a AffineTransform representation of the scale.
	 */
	public AffineTransform getScale();
	
	/**
	 * Set the object to shear.
	 * 
	 * @param x - Shear on x axis.
	 * @param y - Shear on y axis.
	 */
	public void setShear(double x, double y);
	
	/**
	 * Returns the current shear in a AffineTransform.
	 * 
	 * @return a AffineTransform with current shear.
	 */
	public AffineTransform getShear();
	
	/**
	 * Returns the current AffineTransform of object.
	 * 
	 * @return a AffineTransform of object.
	 */
	public AffineTransform getAffineTransform();
	
	/**
	 * Returns the type of renderable.
	 * 
	 * @return a RenderType enum of type of renderable.
	 */
	public RenderableType getType();
	
	/**
	 * Returns the name of the object.
	 * 
	 * @return a string representation of name.
	 */
	public String getName();
	
	/**
	 * Returns a string representation of a Object.
	 * 
	 * @return a string representation of object.
	 */
	public String toString();
	
	/**
	 * Sets the colour of the renderable.
	 * 
	 * @param col - a Java.Color representation of a colour.
	 */
	public void setColour(Color col);
	
	/**
	 * Returns the Java.color of renderable
	 * 
	 * @return a Java.Color representation of renderable.
	 */
	public Color getColor();
	
	
}
