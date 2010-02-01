/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     Flemming N. Larsen
 *     - Javadocs
 *******************************************************************************/
namespace robocode.robotinterfaces
{
    /// <summary>
    /// An evnt interface for receiving basic robot events with an
    /// <see cref="IBasicRobot"/>.
    /// <seealso cref="IBasicRobot"/>
    /// </summary>
    public interface IBasicEvents
    {
        /// <summary>
        /// This method is called every turn in a battle round in order to provide
        /// the robot status as a complete snapshot of the robot's current state at
        /// that specific time.
        /// <p/>
        /// The main benefit of this method is that you'll automatically receive all
        /// current data values of the robot like e.g. the x and y coordinate,
        /// heading, gun heat etc., which are grouped into the exact same time/turn.
        /// <p/>
        /// This is the only way to map the robots data values to a specific time.
        /// For example, it is not possible to determine the exact time of the
        /// robot's heading by calling first calling <see cref="Robot.Time"/> and then
        /// <see cref="Robot.Heading"/> afterwards, as the time <em>might</em> change
        /// after between the <see cref="Robot.Time"/> and <see cref="Robot.Heading"/>
        /// call.
        /// <seealso cref="StatusEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnStatus(StatusEvent evnt);

        /// <summary>
        /// This method is called when one of your bullets hits another robot.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <p/>
        /// <example>
        /// <pre>
        ///   public void OnBulletHit(BulletHitEvent evnt) {
        ///       Out.println("I hit " + evnt.getName() + "!");
        ///   }
        /// </pre>
        /// </example>
        /// <seealso cref="BulletHitEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnBulletHit(BulletHitEvent evnt);

        /// <summary>
        /// This method is called when one of your bullets hits another bullet.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <p/>
        /// <example>
        /// <pre>
        ///   public void OnBulletHitBullet(BulletHitBulletEvent evnt) {
        ///       Out.println("I hit a bullet fired by " + evnt.getBullet().getName() + "!");
        ///   }
        /// </pre>
        ///</example>
        /// <seealso cref="BulletHitBulletEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnBulletHitBullet(BulletHitBulletEvent evnt);

        /// <summary>
        /// This method is called when one of your bullets misses, i.e. hits a wall.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <p/>
        /// <example>
        /// <pre>
        ///   public void OnBulletMissed(BulletMissedEvent evnt) {
        ///       Out.println("Drat, I missed.");
        ///   }
        /// </pre>
        ///</example>
        /// <seealso cref="BulletMissedEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnBulletMissed(BulletMissedEvent evnt);

        /// <summary>
        /// This method is called if your robot dies.
        /// <p/>
        /// You should override it in your robot if you want to be informed of this
        /// evnt. Actions will have no effect if called from this section. The
        /// intent is to allow you to perform calculations or print something out
        /// when the robot is killed.
        /// <seealso cref="DeathEvent"/>
        /// <seealso cref="WinEvent"/>
        /// <seealso cref="BattleEndedEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnDeath(DeathEvent evnt);

        /// <summary>
        /// This method is called when your robot is hit by a bullet.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <p/>
        /// <example>
        /// <pre>
        ///   void OnHitByBullet(HitByBulletEvent evnt) {
        ///       Out.println(event.getRobotName() + " hit me!");
        ///   }
        /// </pre>
        ///</example>
        /// <seealso cref="HitByBulletEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnHitByBullet(HitByBulletEvent evnt);

        /// <summary>
        /// This method is called when your robot collides with another robot.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <p/>
        /// <example>
        /// <pre>
        ///   void OnHitRobot(HitRobotEvent evnt) {
        ///       if (event.getBearing() > -90 && evnt.getBearing() &lt;= 90) {
        ///           Back(100);
        ///       } else {
        ///           Ahead(100);
        ///       }
        ///   }
        /// <p/>
        ///   -- or perhaps, for a more advanced robot --
        /// <p/>
        ///   public void OnHitRobot(HitRobotEvent evnt) {
        ///       if (event.getBearing() > -90 && evnt.getBearing() &lt;= 90) {
        ///           SetBack(100);
        ///       } else {
        ///           SetAhead(100);
        ///       }
        ///   }
        /// </pre>
        /// </example>
        /// <p/>
        /// The angle is relative to your robot's facing. So 0 is straight Ahead of
        /// you.
        /// <p/>
        /// This evnt can be generated if another robot hits you, in which case
        /// <see cref="HitRobotEvent.IsMyFault()"/> will return
        /// {@code false}. In this case, you will not be automatically stopped by the
        /// game -- but if you continue moving toward the robot you will hit it (and
        /// generate another evnt). If you are moving away, then you won't hit it.
        /// <seealso cref="HitRobotEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnHitRobot(HitRobotEvent evnt);

