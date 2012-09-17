package robocode.control.snapshot;

import java.awt.geom.AffineTransform;

public interface ICustomObjectSnapshot {
	AffineTransform getAffineTransform();
	
	boolean getHide();
	
	float getAlpha();
	
	String getName();

	String getFilename();
}
