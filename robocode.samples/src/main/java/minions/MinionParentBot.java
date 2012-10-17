package minions;

import java.util.List;

import robocode.*;

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