package robocode.control.snapshot;

import java.awt.geom.AffineTransform;

public interface ICustomObjectSnapshot {
	AffineTransform getMatrix();
	
	String getName();

	String getFilename();
}
