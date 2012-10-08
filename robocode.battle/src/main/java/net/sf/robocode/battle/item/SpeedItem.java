package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;


/**
 * A speed item. Extends item drop
 * @author s4238358
 *
 */
public class SpeedItem extends ItemDrop {

    public SpeedItem(Battle battle, String name) {
    	super(true, 400, 0, false, battle);
   //     System.out.println("Speed Item");
        this.name = "SpeedItem";
    }

    public void doItemEffect(RobotPeer robot) {
        return;
    }
}
