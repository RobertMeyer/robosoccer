package robocode;

public class AirStrike {
	
	public AirStrike() {
		for (int i = 0; i < 20; i++) {
			System.out.println("spawning AS bullet");
			Bullet bullet = new Bullet(0, 100, 100, 2, "AirStrike", null, true, i);
			//BulletCommand wrapper = new BulletCommand(power, false, 0, bulletCounter);

		}

	}
}