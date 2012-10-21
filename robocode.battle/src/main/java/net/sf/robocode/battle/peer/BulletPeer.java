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
 *     Jonathan Wong
 *     - Implemented Friendly Fire.
 *     	 Added boolean flag for negation of damage when bullet is from a friendly
 *******************************************************************************/
package net.sf.robocode.battle.peer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.geom.Line2D;
import java.util.List;

import net.sf.robocode.battle.FreezeRobotDeath;
import net.sf.robocode.battle.BoundingRectangle;
import net.sf.robocode.battle.FriendlyFireTracker;
import net.sf.robocode.battle.KillstreakTracker;
import net.sf.robocode.peer.BulletStatus;
import robocode.BattleRules;
import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.RobotAttribute;
import robocode.Rules;
import robocode.control.snapshot.BulletState;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Titus Chen (constributor)
 * @author CSSE2003 Team forkbomb (contributor)
 */
public class BulletPeer {
	private static final int EXPLOSION_LENGTH = 17;
	private static final int RADIUS = 3;
	protected final RobotPeer owner;
	private final BattleRules battleRules;
	private final int bulletId;
	protected RobotPeer victim;
	protected BulletState state;
	private double heading;
	protected double x;
	protected double y;
	private double lastX;
	private double lastY;
	protected double power;
	private double deltaX;
	private double deltaY;
	private final Line2D.Double boundingLine = new Line2D.Double();
	protected int frame; // Do not set to -1
	private final int color;
	protected int explosionImageIndex; // Do not set to -1
	//width and height of the bounding box
	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;
	//new bounding box used in get of collision with teleporter
	private final BoundingRectangle boundingBox;

	// the killstreak tracker belonging to the battle
	KillstreakTracker ks;

	public BulletPeer(RobotPeer owner, BattleRules battleRules, int bulletId) {
		super();
		this.owner = owner;
		this.battleRules = battleRules;
		this.bulletId = bulletId;
		state = BulletState.FIRED;
		color = owner.getBulletColor(); // Store current bullet color set on
										// robot
		//init the boudning box
		this.boundingBox = new BoundingRectangle();
		ks = owner.battle.getKillstreakTracker();
	}

	private void checkBulletCollision(List<BulletPeer> bullets) {
		for (BulletPeer b : bullets) {
			if (b != null && b != this && b.isActive()
					&& intersect(b.boundingLine)) {
				state = BulletState.HIT_BULLET;
				frame = 0;
				x = lastX;
				y = lastY;

				b.state = BulletState.HIT_BULLET;
				b.frame = 0;
				b.x = b.lastX;
				b.y = b.lastY;

				owner.addEvent(new BulletHitBulletEvent(createBullet(false), b
						.createBullet(true)));
				b.owner.addEvent(new BulletHitBulletEvent(
						b.createBullet(false), createBullet(true)));
				break;
			}
		}
	}

	private Bullet createBullet(boolean hideOwnerName) {
		String ownerName = (owner == null) ? null
				: (hideOwnerName ? getNameForEvent(owner) : owner.getName());
		String victimName = (victim == null) ? null : (hideOwnerName ? victim
				.getName() : getNameForEvent(victim));

		return new Bullet(heading, x, y, power, ownerName, victimName,
				isActive(), bulletId);
	}

	private BulletStatus createStatus() {
		return new BulletStatus(bulletId, x, y, victim == null ? null
				: getNameForEvent(victim), isActive());
	}

	private String getNameForEvent(RobotPeer otherRobot) {
		if (battleRules.getHideEnemyNames() && !owner.isTeamMate(otherRobot)) {
			return otherRobot.getAnnonymousName();
		}
		return otherRobot.getName();
	}

