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
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import net.sf.robocode.io.Logger;
import net.sf.robocode.repository.Database;

/**
 * @author Pavel Savara (original)
 */
public abstract class BaseRoot implements Serializable, IRepositoryRoot {

    private static final long serialVersionUID = 1L;
    protected transient Database db;
    protected final File rootPath;
    protected final URL rootURL;

    public BaseRoot(Database db, File rootPath) {
        this.db = db;
        this.rootPath = rootPath;

        URL url;

        try {
            url = rootPath.toURI().toURL();
        } catch (MalformedURLException e) {
            url = null;
            Logger.logError(e);
        }
        this.rootURL = url;
    }

    @Override
    public URL getURL() {
        return rootURL;
    }

    @Override
    public File getPath() {
        return rootPath;
    }

    public void setDatabase(Database db) {
        this.db = db;
    }

    @Override
    public String toString() {
        return rootURL != null ? rootURL.toString() : null;
    }

    @Override
    public void extractJAR() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IRepositoryRoot) {
            return ((IRepositoryRoot) obj).getURL().equals(rootURL);
        }
        return false;
    }
}
