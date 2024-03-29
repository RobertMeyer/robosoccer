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
package net.sf.robocode.repository.items.handlers;

import java.net.URL;
import net.sf.robocode.core.Container;
import net.sf.robocode.repository.Database;
import net.sf.robocode.repository.items.IItem;
import net.sf.robocode.repository.items.RobotItem;
import net.sf.robocode.repository.root.IRepositoryRoot;

/**
 * Handler for accepting and registering .properties files.
 *
 * @author Pavel Savara (original)
 */
public class PropertiesHandler extends ItemHandler {

    @Override
    public IItem acceptItem(URL itemURL, IRepositoryRoot root, Database db) {
        final String name = itemURL.toString().toLowerCase();

        if (name.endsWith(".properties") && !name.endsWith("robocode.properties")) {
            return register(itemURL, root, db);
        }
        return null;
    }

    private IItem register(URL itemURL, IRepositoryRoot root, Database db) {
        RobotItem item = (RobotItem) db.getItem(itemURL.toString());

        if (item == null) {
            item = createItem(itemURL, root, db);
        }
        db.putItem(item);
        return item;
    }

    protected RobotItem createItem(URL itemURL, IRepositoryRoot root, Database db) {
        final RobotItem robotItem = new RobotItem(itemURL, root);

        robotItem.setPropertiesURL(itemURL);

        final String lang = robotItem.getRobotLanguage();

        if (!lang.equals("java")) {
            // dispatch to other robot types
            String uplang = lang.substring(0, 1).toUpperCase() + lang.substring(1).toLowerCase();
            final PropertiesHandler handler = Container.getComponent(PropertiesHandler.class,
                                                                     uplang + "PropertiesHandler");

            return handler.createItem(itemURL, root, db);
        }
        return robotItem;
    }
}
