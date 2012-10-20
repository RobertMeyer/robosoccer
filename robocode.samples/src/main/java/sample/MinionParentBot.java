package sample;

import java.util.List;

import robocode.AdvancedRobot;
import robocode.MinionProxy;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

public class MinionParentBot extends AdvancedRobot {
    @Override
    public void run() {
    	spawnMinion(MINION_TYPE_ATK);
    	MinionProxy parent = this.getParent();
    	List<MinionProxy> minions = getMinions();
        while (true) {
            setTurnGunRight(180); // Spin gun around
            waitFor(new TurnCompleteCondition(this));
        }
    }
}