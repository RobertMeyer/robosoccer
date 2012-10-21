/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Joel Addison
 *     - Initial API and implementation
 *     Jack Toohey
 *     - Changes to create dynamic sized obstacles.
 *******************************************************************************/
package net.sf.robocode.battle.peer;


import robocode.*;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.BoundingRectangle;


/** Obstacle object for use in Obstacle mode and Maze mode.
 * Obstacles are placed onto the battlefield at the start of each battle.
 * Robots cannot see or move through obstacles.
 * Depending on the game mode settings, bullets with either be destroyed be harmless
 * or will destroy the obstacle on collision.
 * Bullets can never move through obstacles.
 * 
 * @author Joel Addison
 * @author Jack Toohey (contributor)
 */
public class ObstaclePeer {

	// Use width and height of the background squares
	private int width = 32;
    private int height = 32;
    protected BattleRules battleRules;
    protected Battle battle;
    protected final BoundingRectangle boundingBox;
	
	protected double x;
	protected double y;

	public ObstaclePeer(Battle battle, BattleRules battleRules, int obstacleId) {
		super();
		this.battle = battle;
		this.battleRules = battleRules;
		this.boundingBox = new BoundingRectangle();
		updateBoundingBox();
	}
	
	public void destroy() {
		battle.registerDestroyedWall(this);
	}

	public void setX(double x) {
		this.x = x;
		updateBoundingBox();
	}

	public void setY(double y) {
		this.y = y;
		updateBoundingBox();
	}
	
	public void setWidth(int width) {
		this.width = width;
		updateBoundingBox();
	}
	
	public void setHeight(int height) {
		this.height = height;
		updateBoundingBox();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

    public double getX() {
        return x;
    }

	public double getY() {
        return y;
    }
	
    public void updateBoundingBox() {
        boundingBox.setRect(x - width / 2, y - height / 2, width, height);
    }
	
    public BoundingRectangle getBoundingBox() {
        return boundingBox;
    }
	
	@Override
	public String toString() {
		return "Obstacle (" + getX() + ", " + getY() + ")";
	}
	
	public boolean obstacleIntersect(ObstaclePeer o) {
		this.updateBoundingBox();
		o.updateBoundingBox();
		if (this.getBoundingBox().intersects(o.getBoundingBox())) {
			return true;
		}
		return false;		
	}
}