	// Workaround for http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6457965
	private boolean intersect(Line2D.Double line) {
		double x1 = line.x1, x2 = line.x2, x3 = boundingLine.x1, x4 = boundingLine.x2;
		double y1 = line.y1, y2 = line.y2, y3 = boundingLine.y1, y4 = boundingLine.y2;

		double dx13 = (x1 - x3), dx21 = (x2 - x1), dx43 = (x4 - x3);
		double dy13 = (y1 - y3), dy21 = (y2 - y1), dy43 = (y4 - y3);

		double dn = dy43 * dx21 - dx43 * dy21;

		double ua = (dx43 * dy13 - dy43 * dx13) / dn;
		double ub = (dx21 * dy13 - dy21 * dx13) / dn;

		return (ua >= 0 && ua <= 1) && (ub >= 0 && ub <= 1);
	}

	private void checkRobotCollision(List<RobotPeer> robots) {
		for (RobotPeer otherRobot : robots) {
			if (!(otherRobot == null || otherRobot == owner || otherRobot
					.isDead())
					&& otherRobot.getBoundingBox().intersectsLine(boundingLine)) {

				state = BulletState.HIT_VICTIM;
				frame = 0;
				victim = otherRobot;

				double damage = Rules.getBulletDamage(power);
				double score;

				if (otherRobot.attributes.get().get(RobotAttribute.ARMOR) - 1.0 < 0.00001) {
					score = damage;
				} else {
					// Use inverse relationship --> more armor less damage
					score = damage * (1 / otherRobot.getRobotArmor());
					damage = Rules.getBulletDamage(power)
							* (1 / otherRobot.getRobotArmor());
				}

				if (score > otherRobot.getEnergy()) {
					score = otherRobot.getEnergy();
				}
				
				//only change energy is not botzilla
				if (!otherRobot.isBotzilla()) {
					//Dispensers restore energy, everyone else takes it away
					if (owner.isDispenser()) {
						otherRobot.updateEnergy(damage);
					} else {
						if(!(otherRobot.isParent(owner))) {
							otherRobot.updateEnergy(-damage);
						}
						else {
							owner.updateEnergy(-(damage*2));
						}
					}
				}
				
				/**
				 * @author Jonathan W
				 *The checkbox will be checked, this will be activated only when it is played in team battles.
				 *If a friendly fires at a team member with the checkbox checked, damage will be ignored.
				 */
				
				boolean teamFire = (owner.getTeamPeer() != null && owner
						.getTeamPeer() == otherRobot.getTeamPeer());

				if(FriendlyFireTracker.enableFriendlyfire == true){
					if(teamFire){
						otherRobot.updateEnergy(damage);
					}
				}
				
				if(!teamFire){
					owner.getRobotStatistics().scoreBulletDamage(
							otherRobot.getName(), score);
				}
				
				if (otherRobot.getEnergy() <= 0) {
					owner.battle.getBattleMode().robotKill(owner, otherRobot);
					if (otherRobot.isAlive()) {
						otherRobot.kill();
						
						FreezeRobotDeath massFreeze = new FreezeRobotDeath(otherRobot, owner);
						massFreeze.freezeEverything(robots);
						
						if (owner.battle.getBattleMode().respawnsOn()) {
                    		otherRobot.respawn(robots);
                    	}
						ks.updateKillStreak(owner, otherRobot);
						if (!teamFire) {
							final double bonus = owner.getRobotStatistics()
									.scoreBulletKill(otherRobot.getName());

							if (bonus > 0) {
								owner.println("SYSTEM: Bonus for killing "
										+ (owner.getNameForEvent(otherRobot)
												+ ": " + (int) (bonus + .5)));
							}
						}
					}
				}
				
				//do not give energy bonus for shooting Botzilla
				//Dispensers don't get an energy bonus either
				if (!otherRobot.isBotzilla() && !owner.isDispenser()) {
					owner.updateEnergy(Rules.getBulletHitBonus(power));
				}
				
				Bullet bullet = createBullet(false);

				otherRobot.addEvent(new HitByBulletEvent(robocode.util.Utils
						.normalRelativeAngle(heading + Math.PI
								- otherRobot.getBodyHeading()), bullet));

				owner.addEvent(new BulletHitEvent(owner
						.getNameForEvent(otherRobot), otherRobot.getEnergy(),
						bullet));

				double newX, newY;

				if (otherRobot.getBoundingBox().contains(lastX, lastY)) {
					newX = lastX;
					newY = lastY;

					setX(newX);
					setY(newY);
				} else {
					newX = x;
					newY = y;
				}

				deltaX = newX - otherRobot.getX();
				deltaY = newY - otherRobot.getY();

				break;
			}
		}
	}

	
	private void checkTeleporterCollsion(List<TeleporterPeer> teleporters) {
		/*//x & y are the final result on where to place teleported bullets after checking new teleported location is within bounds of the battlefield
		double x, y;	
		for (TeleporterPeer t : teleporters) {
			if(t.getCircle(Portal.PORTAL1).intersects(boundingBox)){
				//set x to portal position + WIDTH. If this is will place it outside the battlefield, set it to -WIDTH
				x = t.getX(Portal.PORTAL2)+WIDTH;
				if (x > this.battleRules.getBattlefieldWidth()) {
					x = t.getX(Portal.PORTAL2)-WIDTH;
				}
				//set y to portal position + HEIGHT. If this is larger than battlefield, set to -HEIGHT
				y = t.getY(Portal.PORTAL2)+HEIGHT;
				if (y > this.battleRules.getBattlefieldWidth()) {
					y = t.getY(Portal.PORTAL2)-HEIGHT;
				}
				setX(x);
				setY(y);
				setHeading(getHeading()*Math.random()*Math.PI);
				//setHeading(getHeading());
				updateBoundingBox();
				continue;
			} else if(t.getCircle(Portal.PORTAL2).intersects(boundingBox)){
				//same as above but for other portal
				x = t.getX(Portal.PORTAL1)+WIDTH;
				if (x > this.battleRules.getBattlefieldWidth()) {
					x = t.getX(Portal.PORTAL1)-WIDTH;
				}
				y = t.getY(Portal.PORTAL1)+HEIGHT;
				if (y > this.battleRules.getBattlefieldHeight()) {
					y = t.getY(Portal.PORTAL1)-HEIGHT;
				}
				setX(x);
				setY(y);
				setHeading(getHeading()*Math.random()*Math.PI);
				//setHeading(getHeading());
				updateBoundingBox();
			}
		
		}
		*/
	}
	
