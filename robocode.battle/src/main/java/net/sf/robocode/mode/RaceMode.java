package net.sf.robocode.mode;

public class RaceMode extends ClassicMode {

    public void execute() {
        System.out.println("Race Mode.");
    }

    //overwriting toString() class so our "mode" name is now returned
    @Override
    public String toString() {
        return new String("Race Mode");
    }
}
