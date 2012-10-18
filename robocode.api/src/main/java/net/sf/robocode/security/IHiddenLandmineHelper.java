package net.sf.robocode.security;

import robocode.Landmine;;
public interface IHiddenLandmineHelper {

	void update(Landmine landmine, double x, double y, String victimName, boolean isActive);
}
