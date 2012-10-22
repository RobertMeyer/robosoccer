package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.RobocodeTestBed;

public class TestPreposition extends RobocodeTestBed {
	
	@Override
	public String getRobotNames() {
		return "sample.Crazy,sample.Target";
	}
	
	@Override
    public String getInitialPositions() {
        return "(50,50,0), (100,50,90)"; 
    }
	/**
	@Override
    public void onRoundStarted(final RoundStartedEvent event) {
        super.onRoundStarted(event);
        if (event.getRound() == 0) {
            IRobotSnapshot crazy = event.getStartSnapshot().getRobots()[0];
            IRobotSnapshot target = event.getStartSnapshot().getRobots()[1];

            Assert.assertNear(50, crazy.getX());
            Assert.assertNear(50, crazy.getY());
            Assert.assertNear(90, crazy.getRadarHeading());
            Assert.assertNear(100, target.getX());
            Assert.assertNear(50, target.getY());
            Assert.assertNear(90, target.getRadarHeading());
        }
    }
    */

}
