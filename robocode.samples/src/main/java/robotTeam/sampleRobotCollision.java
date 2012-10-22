/**
 * CSSE2003 Team MCJJ
 * For testing of collision
 */

package robotTeam;

import java.awt.Color;

import robocode.BulletHitEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class sampleRobotCollision extends Robot{
	
	double lastknownEnemyPos;
	double lastknownEnemyDist;
	
	double firePower = Rules.MAX_BULLET_POWER;
	
	public void run(){
		System.out.println("Robot Ready!\n");
		setAllColors(Color.black);
		setAdjustRadarForRobotTurn(true);
		fire(1);
		
		while(true){
			turnRadarLeft(Double.POSITIVE_INFINITY);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent event){
		lastknownEnemyPos = event.getBearing();
		lastknownEnemyDist = event.getDistance();
		
		turnRight(lastknownEnemyPos-getGunHeading()+getHeading());
		
		bulletPower(lastknownEnemyDist);
		ahead(lastknownEnemyDist);
	//	fire(firePower);
	}
	
	public void bulletPower(double distance){
		if(getEnergy()<30)
			firePower = 1;
		else
			firePower = Math.min(800/distance, Rules.MAX_BULLET_POWER);
	}
	
	public void onBulletHit(BulletHitEvent e){
		for(int i=0; i<5; i++){
			ahead(200);
		}
	}
	
	public void onHitWall(HitWallEvent event) {
		turnRight(event.getBearing()+90);
		back(400);		
	}

}