	private void updateBoundingBox() {
		boundingBox.setRect(getX() - WIDTH / 2 + 2, getY() - HEIGHT / 2 + 2, WIDTH - 4, HEIGHT - 4);
	}
	
	private void checkWallCollision() {
		double ricochetValue = owner.battle.getBattleMode().modifyRicochet(
				battleRules);
		// check if bullet is at boundary
		if ((x - RADIUS <= 0) || (y - RADIUS <= 0)
				|| (x + RADIUS >= battleRules.getBattlefieldWidth())
				|| (y + RADIUS >= battleRules.getBattlefieldHeight())) {
			// check if bullet should ricochet
			if (owner.battle.getBattleMode().shouldRicochet(this.power,
					Rules.MIN_BULLET_POWER, ricochetValue)) {
				// reduce power for the ricochet
				this.power = this.power / ricochetValue;
				// the following checks which wall (top/bottom/side) the bullet
				// is hitting and adjusts the heading accordingly
				if (y - RADIUS <= 0
						|| y + RADIUS >= battleRules.getBattlefieldHeight()) {
					// If bullet tries to escape battlefield reposition on the edge
					if (y - RADIUS < 0) {
						y = 0 + RADIUS;
					} else if (y + RADIUS > battleRules.getBattlefieldHeight()) {
						y = battleRules.getBattlefieldHeight() - RADIUS;
					}
					// top/bottom wall
					if (getHeading() >= Math.PI) {
						// bullet moving in a right to left direction
						setHeading(3 * Math.PI - getHeading());
					} else {
						// bullet moving in a left to right direction
						setHeading(Math.PI - getHeading());
					}
				} else if (x - RADIUS <= 0
						|| x + RADIUS >= battleRules.getBattlefieldWidth()) {
					// If bullet tries to escape battlefield reposition on the edge
					if (x - RADIUS < 0) {
						x = 0 + RADIUS;
					} else if (x + RADIUS > battleRules.getBattlefieldWidth()) {
						x = battleRules.getBattlefieldWidth() - RADIUS;
					}
					// side wall
					setHeading(2 * Math.PI - getHeading());
				}
			} else {
				// Bullet shouldn't ricochet
				state = BulletState.HIT_WALL;
				frame = 0;
				owner.addEvent(new BulletMissedEvent(createBullet(false)));
			}
		}
	}
	
