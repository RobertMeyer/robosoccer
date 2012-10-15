package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.HaltPack;
import net.sf.robocode.battle.item.HealthPack;
import net.sf.robocode.battle.item.PoisonPack;
import net.sf.robocode.battle.item.ItemDrop;

public class ItemMode extends ClassicMode {
	

	List<ItemDrop> items = new ArrayList<ItemDrop>();


    @Override
    public String toString() {
        return "Item Mode";
    }

    @Override
    public String getDescription() {
        return "A mode with items and power-ups that robots can pickup and use.";
    }

	public void setItems(Battle battle){
		items.add(new HealthPack(battle,"health1"));
		items.add(new HealthPack(battle,"health2"));
		items.add(new HaltPack(battle,"halt1"));
		items.add(new PoisonPack(battle,"poison1"));
		items.add(new HealthPack(battle,"health3"));
		items.add(new HaltPack(battle,"halt2"));
		items.add(new HealthPack(battle,"health4"));
		items.add(new PoisonPack(battle,"poison2"));

	}
	
	/**
	 * Get the items needed for the items
	 * @return the items needed
	 */
	public List<? extends ItemDrop> getItems() {
		return items;
	}
}
