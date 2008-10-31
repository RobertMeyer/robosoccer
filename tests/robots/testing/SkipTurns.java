/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package testing;


import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.StatusEvent;


/**
 * @author Pavel Savara (original)
 */
public class SkipTurns extends AdvancedRobot {
	private int skipped = 0;

	final int limit = 5;

	@Override
	public void run() {
		// noinspection InfiniteLoopStatement
		for (;;) {
			if (skipped > limit) {
				// satisfied, end battle please
				throw new Error();
			}
			turnLeft(10);
			if (skipped > limit) {
				// satisfied, end battle please
				throw new Error();
			}
			ahead(1);
			if (skipped > limit) {
				// satisfied, end battle please
				throw new Error();
			}
			turnLeft(10);
			if (skipped > limit) {
				// satisfied, end battle please
				throw new Error();
			}
			back(1);
		}
	}

	@Override
	public void onStatus(StatusEvent e) {
		out.println("live");
		slowResponse();
	}

	@Override
	public void onSkippedTurn(SkippedTurnEvent event) {
		out.println("Skipped!!!");

		skipped++;
	}

	private void slowResponse() {
		Object w = new Object();

		synchronized (w) {
			try {
				if (skipped > 3) {
					w.wait(1000);
				} else {
					w.wait(100);
				}
			} catch (InterruptedException e) {
				// eat interupt
				e.printStackTrace(out);
			}
		}
	}
}
