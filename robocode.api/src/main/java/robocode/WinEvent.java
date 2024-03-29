/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Updated Javadocs
 *******************************************************************************/
package robocode;

import java.awt.*;
import java.nio.ByteBuffer;
import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.Event;
import robocode.Robot;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

/**
 * This event is sent to {@link Robot#onWin(WinEvent) onWin()} when your robot
 * wins the round in a battle.
 *
 * @author Mathew A. Nelson (original)
 */
public final class WinEvent extends Event {

    private static final long serialVersionUID = 1L;
    private final static int DEFAULT_PRIORITY = 100; // System event -> cannot be changed!

    /**
     * Called by the game to create a new WinEvent.
     */
    public WinEvent() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPriority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
        IBasicEvents listener = robot.getBasicEventListener();

        if (listener != null) {
            listener.onWin(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final boolean isCriticalEvent() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    byte getSerializationType() {
        return RbSerializer.WinEvent_TYPE;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_TYPEINFO;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            return new WinEvent();
        }
    }
}
