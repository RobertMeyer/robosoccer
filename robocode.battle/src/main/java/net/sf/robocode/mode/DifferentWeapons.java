package net.sf.robocode.mode;

/**
 *
 */
public class DifferentWeapons extends ClassicMode {

    public void execute() {
        System.out.println("Different weapons mode");
    }

    @Override
    public String toString() {
        return new String("Different weapons");
    }

    @Override
    public String getDescription() {
        return "Robots can choose a different weapon from the armoury.";
    }
}