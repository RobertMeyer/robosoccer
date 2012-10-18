/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package sampleex;

import java.io.PrintStream;
import robocode.*;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.peer.IBasicRobotPeer;
import robocode.robotinterfaces.peer.IStandardRobotPeer;

/**
 * A sample robot.
 * Is not inherited from classic base robots, uses new experimental access to RobotPeer.
 * Use -DEXPERIMENTAL=true to start robocode for this robot.
 * This composition version is showing possible decomposition of robot, main runnable and event handler to different classes.
 *
 * @author Pavel Savara (original)
 */
public class AlienComposition implements IBasicRobot {

    PrintStream out;
    IStandardRobotPeer peer;
    final AlienMain main;
    final AlienEventHandler handler;

    public AlienComposition() {
        main = new AlienMain();
        handler = new AlienEventHandler();
    }

    @Override
    public void setPeer(IBasicRobotPeer iRobotPeer) {
        peer = (IStandardRobotPeer) iRobotPeer;
    }

    @Override
    public void setOut(PrintStream printStream) {
        out = printStream;
    }

    @Override
    public Runnable getRobotRunnable() {
        return main;
    }

    @Override
    public IBasicEvents getBasicEventListener() {
        return handler;
    }

    class AlienMain implements Runnable {

        @Override
        public void run() {
            while (true) {
                peer.move(100); // Move ahead 100
                peer.turnGun(Math.PI * 2); // Spin gun around
                peer.move(-100); // Move back 100
                peer.turnGun(Math.PI * 2); // Spin gun around
            }
        }
    }

    class AlienEventHandler implements IBasicEvents {

        @Override
        public void onScannedRobot(ScannedRobotEvent e) {
            peer.setFire(1);
        }

        @Override
        public void onHitByBullet(HitByBulletEvent e) {
            peer.turnBody(Math.PI / 2 + e.getBearingRadians());
        }

        @Override
        public void onStatus(StatusEvent e) {
        }

        @Override
        public void onBulletHit(BulletHitEvent e) {
        }

        @Override
        public void onBulletHitBullet(BulletHitBulletEvent e) {
        }

        @Override
        public void onBulletMissed(BulletMissedEvent e) {
        }

        @Override
        public void onDeath(DeathEvent e) {
        }

        @Override
        public void onHitRobot(HitRobotEvent e) {
        }

        @Override
        public void onHitWall(HitWallEvent e) {
        }

        @Override
        public void onRobotDeath(RobotDeathEvent e) {
        }

        @Override
        public void onWin(WinEvent e) {
        }

//		@Override
//		public void onWaypointPassed(WaypointPassedEvent event) {
//			// TODO Auto-generated method stub
//			
//		}
    }
}
