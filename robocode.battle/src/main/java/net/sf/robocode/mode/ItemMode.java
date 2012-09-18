package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.HealthPack;
import net.sf.robocode.battle.ItemDrop;

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
		items.add(new HealthPack(battle));
	}
	
	/**
	 * Get the items needed for the items
	 * @return the items needed
	 */
	public List<? extends ItemDrop> getItems() {
		return items;
	}
}
