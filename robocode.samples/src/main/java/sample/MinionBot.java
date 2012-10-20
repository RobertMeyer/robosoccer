package sample;

import robocode.Minion;
import robocode.MinionProxy;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

public class MinionBot extends Minion {
	public void run() {
		MinionProxy parent = getParent();
		double x = parent.getX();
        while (true) {
            setTurnGunRight(180); // Spin gun around
            waitFor(new TurnCompleteCondition(this));
        }
	}
	@Override
	public int getMinionType() {
		return MINION_TYPE_ATK;
	}
}