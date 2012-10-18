package robocode.control.snapshot;

import java.awt.Color;
import java.awt.geom.AffineTransform;

/**
 * This interface defines a snapshot of an CustomObject.
 * 
 * @author Benjamin Evenson @ Team-G1
 */
public interface IRenderableSnapshot {
	
	/**
	 * Returns the AffineTransform of object.
	 * 
	 * @return a AffineTransform representation of object.
	 */
	AffineTransform getAffineTransform();
	
	/**
	 * Returns the x position of object.
	 * 
	 * @return a double representation of x position.
	 */
	double getX();
	
	/**
	 * Returns the y position of object.
	 * 
	 * @return a double representation of y position.
	 */
	double getY();
	
	/**
	 * Returns the angle in radians.
	 * 
	 * @return a double representation of rotation in radians.
	 */
	double getRotation();
	
	/**
	 * Returns the x shear of object.
	 * 
	 * @return a double representation of x shear.
	 */
	double getShearX();
	
	/**
	 * Returns the y shear of object.
	 * 
	 * @return a double representation of y shear.
	 */
	double getShearY();
	
	/**
	 * Returns the x scale of object.
	 * 
	 * @return a double representation of x scale.
	 */
	double getScaleX();
	
	/**
	 * Returns the y scale of object.
	 * 
	 * @return a double representation of y scale.
	 */
	double getScaleY();
	
	/**
	 * Returns the current state of object.
	 * 
	 * @return a boolean of current state of object.
	 */
	boolean getHide();
	
	/**
	 * Returns the alpha level of object.
	 * 
	 * @return a float representation of alpha level.
	 */
	float getAlpha();
	
	/**
	 * Returns the key name of object.
	 * 
	 * @return a string key representation of object.
	 */
	String getName();

	/**
	 * Return the file path of object.
	 * 
	 * @return a file path to object image.
	 */
	String getFilename();
	
	/**
	 * Returns the type of snapshot.
	 * 
	 * @return a enum RenderType
	 */
	RenderableType getType();
	
	/**
	 * Return the colour of renderable
	 * 
	 * @return a java.color representation of colour.
	 */
	Color getColour();
	
	int getSpriteWidth();
	int getSpriteHeight();
	int getRows();
	int getCols();
}
