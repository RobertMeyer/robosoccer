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
package net.sf.robocode.repository.items;

import java.io.Serializable;
import java.net.URL;
import net.sf.robocode.repository.root.IRepositoryRoot;

/**
 * @author Pavel Savara (original)
 */
public abstract class BaseItem implements IItem, Serializable {

    private static final long serialVersionUID = 1L;
    protected URL itemURL;
    protected IRepositoryRoot root;
    protected long lastModified;
    protected boolean isValid;

    public BaseItem(URL itemURL, IRepositoryRoot root) {
        this.itemURL = itemURL;
        this.root = root;
        this.lastModified = 0;
    }

    @Override
    public URL getItemURL() {
        return itemURL;
    }

    @Override
    public IRepositoryRoot getRoot() {
        return root;
    }

    public boolean isInJAR() {
        return root.isJAR();
    }

    @Override
    public long getLastModified() {
        return lastModified;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
