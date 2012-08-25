package robocode;

/**
 * A piece of equipment. This class defines all the stats that a piece of
 * equipment can change. Note that the actual mechanical change is performed in
 * the StatRobotPeer class.
 * 
 * Please note that all
 * 
 * @author Malcolm Inglis
 * 
 */
public class EquipmentPart {
	public enum Position {
		BODY, WEAPON, TRACKS, RADAR, POWER;
	}
	
	final Position position;
	final double speed, energy, energyRegen, armor, bulletDamage, bulletSpeed,
			bulletRate, gunHeatRate, ramAttack, ramDefense, radarAngle;

	public static class Builder {
		// Required parameters
		private final Position position;
		
		// Optional parameters - initialized to default values
		private double speed = 0, energy = 0, energyRegen = 0, armor = 0,
				bulletDamage = 0, bulletSpeed = 0, bulletRate = 0,
				gunHeatRate = 0, ramAttack = 0, ramDefense = 0, radarAngle = 0;

		public Builder(Position position) {
			this.position = position;
		}
		
		public EquipmentPart build() {
			return new EquipmentPart(this);
		}

		public Builder speed(double val) {
			speed = val;
			return this;
		}

		public Builder energy(double val) {
			energy = val;
			return this;
		}
		
		public Builder energyRegen(double val) {
			energyRegen = val;
			return this;
		}

		public Builder armor(double val) {
			armor = val;
			return this;
		}

		public Builder bulletDamage(double val) {
			bulletDamage = val;
			return this;
		}

		public Builder bulletSpeed(double val) {
			bulletSpeed = val;
			return this;
		}

		public Builder bulletRate(double val) {
			bulletRate = val;
			return this;
		}

		public Builder gunHeatRate(double val) {
			gunHeatRate = val;
			return this;
		}

		public Builder ramAttack(double val) {
			ramAttack = val;
			return this;
		}

		public Builder ramDefense(double val) {
			ramDefense = val;
			return this;
		}

		public Builder radarAngle(double val) {
			radarAngle = val;
			return this;
		}
	}

	private EquipmentPart(Builder builder) {
		position = builder.position;
		speed = builder.speed;
		energy = builder.energy;
		energyRegen = builder.energyRegen;
		armor = builder.armor;
		bulletDamage = builder.bulletDamage;
		bulletSpeed = builder.bulletSpeed;
		bulletRate = builder.bulletRate;
		gunHeatRate = builder.gunHeatRate;
		ramAttack = builder.ramAttack;
		ramDefense = builder.ramDefense;
		radarAngle = builder.radarAngle;
	}
}
