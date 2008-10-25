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
package robots;


import helpers.RobotTestBed;
import robocode.battle.events.TurnEndedEvent;
import robocode.battle.snapshot.RobotSnapshot;
import org.junit.Test;


/**
 * @author Pavel Savara (original)
 */
public class TestBadNamespace extends RobotTestBed {
	@Test
	public void run() {
		super.run();
	}

	@Override
	public int getExpectedRobotCount(String list) {
		return 1;
	}

	public void onTurnEnded(TurnEndedEvent event) {}

	@Override
	public String getRobotNames() {
		return "sample.Fire,robocode.BadNamespace";
	}
}
