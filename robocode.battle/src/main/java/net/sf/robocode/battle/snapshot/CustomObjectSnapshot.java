package net.sf.robocode.battle.snapshot;

import java.awt.geom.AffineTransform;

import robocode.control.snapshot.ICustomObjectSnapshot;

import net.sf.robocode.battle.CustomObject;

public final class CustomObjectSnapshot implements ICustomObjectSnapshot{
	private AffineTransform at;
	private String name;
	private String filename;
	
	public CustomObjectSnapshot() {
		
	}
	
	public CustomObjectSnapshot(CustomObject co) {
		this.at = new AffineTransform();
		this.at.translate(co.getTranslateX(), co.getTranslateY());
		this.at.rotate(co.getRotation());
		this.name = co.getName();
		this.filename = co.getFilename();
	}

	@Override
	public String getFilename() {
		return filename;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public AffineTransform getMatrix() {
		return AffineTransform.getTranslateInstance(at.getTranslateX(), at.getTranslateY());
	}

}
