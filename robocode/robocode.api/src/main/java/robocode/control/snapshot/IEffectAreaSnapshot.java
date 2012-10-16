package robocode.control.snapshot;

public interface IEffectAreaSnapshot {
	
	double getXCoord();
	double getYCoord();
	int getActiveEffectAreas();
	int getTileWidth();
	int getTileHeight();
	int getActiveEffect();
}