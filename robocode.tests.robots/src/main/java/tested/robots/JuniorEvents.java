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
package tested.robots;

import java.awt.*;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Map;
import robocode.*;
import robocode.Event;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IJuniorRobot;
import robocode.robotinterfaces.peer.IBasicRobotPeer;
import robocode.util.Utils;

/**
 * @author Pavel Savara (original)
 */
public class JuniorEvents implements IJuniorRobot, IBasicEvents, Runnable {

    IBasicRobotPeer peer;
    PrintStream out;
    final Hashtable<String, Integer> counts = new Hashtable<String, Integer>();
    Bullet bullet;

    @Override
    public void run() {
        // noinspection InfiniteLoopStatement
        while (true) {
            peer.move(100); // Move ahead 100
            peer.turnGun(Math.PI * 2); // Spin gun around
            peer.move(-100); // Move back 100
            peer.turnGun(Math.PI * 2); // Spin gun around
        }
    }

    @Override
    public Runnable getRobotRunnable() {
        return this;
    }

    @Override
    public IBasicEvents getBasicEventListener() {
        return this;
    }

    @Override
    public void setPeer(IBasicRobotPeer peer) {
        this.peer = peer;
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
    }

    @Override
    public void onStatus(StatusEvent event) {
        count(event);
        final Graphics2D g = peer.getGraphics();

        g.setColor(Color.orange);
        g.drawOval((int) (peer.getX() - 55), (int) (peer.getY() - 55), 110, 110);
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
        count(event);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent event) {
        count(event);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent event) {
        count(event);
    }

    @Override
    public void onDeath(DeathEvent event) {
        count(event);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        count(event);
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        count(event);
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        count(event);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        count(event);
    }

    @Override
    public void onWin(WinEvent event) {
        count(event);

        // this is tested output
        for (Map.Entry<String, Integer> s : counts.entrySet()) {
            out.println(s.getKey() + " " + s.getValue());
        }
        out.println("last bullet heading " + bullet.getHeadingRadians());
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        count(event);

        // Turn gun to point at the scanned robot
        peer.turnGun(Utils.normalAbsoluteAngle(peer.getBodyHeading() + event.getBearingRadians() - peer.getGunHeading())); //

        // Fire!
        double power = 1;

        Bullet firedBullet = peer.fire(power);

        if (firedBullet != null) {
            this.bullet = firedBullet;
        }
    }

    private void count(Event event) {
        final String name = event.getClass().getName();
        Integer curr = counts.get(name);

        if (curr == null) {
            curr = 0;
        }
        counts.put(name, curr + 1);
    }
}
