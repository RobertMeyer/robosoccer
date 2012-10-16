package net.sf.robocode.test.robots;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
/**
 * @author Jordan Henderson
 */
public class TestMinionSpawn extends RobocodeTestBed {

    @Test
    @Override
    public void run() {
        super.run();

    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
    	super.onTurnEnded(event);
    	final int currentTurn = event.getTurnSnapshot().getTurn();
    	if(currentTurn == 1) {
	        final IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[0];
	        List<IRobotSnapshot> minions = robot.getMinions();
	        //Check 1 minion has been spawned.
	        int mSize = minions.size();
	        Assert.assertTrue("minions.size() = " + mSize + ", expected 1. Aborting.", mSize == 1);
	        if(minions.size() != 1)
	        	return;
	        //Check the minion is actually a minion.
	        IRobotSnapshot minion = minions.get(0);
	        boolean isMinion = minion.isMinion();
	        Assert.assertTrue("minion[0].isMinion() = " + isMinion + ", expected true. Aborting.", isMinion);
        }
    }
    
	@Override
    public String getRobotNames() {
        return "sample.MinionParentBot";
    }

}
