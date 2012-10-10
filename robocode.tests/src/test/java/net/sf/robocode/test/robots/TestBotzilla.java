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
package net.sf.robocode.test.robots;

//import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * @author Pavel Savara (original)
 */
public class TestBotzilla extends RobocodeTestBed {

	/*
	 * 1. Set mode to Botzilla
	 * 2. Test if it's been set.
	 * 3. When turns hits 749, not Botzilla.
	 * 4. When turns hit botzillaSpawnTime (750) see if it has been spawned.
	 * 
	 * 
	 */
	
    @Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "sample.Target, sample.Walls, sample.Target";
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);
        final int currentTurn = event.getTurnSnapshot().getTurn();
        // System.out.println("Current turn # is: " + currentTurn);
        // TODO change 749 and 750 to use the botzillaSpawnTime variable.
        if (currentTurn == 749) {
        	// Check if Botzilla HAS spawned.
        } else if (currentTurn >= 750) {
        	
        }
    }
    
//    BattleProperties battleProperties = new BattleProperties;
//    battleProperties.setBattleMode(Botzilla);
}
