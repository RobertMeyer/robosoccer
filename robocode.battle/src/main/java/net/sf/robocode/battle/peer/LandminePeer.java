package net.sf.robocode.battle.peer;

import java.awt.geom.Line2D;
import java.util.List;

import net.sf.robocode.battle.KillstreakTracker;
import net.sf.robocode.peer.LandmineStatus;

import robocode.BattleRules;
import robocode.HitByLandmineEvent;
import robocode.Landmine;
import robocode.LandmineHitEvent;
import robocode.RobotAttribute;
import robocode.Rules;
import robocode.control.snapshot.BulletState;
import robocode.control.snapshot.LandmineState;;

public class LandminePeer {
	private static final int EXPLOSION_LENGTH = 17;
	private static final int RADIUS = 3;
	protected final RobotPeer owner;
	private final BattleRules battleRules;
	private final int landmineId;
	protected RobotPeer victim;
	protected LandmineState state;
	protected double x;
	protected double y;
	protected double power;
	private final Line2D.Double boundingLine = new Line2D.Double();
	protected int frame; // Do not set to -1
	private final int color;
	protected int explosionImageIndex; // Do not set to -1
	
	KillstreakTracker ks;
	
	public LandminePeer(RobotPeer owner, BattleRules battleRules, int landmineId) {
		super();
		this.owner = owner;
		this.battleRules = battleRules;
		this.landmineId = landmineId;
		state = LandmineState.FIRED;
		color = owner.getLandmineColor(); // Store current bullet color set on
										// robot
		ks = owner.battle.getKillstreakTracker();
	}
	
	private Landmine createLandmine(boolean hideOwnerName) {
		String ownerName = (owner == null) ? null
				: (hideOwnerName ? getNameForEvent(owner) : owner.getName());
		String victimName = (victim == null) ? null : (hideOwnerName ? victim
				.getName() : getNameForEvent(victim));

		return new Landmine(x, y, power, ownerName, victimName,
				isActive(), landmineId);
	}
	
	private LandmineStatus createStatus() {
		return new LandmineStatus(landmineId, x, y, victim == null ? null
				: getNameForEvent(victim), isActive());
	}
	
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

				state = LandmineState.HIT_VICTIM;
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
				
				//only apply damage is not botzilla
				if (!otherRobot.isBotzilla()) {
					otherRobot.updateEnergy(-damage);
				}
				
				boolean teamFire = (owner.getTeamPeer() != null && owner
						.getTeamPeer() == otherRobot.getTeamPeer());

				if (!teamFire) {
					owner.getRobotStatistics().scoreBulletDamage(
							otherRobot.getName(), score);
				}

				if (otherRobot.getEnergy() <= 0) {
					if (otherRobot.isAlive()) {
						otherRobot.kill();
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
				if (!otherRobot.isBotzilla()) {
					owner.updateEnergy(Rules.getBulletHitBonus(power));
				}
				
				Landmine landmine = createLandmine(false);

				otherRobot.addEvent(new HitByLandmineEvent(robocode.util.Utils
						.normalRelativeAngle(Math.PI
								- otherRobot.getBodyHeading()), landmine));

				owner.addEvent(new LandmineHitEvent(owner
						.getNameForEvent(otherRobot), otherRobot.getEnergy(),
						landmine));
/**
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
*/
				break;
			}
		}
	}
	
	private String getNameForEvent(RobotPeer otherRobot) {
		if (battleRules.getHideEnemyNames() && !owner.isTeamMate(otherRobot)) {
			return otherRobot.getAnnonymousName();
		}
		return otherRobot.getName();
	}
	
	public boolean isActive() {
		return state.isActive();
	}
	
	public int getLandmineId() {
		return landmineId;
	}

	public int getFrame() {
		return frame;
	}


	public RobotPeer getOwner() {
		return owner;
	}

	public double getPower() {
		return power;
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
	

	public LandmineState getState() {
		return state;
	}

	public int getColor() {
		return color;
	}
	
	public void setPower(double newPower) {
		power = newPower;
	}

	public void setVictim(RobotPeer newVictim) {
		victim = newVictim;
	}

	public void setX(double newX) {
		x = newX;
	}

	public void setY(double newY) {
		y = newY;
	}
	
	public void setState(LandmineState newState) {
		state = newState;
	}

	public void update(List<RobotPeer> robots, List<LandminePeer> landmines) {
		frame++;
		
			if (isActive()) {
				checkRobotCollision(robots);
			}
		
		updateBulletState();
		owner.addLandmineStatus(createStatus());
	}

	protected void updateBulletState() {
		switch (state) {
		case FIRED:
		case HIT_VICTIM:
		case EXPLODED:
			// Note that the bullet explosion must be ended before it goes into
			// the INACTIVE state
			if (frame >= getExplosionLength()) {
				state = LandmineState.INACTIVE;
			}
			break;

		default:
		}
		
	

}
	
	public int getExplosionImageIndex() {
		return explosionImageIndex;
	}
	
	protected int getExplosionLength() {
		return EXPLOSION_LENGTH;
	}
	
	@Override
	public String toString() {
		return getOwner().getName() + " V" + " *" + (int) power
				+ " X" + (int) x + " Y" + (int) y + " H" +  " "
				+ state.toString();
	}
}
