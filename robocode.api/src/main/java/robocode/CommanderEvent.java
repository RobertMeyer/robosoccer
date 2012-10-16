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
import net.sf.robocode.peer.IRobotStatics;
import robocode.Event;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.ISoldierEvents;
import robocode.robotinterfaces.ISoldierRobot;

/**
 * Event for when a button in the Commander controls is pressed.
 *
 * @author Mathew A. Nelson (original)
 */
public class CommanderEvent extends Event {

    private static final long serialVersionUID = 1L;
    private final static int DEFAULT_PRIORITY = 50;
    
    public final int NO_TACTIC = 0;
	public final int PAUSE = 1;
	public final int ADVANCE = 2;
	public final int RETREAT = 3;
	public final int ATTACK = 4;
	public final int INCREASE_POWER = 5;
	public final int DECREASE_POWER = 6;
	public final int TAUNT = 7;
    
    private final String name;
    protected final int tactic;

    /**
     * This constructor is only provided in order to preserve backwards compatibility with old robots that
     * inherits from this Event class.
     * <p>
     * <b>Note</b>: You should not inherit from this class in your own event class!
     * The internal logic of this event class might change. Hence, your robot might
     * not work in future Robocode versions, if you choose to inherit from this class.
     *
     * @deprecated Use {@link #CommanderEvent(String, int)} instead.
     */
    public CommanderEvent() {
        super();
        this.name = null;
        this.tactic = 0;
    }

    /**
     * Called by the game to create a new CommanderEvent.
     *
     * @param name	 the name of the scanned robot
     * @param tactic   the tactic chosen
     */
    public CommanderEvent(String name, int tactic) {
        super();
        this.name = name;
        this.tactic = tactic;
    }
    
    public int getTactic() {
    	return tactic;
    }
    
    /**
     * {@inheritDoc}
     */
   /* @Override
    public final int compareTo(Event event) {
        //Gives higher preference over comparisons
        return -1;
    }*/

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
        
    	ISoldierEvents listener = ((ISoldierRobot)robot).getSoldierEventListener();
    	if (listener != null) {
    		listener.onCommandRecieved(this);
        }
    }
}
