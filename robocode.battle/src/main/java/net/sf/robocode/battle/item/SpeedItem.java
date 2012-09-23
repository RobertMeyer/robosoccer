package net.sf.robocode.battle.item;


/**
 * A speed item. Extends item drop
 * @author s4238358
 *
 */
public class SpeedItem extends ItemDrop {

    public SpeedItem(boolean isDestroyable, int lifespan, int health, boolean isEquippable) {
        super(isDestroyable, lifespan, health, isEquippable, null);
   //     System.out.println("Speed Item");
        this.name = "SpeedItem";
    }

    public void doItemEffect() {
        return;
    }
}