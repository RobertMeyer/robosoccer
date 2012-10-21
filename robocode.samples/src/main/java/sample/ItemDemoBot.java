package sample;

import java.awt.Color;

import robocode.HitItemEvent;
import robocode.ItemRobot;
import robocode.ScannedItemEvent;

/**
 * A demonstration robot, designed to showcase the implementation of {@link ScannedItemEvent}
 * and {@link HitItemEvent}.
 * 
 * TODO implement onScannedItem fully, flesh out functionality
 * 
 * @author Ameer Sabri (Dream Team)
 *
 */
public class ItemDemoBot extends ItemRobot {
	
	/**
	 * Method run whenever an instance of the robot is created.
	 */
	boolean itemHit = false;
	boolean itemScanned = false;
	
	public void run() {
		setColors(Color.BLACK ,Color.BLACK ,Color.BLACK ,Color.BLACK ,Color.BLACK);
		
		/*
		 * An infinite loop which moves the robot forward 150 pixels, and turns
		 * the robot at a random angle from -135 degrees to 135 degrees.
		 * The angle is normalised to reduce turn time. Runs for the duration
		 * of the robot's life.
		 * 
		 * Placeholder functionality.
		 */
		while(true) {
			this.doTurnActions();
		}
	}

	public void doTurnActions() {
		ahead(200);
		turnRight((Math.random()*270) - 135);
		if (itemHit){
			setBodyColor(Color.WHITE);
			setRadarColor(Color.WHITE);
			setGunColor(Color.WHITE);
			itemHit = false;
		}
		if (itemScanned){
			setBodyColor(Color.BLUE);
			setRadarColor(Color.BLUE);
			setGunColor(Color.BLUE);
			itemScanned = false;
		}
	}
	
	/**
	 * Method run when the robot hits a wall of the battlefield. Moves the
	 * robot 100 pixels back, and turns the robot at a random angle from 
	 * -180 degrees to 180 degrees. The angle is normalised to reduce turn time.
	 * 
	 * @param e the triggered HitWallEvent event.
	 */
	public void onHitWall() {
		ahead(-100);
		turnRight((Math.random()*360) - 180);
	}
	
	/**
	 * Method run when the robot scans an item. To be finished.
	 */
	public void onScannedItem(ScannedItemEvent e) {
		double x = e.getX();
		double y = e.getY();
		double distance = e.getDistance();
		itemScanned = true;
	}
	
	/**
	 * Method run when the robot hits an item. Changes colour of the robot to all-white.
	 */
	public void onHitItem(HitItemEvent e) {
		itemHit = true;
	}
}
