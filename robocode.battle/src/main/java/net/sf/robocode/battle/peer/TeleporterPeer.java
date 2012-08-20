package net.sf.robocode.battle.peer;

import net.sf.robocode.teleporters.ITeleporter;

public class TeleporterPeer implements ITeleporter {
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	public TeleporterPeer(double x1, double y1, double x2, double y2) {
		setXY(x1, y1, Portal.PORTAL1);
		setXY(x2, y2, Portal.PORTAL2);
	}

	@Override
	public void setXY(double x, double y, Portal target) {
		if (target == Portal.PORTAL1) {		
			x1 = x;
			y1 = y;
		} else if (target == Portal.PORTAL2) {
			x2 = x;
			y2 = y;
		}
	}

	@Override
	public void setX(double x, Portal target) {
		if (target == Portal.PORTAL1) {		
			x1 = x;
		} else if (target == Portal.PORTAL2) {
			x2 = x;
		}
	}

	@Override
	public void setY(double y, Portal target) {
		if (target == Portal.PORTAL1) {		
			y1 = y;
		} else if (target == Portal.PORTAL2) {
			y2 = y;
		}		
	}

	@Override
	public double getX(Portal target) {
		
		return 0;
	}

	@Override
	public double getY(Portal target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPair(ITeleporter pair) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ITeleporter getPair() {
		// TODO Auto-generated method stub
		return null;
	}
}