        /// <summary>
        /// This method is called when your robot collides with a wall.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <p/>
        /// The wall at the top of the screen is 0 degrees, right is 90 degrees,
        /// bottom is 180 degrees, left is 270 degrees. But this evnt is relative to
        /// your heading, so: The bearing is such that <see cref="Robot.TurnRight(double)"/>
        /// <see cref="HitWallEvent.Bearing"/> will
        /// point you perpendicular to the wall.
        /// <p/>
        /// <example>
        /// <pre>
        ///   void OnHitWall(HitWallEvent evnt) {
        ///       Out.println("Ouch, I hit a wall bearing " + evnt.Bearing + " degrees.");
        ///   }
        /// </pre>
        /// </example>
        /// <seealso cref="HitWallEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnHitWall(HitWallEvent evnt);

        /// <summary>
        /// This method is called when your robot sees another robot, i.e. when the
        /// robot's radar Scan "hits" another robot.
        /// You should override it in your robot if you want to be informed of this
        /// evnt. (Almost all robots should override this!)
        /// <p/>
        /// This evnt is automatically called if there is a robot in range of your
        /// radar.
        /// <p/>
        /// Note that the robot's radar can only see robot within the range defined
        /// by <see cref="Rules.RADAR_SCAN_RADIUS"/> (1200 pixels).
        /// <p/>
        /// Also not that the bearing of the scanned robot is relative to your
        /// robot's heading.
        /// <p/>
        /// <example>
        /// <pre>
        ///   void OnScannedRobot(ScannedRobotEvent evnt) {
        ///       // Assuming radar and gun are aligned...
        ///       if (event.getDistance() &lt; 100) {
        ///           Fire(3);
        ///       } else {
        ///           Fire(1);
        ///       }
        ///   }
        /// </pre>
        /// </example>
        /// <p/>
        /// <b>Note:</b><br/>
        /// The game assists Robots in firing, as follows:
        /// <ul>
        /// <li>If the gun and radar are aligned (and were aligned last turn),</li>
        /// <li>and the evnt is current,</li>
        /// <li>and you call Fire() before taking any other actions, {@link
        /// Robot.Fire(double) Fire()} will Fire directly at the robot.</li>
        /// </ul>
        /// <p/>
        /// In essence, this means that if you can see a robot, and it doesn't move,
        /// then Fire will hit it.
        /// <p/>
        /// AdvancedRobots will NOT be assisted in this manner, and are expected to
        /// examine the evnt to determine if <see cref="Robot.Fire(double)"/> would
        /// hit. (i.e. you are spinning your gun around, but by the time you get the
        /// evnt, your gun is 5 degrees past the robot).
        /// <seealso cref="ScannedRobotEvent"/>
        /// <seealso cref="Event"/>
        /// <seealso cref="Rules.RADAR_SCAN_RADIUS"/>
        /// </summary>
        void OnScannedRobot(ScannedRobotEvent evnt);

        /// <summary>
        /// This method is called when another robot dies.
        /// You should override it in your robot if you want to be informed of this
        /// evnt.
        /// <seealso cref="RobotDeathEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnRobotDeath(RobotDeathEvent evnt);

        /// <summary>
        /// This method is called if your robot wins a battle.
        /// <p/>
        /// Your robot could perform a victory dance here! :-)
        /// <seealso cref="DeathEvent"/>
        /// <seealso cref="BattleEndedEvent"/>
        /// <seealso cref="Event"/>
        /// </summary>
        void OnWin(WinEvent evnt);
    }
}

//happy