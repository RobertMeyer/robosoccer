/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Code cleanup & optimizations
 *     - Bugfix: checkBulletCollision() now uses a workaround for the Java 5 bug
 *       #6457965 with Line2D.intersectsLine via intersect(Line2D.Double line)
 *     - Integration of robocode.Rules
 *     - Replaced width and height with radius
 *     - Added constructor for the BulletRecord to support the replay feature
 *     - Fixed synchonization issues on member fields and methods
 *     - Some private methods were declared public, and have therefore been
 *       redeclared as private
 *     - Replaced getting the number of explosion frames from image manager with
 *       integer constant
 *     - Removed hitTime and resetHitTime(), which is handled thru frame instead
 *     - Added getExplosionLength() to get the exact number of explosion frames
 *       for this class and sub classes
 *     - The update() method is now removing the bullet from the battle field,
 *       when the bullet reaches the inactive state (i.e. is finished)
 *     - Bugfix: Changed the delta coordinates of a bullet explosion on a robot,
 *       so that it will be on the true bullet line for all bullet events
 *     - The coordinates of the bullet when it hits, and the coordinates for the
 *       explosion rendering on a robot has been split. So now the bullet is
 *       painted using the new getPaintX() and getPaintY() methods
 *     Luis Crespo
 *     - Added states
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Titus Chen
 *     - Bugfix: Added Battle parameter to the constructor that takes a
 *       BulletRecord as parameter due to a NullPointerException that was raised
 *       as the battleField variable was not intialized
 *     Pavel Savara
 *     - disconnected from Bullet, now we rather send BulletStatus to proxy side
 *******************************************************************************/
package net.sf.robocode.battle.peer;


import robocode.*;

import java.awt.geom.Line2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BoundingRectangle;


/**
 * @author Joel Addison
 */
public class ObstaclePeer {

	// Use width and height of the background squares
	public final static int WIDTH = 64;
    public final static int HEIGHT = 64;
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
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

    public double getX() {
        return x;
    }

	public double getY() {
        return y;
    }
	
    public void updateBoundingBox() {
        boundingBox.setRect(x - WIDTH / 2 + 2, y - HEIGHT / 2 + 2, WIDTH - 4, HEIGHT - 4);
    }
	
    public BoundingRectangle getBoundingBox() {
        return boundingBox;
    }
	
	@Override
	public String toString() {
		return "Obstacle";
	}
}
