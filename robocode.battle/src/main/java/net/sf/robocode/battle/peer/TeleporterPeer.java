package net.sf.robocode.battle.peer;

import net.sf.robocode.battle.BoundingRectangle;
import net.sf.robocode.teleporters.ITeleporter;
import net.sf.robocode.teleporters.ITeleporter.Portal;

public class TeleporterPeer implements ITeleporter {
	
	private double width;
	private double height;
	
	private boolean blackHole;
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	public TeleporterPeer(double x1, double y1, double x2, double y2) {
		width = 40;
		height = 40;
		setXY(x1, y1, Portal.PORTAL1);
		setXY(x2, y2, Portal.PORTAL2);
		
		if(x2 < 0 || y2 < 0){
			blackHole = true;
		}
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
	

	
	public java.awt.geom.Ellipse2D.Float getCircle(Portal target){
		java.awt.geom.Ellipse2D.Float circle = new java.awt.geom.Ellipse2D.Float();
		if (target == Portal.PORTAL1) {
			circle.setFrame(x1-(width/2), y1-(height/2), width, height);
		} else if (target == Portal.PORTAL2) {
			circle.setFrame(x2-(width/2), y2-(height/2), width, height);
		}
		return circle;
	}
	
	public BoundingRectangle getBoundingBox(Portal target) {
		BoundingRectangle rect = new BoundingRectangle();
		if (target == Portal.PORTAL1) {
			rect.setRect(x1-(width/2), y1-(height/2), width, height);
		} else if (target == Portal.PORTAL2) {
			rect.setRect(x2-(width/2), y2-(height/2), width, height);
		}
		return rect;
	}

	/* Function is called by RobotPeer to test for teleporter
	 * collision on the pair of teleporters.
	 * 
	 * Returns (int x, int y)
	 * 		- x,y > 0 if there is a collision,
	 * which is the corresponding x and y position for the robot
	 * to teleport to.
	 * 		- x,y = -1 if there is no collision
	 * 		- x,y = -2 if the 
	 * 		
	 * 
	 */
	
	public double[] getCollisionReaction(BoundingRectangle bound){
		double[] a = {-1, -1};
		if(this.getCircle(Portal.PORTAL1).intersects(bound)){
			//it collided
			//check for new location
			if(blackHole){
				a[0] = -2;
				a[1] = -2;
			}else{
				a[0] = this.getX(Portal.PORTAL2);
				a[1] = this.getY(Portal.PORTAL2);
			}
		}else if(this.getCircle(Portal.PORTAL2).intersects(bound)){
			a[0] = this.getX(Portal.PORTAL1);
			a[1] = this.getY(Portal.PORTAL1);
		}
		return a;
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
