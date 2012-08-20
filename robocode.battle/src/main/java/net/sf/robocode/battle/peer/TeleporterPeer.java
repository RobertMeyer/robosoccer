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

	public void setXY(double x, double y, Portal target) {
		if (target == Portal.PORTAL1) {		
			x1 = x;
			y1 = y;
		} else if (target == Portal.PORTAL2) {
			x2 = x;
			y2 = y;
		}
	}

	public void setX(double x, Portal target) {
		if (target == Portal.PORTAL1) {		
			x1 = x;
		} else if (target == Portal.PORTAL2) {
			x2 = x;
		}
	}

	public void setY(double y, Portal target) {
		if (target == Portal.PORTAL1) {		
			y1 = y;
		} else if (target == Portal.PORTAL2) {
			y2 = y;
		}		
	}

	@Override
	public double getX(Portal target) {
		if (target == Portal.PORTAL1) {
			return x1;
		} else if (target == Portal.PORTAL2) {
			return x2;
		}
		return 0;
	}

	public double getY(Portal target) {
		if (target == Portal.PORTAL1) {
			return y1;
		} else if (target == Portal.PORTAL2) {
			return y2;
		}
		return 0;
	}

	// Ignore the following... 
	
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
