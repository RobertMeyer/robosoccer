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
}
