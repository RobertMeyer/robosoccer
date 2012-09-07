package net.sf.robocode.mode;

public class SlowMode extends ClassicMode {

    @Override
    public double modifyVelocity(double velocityIncrement) {
        return velocityIncrement / 2;
    }

    @Override
    public String toString() {
        return "Slow Mode";
    }

    @Override
    public String getDescription() {
        return "Robots move at half speed.";
    }
}
