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
package net.sf.robocode.js.repository;


import net.sf.robocode.core.Container;
import net.sf.robocode.js.repository.items.handlers.JsScriptHandler;
import net.sf.robocode.js.repository.items.handlers.JsPropertiesHandler;
import net.sf.robocode.js.repository.items.JsRobotItem;


/**
 * @author Pavel Savara (original)
 */
public class Module {
	static {
		// file handlers
		Container.cache.addComponent("jsPropertiesHandler", JsPropertiesHandler.class);
		Container.cache.addComponent("jsScriptHandler", JsScriptHandler.class);
	}
}
