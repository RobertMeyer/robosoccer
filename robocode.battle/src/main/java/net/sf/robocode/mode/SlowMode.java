package net.sf.robocode.mode;

public class SlowMode extends ClassicMode {

    public double modifyVelocity(double velocityIncrement) {
        return velocityIncrement / 2;
    }

    public String toString() {
        return "Slow Mode";
    }

    public String getDescription() {
        return "Robots move at half speed.";
    }
}
