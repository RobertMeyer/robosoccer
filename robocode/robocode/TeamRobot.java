/*******************************************************************************
 * Copyright (c) 2001, 2007 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Bugfix: getTeammates() returned null instead of an empty array when no
 *       teammates exists
 *     - Updated Javadoc
 *******************************************************************************/
package robocode;


import java.io.IOException;
import java.io.Serializable;

import robocode.peer.RobotPeer;


/**
 * An advanced type of robot that supports messages between teammates.
 * <p>
 * If you have not already, you should create a {@link Robot} first.
 *
 * @see Robot
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class TeamRobot extends AdvancedRobot {

	/**
	 * Checks if a given robot name is the name of one of your teammates.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void onScannedRobot(ScannedRobotEvent e) {
	 *       if (isTeammate(e.getName()) {
	 *           return;
	 *       } else {
	 *           fire(1);
	 *       }
	 *   }
	 * </pre>
	 * @param name the robot name to check
	 */
	public boolean isTeammate(String name) {
		if (peer != null) {
			peer.getCall();
			if (peer.getTeamPeer() == null) {
				return false;
			}
			return peer.getTeamPeer().contains(name);
		} else {
			uninitializedException("isTeammate");
			return false;
		}
	}

	/**
	 * Returns the names of all your teammates.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void run() {
	 *       // Prints out all teammates
	 *       for (String teammate : getTeammates()) {
	 *           System.out.println(teammate);
	 *       }
	 *   }
	 * </pre>
	 *
	 * @return an array containing the names of all your teammates.
	 *     The length of the array is equal to the number of teammates
	 */
	public String[] getTeammates() {
		if (peer != null) {
			peer.getCall();
			robocode.peer.TeamPeer teamPeer = peer.getTeamPeer();

			if (teamPeer == null) {
				return new String[0];
			}
			String s[] = new String[teamPeer.size() - 1];

			int index = 0;

			for (RobotPeer teammate : teamPeer) {
				if (teammate != peer) {
					s[index++] = teammate.getName();
				}
			}
			return s;
		} else {
			uninitializedException("getTeammates");
			return null;
		}
	}

	/**
	 * Broadcasts a message to all teammates.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void run() {
	 *       broadcastMessage("I'm here!");
	 *   }
	 * </pre>
	 *
	 * @param message the message to broadcast to all teammates
	 */
	public void broadcastMessage(Serializable message) throws IOException {
		if (peer != null) {
			peer.setCall();
			if (peer.getMessageManager() == null) {
				throw new IOException("You are not on a team.");
			}
			peer.getMessageManager().sendMessage(null, message);
		} else {
			uninitializedException("broadcastMessage");
		}
	}

	/**
	 * Sends a message to one (or more) teammates.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void run() {
	 *       sendMessage("sample.DroidBot", "I'm here!");
	 *   }
	 * </pre>
	 *
	 * @param name the name of the intended recipient of the message
	 * @param message the message to send
	 */
	public void sendMessage(String name, Serializable message) throws IOException {
		if (peer != null) {
			peer.setCall();
			if (peer.getMessageManager() == null) {
				throw new IOException("You are not on a team.");
			}
			peer.getMessageManager().sendMessage(name, message);
		} else {
			uninitializedException("sendMessage");
		}
	}

	/**
	 * This method is called when your robot receives a message from a teammate.
	 * You should override it in your robot if you want to be informed of this
	 * event.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void onMessageReceived(MessageEvent event) {
	 *       out.println(event.getSender() + " sent me: " + event.getMessage());
	 *   }
	 * </pre>
	 *
	 * @param event the event sent by the game
	 *
	 * @see MessageEvent
	 * @see Event
	 */
	public void onMessageReceived(MessageEvent event) {}
}
