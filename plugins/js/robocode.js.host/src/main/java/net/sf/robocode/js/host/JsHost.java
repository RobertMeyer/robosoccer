/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.js.host;


import net.sf.robocode.host.IRobotClassLoader;
import net.sf.robocode.host.JavaHost;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.js.host.security.JsRobotClassLoader;


/**
 * @author Pavel Savara (original)
 */
public class JsHost extends JavaHost {
	public IRobotClassLoader createLoader(IRobotRepositoryItem robotRepositoryItem) {
		return new JsRobotClassLoader(robotRepositoryItem.getClassPathURL(), robotRepositoryItem.getFullClassName());
	}
}
