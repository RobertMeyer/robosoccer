package net.sf.robocode.battle.snapshot;

import java.awt.geom.AffineTransform;

import robocode.control.snapshot.ICustomObjectSnapshot;

import net.sf.robocode.battle.CustomObject;

public final class CustomObjectSnapshot implements ICustomObjectSnapshot {
	private AffineTransform at;
	private String name;
	private String filename;
	private boolean hide;
	private float alpha;

	public CustomObjectSnapshot() {

	}

	public CustomObjectSnapshot(CustomObject co) {
		this.at = new AffineTransform();
		this.at = co.getAffineTransform();
		this.name = co.getName();
		this.filename = co.getFilename();
		this.hide = co.getHide();
		this.alpha = co.getAlpha();
	}

	@Override
	public boolean getHide() {
		return this.hide;
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public AffineTransform getAffineTransform() {
		return this.at;
	}

	@Override
	public float getAlpha() {
		return this.alpha;
	}

}
