/*******************************************************************************
 * Copyright (c) 2001, 2007 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Moved all methods to classes like FileUtil, StringUtil, WindowUtil,
 *       Logger etc. exception for the following methods, which have been kept
 *       here as legacy robots make use of these methods:
 *       - normalAbsoluteAngle()
 *       - normalNearAbsoluteAngle()
 *       - normalRelativeAngle()
 *     - The isNear() was made public
 *     - Optimized and provided javadocs for all methods
 *     - Restored the copy() method for keeping backwards compability with
 *       RoboRumble@Home
 *******************************************************************************/
package robocode.util;


import static java.lang.Math.PI;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Utility class that provide methods for normalizing angles.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class Utils {

	private final static double TWO_PI = 2 * PI;
	private final static double THREE_PI_OVER_TWO = 3 * PI / 2;
	private final static double PI_OVER_TWO = PI / 2;

	// Hide the default constructor as this class only provides static method
	private Utils() {}

	/**
	 * Normalizes an angle to an absolute angle.
	 * The normalized angle will be in the range from 0 to 2*PI, where 2*PI
	 * itself is not included.
	 *
	 * @param angle the angle to normalize
	 * @return the normalized angle that will be in the range of [0,2*PI[
	 */
	public static double normalAbsoluteAngle(double angle) {
		return (angle %= TWO_PI) >= 0 ? angle : (angle + TWO_PI);
	}

	/**
	 * Normalizes an angle to a relative angle.
	 * The normalized angle will be in the range from -PI to PI, where PI
	 * itself is not included.
	 *
	 * @param angle the angle to normalize
	 * @return the normalized angle that will be in the range of [-PI,PI[
	 */
	public static double normalRelativeAngle(double angle) {
		return (angle %= TWO_PI) >= 0 ? (angle < PI) ? angle : angle - TWO_PI : (angle >= -PI) ? angle : angle + TWO_PI;
	}

	/**
	 * Normalizes an angle to be near an absolute angle.
	 * The normalized angle will be in the range from 0 to 2*PI, where 2*PI
	 * itself is not included.
	 * If the normalized angle is near to 0, PI/2, PI, 3*PI/2 or 2*PI, that
	 * angle will be returned. The {@link #isNear(double, double) isNear}
	 * method is used for defining when the angle is near one of angles listed
	 * above.
	 *
	 * @param angle the angle to normalize
	 * @return the normalized angle that will be in the range of [0,2*PI[
	 *
	 * @see #normalAbsoluteAngle(double)
	 * @see #isNear(double, double)
	 */
	public static double normalNearAbsoluteAngle(double angle) {
		angle = (angle %= TWO_PI) >= 0 ? angle : (angle + TWO_PI);

		if (isNear(angle, PI)) {
			return PI;
		} else if (angle < PI) {
			if (isNear(angle, 0)) {
				return 0;
			} else if (isNear(angle, PI_OVER_TWO)) {
				return PI_OVER_TWO;
			}
		} else {
			if (isNear(angle, THREE_PI_OVER_TWO)) {
				return THREE_PI_OVER_TWO;
			} else if (isNear(angle, TWO_PI)) {
				return 0;
			}
		}
		return angle;
	}

	/**
	 * Tests if the two specified angles are near to each other.
	 * Whether or not the specified angles are near t each other is defined by
	 * the following expression:
	 * <code>(Math.abs(angle1 - angle2) < .00001)</code>
	 *
	 * @param angle1 the first angle
	 * @param angle2 the second angle
	 * @return <code>true</code> if the two angles are near to each other;
	 *    <code>false</code> otherwise.
	 */
	public static boolean isNear(double angle1, double angle2) {
		return (Math.abs(angle1 - angle2) < .00001);
	}

	/**
	 * Copies a file into another file.
	 * 
	 * @param inFile the input file to copy
	 * @param outFile the output file to copy to
	 * @return {@code true} if the file was copies succesfully; {@code false}
	 *    otherwise.
	 * @throws IOException
	 */
	public static boolean copy(File inFile, File outFile) throws IOException {
		if (inFile.equals(outFile)) {
			throw new IOException("You cannot copy a file onto itself");
		}
		byte buf[] = new byte[4096];
		FileInputStream in = new FileInputStream(inFile);
		FileOutputStream out = new FileOutputStream(outFile);

		while (in.available() > 0) {
			int count = in.read(buf, 0, 4096);

			out.write(buf, 0, count);
		}
		return true;
	}
}
