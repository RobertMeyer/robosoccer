package robocode.equipment;

import java.io.Serializable;

import robocode.control.RobocodeEngine;

/**
 * Defines the properties of a robot, which is returned from
 * {@link RobocodeEngine#getLocalRepository()}.
 *
 * @author CSSE2003 Team Forkbomb
 */
public class EquipmentSpecification implements Serializable {
	private static final long serialVersionUID = 2624853832548250765L;

	private final Object fileSpecification;
	private final String name;
	private final String author;
	private final String webpage;
	private final String version;
	private final String robocodeVersion;
	private final String jarFile;
	private final String fullClassName;
	private final String description;

	private EquipmentSpecification(Object fileSpecification, String name,
			String author, String webpage, String version,
			String robocodeVersion, String jarFile, String fullClassName,
			String description) {
		this.fileSpecification = fileSpecification;
		this.name = name;
		this.author = author;
		this.webpage = webpage;
		this.version = version;
		this.robocodeVersion = robocodeVersion;
		this.jarFile = jarFile;
		this.fullClassName = fullClassName;
		this.description = description;
	}

	public String getClassName() {
		return fullClassName;
	}
}
