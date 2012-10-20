package robocode.equipment;

import java.io.Serializable;

import robocode.control.RobocodeEngine;

/**
 * Defines the properties of a piece of equipment, which is returned from
 * {@link RobocodeEngine#getLocalRepository()}.
 *
 * @author CSSE2003 Team Forkbomb
 */
public class EquipmentSpecification implements Serializable {
	private static final long serialVersionUID = 2624853832548250765L;

	private final String fullClassName;

	private EquipmentSpecification(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getClassName() {
		return fullClassName;
	}
}
