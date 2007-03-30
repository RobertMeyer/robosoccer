/*******************************************************************************
 * Copyright (c) 2003, 2007 Albert P�rez and RoboRumble contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Coordinator - by Albert Perez
 *     - Initial API and implementation
 *******************************************************************************/
package roborumble.battlesengine;


/**
 * This class is used to coordinate when to read and write to an object like a
 * mutex/monitor.
 *
 * @author Albert P�rez (original)
 * @author Flemming N. Larsen (contributor)
 */
public class Coordinator {
	private boolean available;

	public synchronized void get() {
		while (!available) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		available = false;
		notifyAll();
	}

	public synchronized void put() {
		while (available) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		available = true;
		notifyAll();
	} 
}