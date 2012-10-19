package sample;

import robocode.Minion;
import robocode.MinionProxy;

public class MinionBot extends Minion {

	public void run() {
		MinionProxy parent = getParent();
		double x = parent.getX();
	}

	@Override
	public int getMinionType() {
		return MINION_TYPE_ATK;
	}
}