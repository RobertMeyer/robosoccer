/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *******************************************************************************/
package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
public class WinException extends Error { // Must be error!

    private static final long serialVersionUID = 1L;

    public WinException() {
        super();
    }

    public WinException(String s) {
        super(s);
    }
}