	private void checkObstacleCollision(List<ObstaclePeer> obstacles) {
		for (ObstaclePeer obstacle: obstacles) {
			if (!(obstacle == null)) {
            	if (obstacle.getBoundingBox().intersectsLine(boundingLine)) {
            		state = BulletState.HIT_WALL;
    				frame = 0;
    				owner.addEvent(new BulletMissedEvent(createBullet(false)));
    				obstacle.destroy();
    				/* Can only hit 1 obstacle with 1 bullet */
    				return;
        		}
            }
		}
	}

	public int getBulletId() {
		return bulletId;
	}

	public int getFrame() {
		return frame;
	}

	public double getHeading() {
		return heading;
	}

	public RobotPeer getOwner() {
		return owner;
	}

	public double getPower() {
		return power;
	}

	public double getVelocity() {
		if (owner.getBulletSpeed(power) - 1.0 < 0.00001) {
			return Rules.getBulletSpeed(power);
		} else {
			return owner.getBulletSpeed(power);
		}
	}

	public RobotPeer getVictim() {
		return victim;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getPaintX() {
		return (state == BulletState.HIT_VICTIM && victim != null) ? victim
				.getX() + deltaX : x;
	}

	public double getPaintY() {
		return (state == BulletState.HIT_VICTIM && victim != null) ? victim
				.getY() + deltaY : y;
	}

	public boolean isActive() {
		return state.isActive();
	}

	public BulletState getState() {
		return state;
	}

	public int getColor() {
		return color;
	}

	public void setHeading(double newHeading) {
		heading = newHeading;
	}

	public void setPower(double newPower) {
		power = newPower;
	}

	public void setVictim(RobotPeer newVictim) {
		victim = newVictim;
	}

	public void setX(double newX) {
		x = lastX = newX;
	}

	public void setY(double newY) {
		y = lastY = newY;
	}

	public void setState(BulletState newState) {
		state = newState;
	}

	public void update(List<RobotPeer> robots, List<BulletPeer> bullets, List<ObstaclePeer> obstacles, List<TeleporterPeer> teleporters) {
		frame++;
		if (isActive()) {
			updateMovement();
			checkWallCollision();
			checkTeleporterCollsion(teleporters);
			checkObstacleCollision(obstacles);
			if (isActive()) {
				checkRobotCollision(robots);
			}
			if (isActive() && bullets != null) {
				checkBulletCollision(bullets);
			}
		}
		updateBulletState();
		owner.addBulletStatus(createStatus());
	}

	protected void updateBulletState() {
		switch (state) {
		case FIRED:
			// Note that the bullet must be in the FIRED state before it goes to
			// the MOVING state
			if (frame > 1) {
				state = BulletState.MOVING;
			}
			break;

		case HIT_BULLET:
		case HIT_VICTIM:
		case HIT_WALL:
		case EXPLODED:
			// Note that the bullet explosion must be ended before it goes into
			// the INACTIVE state
			if (frame >= getExplosionLength()) {
				state = BulletState.INACTIVE;
			}
			break;

		default:
		}
	}

	private void updateMovement() {
		lastX = x;
		lastY = y;

		double v = getVelocity();

		x += v * sin(heading);
		y += v * cos(heading);
		updateBoundingBox();
		boundingLine.setLine(lastX, lastY, x, y);
	}

	public int getExplosionImageIndex() {
		return explosionImageIndex;
	}

	protected int getExplosionLength() {
		return EXPLOSION_LENGTH;
	}

	@Override
	public String toString() {
		return getOwner().getName() + " V" + getVelocity() + " *" + (int) power
				+ " X" + (int) x + " Y" + (int) y + " H" + heading + " "
				+ state.toString();
	}

}
