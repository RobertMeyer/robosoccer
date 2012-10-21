package net.sf.robocode.battle;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.BallPeer;
import net.sf.robocode.battle.peer.SoccerTeamPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.mode.SoccerMode;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.Bullet;
import robocode.HitByBulletEvent;
import robocode.control.RobotSpecification;
import robocode.util.Utils;

/**
 * Tests for the RobotPeer class.
 */
public class BallTest {
	IHostManager hostManager;
	IRobotRepositoryItem robotItem;
	ISettingsManager properties;
	SoccerTeamPeer team;

	RobotSpecification robotSpecification;
	Battle battle;

	BattleProperties battleProperties;

	@BeforeClass
	public static void setUpClass() {
		HiddenAccess.init();
		Container.init();
	}

	@Before
	public void setUp() {
		hostManager = Mockito.mock(IHostManager.class);
		team = Mockito.mock(SoccerTeamPeer.class);
		battle = Mockito.mock(Battle.class);

		robotItem = Mockito.mock(IRobotRepositoryItem.class);
		robotSpecification = HiddenAccess.createSpecification(robotItem, "",
				"", "", "", "", "", "", "");
	}

	/**
	 * Test the onHitByBullet method.
	 */
	@Test
	public void testOnHitByBullet() {
		Mockito.when(battle.getBattleMode()).thenReturn(new SoccerMode());
		BallBot ball = new BallBot();
		Bullet bullet = new Bullet(0.7, 200, 300, 3, "robot", "BallBot", true, 1);
		double currentX = 100;
		double currentY = 100;
		double currentVelocity = 6;
		double currentHeading = 1.9;

		double[] normal = new double[2];
		normal[0] = currentX - 200;
		normal[1] = currentY - 300;

		/*Calculating the expected results*/
		double[] unitN = new double[2];
		unitN[0] = normal[0]
				/ (Math.sqrt(Math.pow(normal[0], 2) + Math.pow(normal[1], 2)));
		unitN[1] = normal[1]
				/ (Math.sqrt(Math.pow(normal[1], 2) + Math.pow(normal[1], 2)));

		double[] unitT = new double[2];
		unitT[0] = -unitN[1];
		unitT[1] = unitN[0];

		double[] velocityBall = new double[2];
		velocityBall[0] = currentVelocity * Math.cos(currentHeading);
		velocityBall[1] = currentVelocity * Math.sin(currentHeading);

		double[] velocityBullet = new double[2];
		velocityBullet[0] = bullet.getVelocity()
				* Math.cos(bullet.getHeadingRadians());
		velocityBullet[1] = bullet.getVelocity()
				* Math.cos(bullet.getHeadingRadians());

		double vBulletN = unitN[0] * velocityBullet[0] + unitN[1]
				* velocityBullet[1];
		double vBallT = unitT[0] * velocityBall[0] + unitT[1] * velocityBall[1];
		double vBallN = unitN[0] * velocityBall[0] + unitN[1] * velocityBall[1];
		double vBallNAfter = (vBallN * (-999) + 2000 * vBulletN) / 1001;
		double[] finalVBallN = new double[2];
		finalVBallN[0] = vBallNAfter * unitN[0];
		finalVBallN[1] = vBallNAfter * unitN[1];
		double[] finalVBallT = new double[2];
		finalVBallT[0] = vBallT * unitN[0];
		finalVBallT[1] = vBallT * unitN[1];
		double[] finalVBall = new double[2];
		finalVBall[0] = finalVBallN[0] + finalVBallT[0];
		finalVBall[1] = finalVBallN[1] + finalVBallT[1];
		double finalVelocity = Math.sqrt(Math.pow(finalVBall[0], 2)
				+ Math.pow(finalVBall[1], 2));
		double finalHeading = Math.atan(finalVBall[1] / finalVBall[0]);
		/*Finished the calculations*/

		double[] vector = ball.calculateMovement(currentX, currentY, currentVelocity, currentHeading, 200, 300, 11, 0.7);
		assertEquals(finalVelocity, vector[0], 0.1);
		assertEquals(finalHeading, vector[1], 0.1);
	}

}

