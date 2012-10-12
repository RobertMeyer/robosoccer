package robocode.control.snapshot;

public interface ILandmineSnapshot {
	
	LandmineState getState();
	
	String getLandmineSound();
	
	double getPower();
	
	double getX();
	
	double getY();
	
	double getPaintX();
	
	double getPaintY();
	
	int getColor();
	
	int getFrame();
	
	boolean isExplosion();
	
	int getExplosionImageIndex();
	
	int getLandmineId();
	
	int getVictimIndex();
	
	int getOwnerIndex();

}
