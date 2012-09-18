package net.sf.robocode.battle.snapshot;

import robocode.control.snapshot.IDebugProperty;
import robocode.control.snapshot.IItemSnapshot;
import net.sf.robocode.battle.item.ItemDrop;

public class ItemSnapshot implements IItemSnapshot {
	
	private double x;
	private double y;
	
	public ItemSnapshot(ItemDrop battleItem){
		this.x = battleItem.getXLocation();
		this.y = battleItem.getYLocation();
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDestructible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEquippable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDebugProperty[] getDebugProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputStreamSnapshot() {
		// TODO Auto-generated method stub
		return null;
	}

}
