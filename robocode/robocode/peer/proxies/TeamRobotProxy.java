/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     - messages are now serialized and deserialized on sender and receiver threads
 *******************************************************************************/
package robocode.peer.proxies;


import robocode.MessageEvent;
import robocode.io.RobocodeObjectInputStream;
import robocode.manager.IHostManager;
import robocode.peer.IRobotPeer;
import robocode.peer.RobotStatics;
import robocode.peer.robot.RobotClassManager;
import robocode.peer.robot.TeamMessage;
import robocode.robotinterfaces.peer.ITeamRobotPeer;

import java.io.*;
import java.util.List;


/**
 * @author Pavel Savara (original)
 */
public class TeamRobotProxy extends AdvancedRobotProxy implements ITeamRobotPeer {
	final int MAX_MESSAGE_SIZE = 32768;
	private final ByteArrayOutputStream byteStreamWriter;

	public TeamRobotProxy(RobotClassManager robotClassManager, IHostManager hostManager, IRobotPeer peer, RobotStatics statics) {
		super(robotClassManager, hostManager, peer, statics);
		byteStreamWriter = new ByteArrayOutputStream(MAX_MESSAGE_SIZE);
	}

	// team
	public String[] getTeammates() {
		getCall();
		return statics.getTeammates();
	}

	public boolean isTeammate(String name) {
		getCall();
		if (name.equals(statics.getName())) {
			return true;
		}
		for (String mate : statics.getTeammates()) {
			if (mate.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void broadcastMessage(Serializable message) throws IOException {
		sendMessage(null, message);
	}

	public void sendMessage(String name, Serializable message) throws IOException {
		setCall();

		try {
			if (!statics.isTeamRobot()) {
				throw new IOException("You are not on a team.");
			}
			byteStreamWriter.reset();
			ObjectOutputStream objectStreamWriter = new ObjectOutputStream(byteStreamWriter);

			objectStreamWriter.writeObject(message);
			objectStreamWriter.flush();
			byteStreamWriter.flush();
			final byte[] bytes = byteStreamWriter.toByteArray();

			objectStreamWriter.reset();
			if (bytes.length > MAX_MESSAGE_SIZE) {
				throw new IOException("Message too big. " + bytes.length + ">" + MAX_MESSAGE_SIZE);
			}
			commands.getTeamMessages().add(new TeamMessage(getName(), name, bytes));
		} catch (IOException e) {
			out.printStackTrace(e);
			throw e;
		}

	}

	@Override
	protected final void loadTeamMessages(List<TeamMessage> teamMessages) {
		if (teamMessages == null) {
			return;
		}
		for (TeamMessage teamMessage : teamMessages) {
			try {
				ByteArrayInputStream byteStreamReader = new ByteArrayInputStream(teamMessage.message);

				byteStreamReader.reset();
				RobocodeObjectInputStream objectStreamReader = new RobocodeObjectInputStream(byteStreamReader,
						robotClassManager.getRobotClassLoader());
				Serializable message = (Serializable) objectStreamReader.readObject();
				final MessageEvent event = new MessageEvent(teamMessage.sender, message);

				RobotClassManager.setTime(event, getTime());
				eventManager.add(event);
			} catch (IOException e) {
				out.printStackTrace(e);
			} catch (ClassNotFoundException e) {
				out.printStackTrace(e);
			}
		}
	}

	// events
	public List<MessageEvent> getMessageEvents() {
		getCall();
		return eventManager.getMessageEvents();
	}
}
