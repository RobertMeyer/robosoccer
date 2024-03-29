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
package net.sf.robocode.repository.root;

import java.io.File;
import java.net.URL;
import net.sf.robocode.repository.items.IItem;

/**
 * @author Pavel Savara (original)
 */
public interface IRepositoryRoot {

    URL getURL();

    File getPath();

    void update(boolean force);

    void update(IItem item, boolean force);

    boolean isChanged(IItem item);

    boolean isJAR();

    boolean isDevelopmentRoot();

    void extractJAR();
}
