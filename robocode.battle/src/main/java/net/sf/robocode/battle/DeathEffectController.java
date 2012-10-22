package net.sf.robocode.battle;

import java.util.List;

import net.sf.robocode.battle.item.HaltPack;
import net.sf.robocode.battle.item.HealthPack;
import net.sf.robocode.battle.item.PoisonPack;
import net.sf.robocode.battle.peer.RobotPeer;

public class DeathEffectController {

	private BattleProperties bp;
	public DeathEffectController(){}
	private Battle battle;
	
	public void setup(BattleProperties b, Battle battle) {
		bp = b;
		this.battle = battle;
	}
	/**
	 * Runs the death effect associated with deadRobot. Effects 1-3 are
	 * different sizes of explosions. Effects 4-6 are different effect areas.
	 * Effects 7-9 are item drops.
	 * 
	 * @param deadRobot
	 *            The robot to enforce death effect from
	 */
	public void deathEffect(RobotPeer deadRobot, 
			List<RobotPeer> surrounding, 
			EffectAreaManager eaManager) {
		int finalX = 0;
		int finalY = 0;
		int yOffset = bp.getBattlefieldHeight() % 64;

		// distance and damage variables used for case 1, 2 and 3
		int damage = -5;
		int explosionDistance = 75;

		if (deadRobot.getDeathEffect() > 3) {
			// Round off to closest X and Y tiles
			// Only applicable to case 4, 5 and 6
			finalX = (int) deadRobot.getX() - (int) deadRobot.getX() % 64;

			finalY = (int) deadRobot.getY() - yOffset + 64;
			finalY = (finalY / 64) * 64;
			finalY = finalY + yOffset;
		}

		switch (deadRobot.getDeathEffect()) {
		case 1:
			// Large explosion - small damage
			explosionDistance *= 3;
			explode(deadRobot, surrounding, damage, explosionDistance);
			break;
		case 2:
			// Medium explosion - medium damage
			explosionDistance *= 2;
			damage *= 2;
			explode(deadRobot, surrounding, damage, explosionDistance);
			break;
		case 3:
			// Small explosion - large damage
			damage *= 3;
			explode(deadRobot, surrounding, damage, explosionDistance);
			break;
		case 4:
			// Effect area 1
			EffectArea deathEffect1 = new EffectArea(finalX, finalY, 64, 64, 1);
			eaManager.addEffectArea(deathEffect1);
			break;
		case 5:
			// Effect area 2
			EffectArea deathEffect2 = new EffectArea(finalX, finalY, 64, 64, 2);
			eaManager.addEffectArea(deathEffect2);
			break;
		case 6:
			// Effect area 3
			EffectArea deathEffect3 = new EffectArea(finalX, finalY, 64, 64, 3);
			eaManager.addEffectArea(deathEffect3);
			break;
		case 7:
			// Health pack
			HealthPack hp = new HealthPack(battle, "hp");
			if (battle.getItemControl().spawnItem(hp, deadRobot.getX(), deadRobot.getY())) {
				battle.addItem(hp);
			}
			break;
		case 8:
			// Poison pack
			PoisonPack pp = new PoisonPack(battle, "pp");
			if (battle.getItemControl().spawnItem(pp, deadRobot.getX(), deadRobot.getY())) {
				battle.addItem(pp);
			}
			break;
		case 9:
			// Halt pack
			HaltPack haltPack = new HaltPack(battle, "halt pack");
			if (battle.getItemControl().spawnItem(haltPack, deadRobot.getX(), deadRobot.getY())) {
				battle.addItem(haltPack);
			}
			break;
		}
	}

	private void explode(RobotPeer deadRobot, List<RobotPeer> surrounding,
			int damage, int explosionDistance) {
		for (RobotPeer aliveRobot : surrounding) {
			if (aliveRobot.isAlive()) {
				// Check distance
				// Simple pythagoras math
				double xDist = deadRobot.getX() - aliveRobot.getX();
				if (xDist < 0)
					xDist = xDist * (-1);
				double yDist = deadRobot.getY() - aliveRobot.getY();
				if (yDist < 0)
					yDist = yDist * (-1);
				double robotDistance = Math.sqrt(xDist * xDist + yDist
						* yDist);

				if (robotDistance <= explosionDistance) {
					// robot is within explosion range
					aliveRobot.updateEnergy(damage);
				}
			}
		}
	}
}
