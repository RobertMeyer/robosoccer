package net.sf.robocode.test.equipment;

import java.util.Random;

import org.junit.Test;

import robocode.control.events.BattleStartedEvent;
import robocode.util.Utils;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

public class TestSword extends RobocodeTestBed {

	
	@Test
    @Override
    public void run() {
        super.run();
    }


	@Override
	public String getRobotNames() {
		// TODO Auto-generated method stub
		return "Sword robot test";
	}
	
	@Override
    public void onBattleStarted(BattleStartedEvent event) {
        if (isDeterministic() && isCheckOnBattleStart()) {
            final Random random = Utils.getRandom();

            if (event.getRobotsCount() == 2) {
                Assert.assertNear(0.98484154, random.nextDouble());
            }
        }
    }

}
