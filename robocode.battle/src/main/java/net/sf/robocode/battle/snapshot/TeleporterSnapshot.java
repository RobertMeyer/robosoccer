package net.sf.robocode.battle.snapshot;

import robocode.control.snapshot.ITeleporterSnapshot;
import net.sf.robocode.battle.peer.TeleporterPeer;
import net.sf.robocode.teleporters.ITeleporter.Portal;

public class TeleporterSnapshot implements ITeleporterSnapshot {
	
	/** The X position of portal 1 */
	private double x1;

	/** The Y position of portal 1*/
	private double y1;
	
	/** The X position of portal 2*/
	private double x2;

	/** The Y position of portal 2*/
	private double y2;
	
	private boolean blackHole;
	
	private double height;
	private double width;
	
	public TeleporterSnapshot(TeleporterPeer portalPeer) {
		blackHole = portalPeer.isBlackHole();
		height = portalPeer.getHeight();
		width = portalPeer.getWidth();
		x1 = portalPeer.getX(Portal.PORTAL1);
		y1 = portalPeer.getY(Portal.PORTAL1);
		x2 = portalPeer.getX(Portal.PORTAL2);
		y2 = portalPeer.getY(Portal.PORTAL2);
	}

	@Override
	/**
	 * Getter function for Portal 1 X coordinates
	 * @returns x coordinate of portal 1
	 */
	public double getPortal1X() {		
		return x1;
	}

	@Override
	/**
	 * Getter function for Portal 1 Y coordinates
	 * @returns y coordinate of portal 1
	 */
	public double getPortal1Y() {
		return y1;
	}

	@Override
	/**
	 * Getter function for Portal 2 X coordinates
	 * @returns x coordinate of portal 2
	 */
	public double getPortal2X() {
		return x2;
	}

	@Override
	/**
	 * Getter function for Portal 2 Y coordinates
	 * @returns y coordinate of portal 2
	 */
	public double getPortal2Y() {
		return y2;
	}

	@Override
	/**
	 * Returns whether teleporter is a black hole or not
	 * @returns boolean - is teleporter or not
	 */
	public boolean isBlackHole() {
		return blackHole;
	}

	@Override
	/**
	 * Returns the width of the teleporter
	 * @returns double - width of teleporter
	 */
	public double getWidth() {
		return width;
	}

	@Override
	/**
	 * Returns the height of the teleporter
	 * @returns double - height of teleporter
	 */
	public double getHeight() {
		return height;
	}

}
