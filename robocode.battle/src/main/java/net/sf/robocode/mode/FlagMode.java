package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic Construct for the CTF mode:
 *  Contains methods for initialising settings
 *
 *
 * @author Team Telos
 *
 */
public class FlagMode extends ClassicMode {

    // Class Variables
    private double pointLimit;
    private double timeLimit;
    // Score variable
    private double flagScore = 0;
    List<String> items = new ArrayList<String>();

    /**
     *
     */
    @Override
    public String getDescription() {
        return "Robots score points per turn they are holding the flag.";
    }

    /**
     *
     */
    public void execute() {
        System.out.println("Capture The Flag");
    }

    /**
     *
     * @param pointSet
     */
    public void setPointLimit(double pointSet) {
        // Called in gui, pointSet is set to the forms' value
        this.pointLimit = pointSet;
        return;
    }

    /**
     *
     * @param timeSet
     */
    public void setTimeLimit(double timeSet) {
        /*
         * Called in gui, timeSet is set to the forms' value
         * Round time entered in seconds, then it can be converted to turns
         * to allow for time scaling in robocode
         */
        this.timeLimit = timeSet;
        return;
    }

    /**
     *
     * @return the point limit
     */
    public double getPointLimit() {
        return this.pointLimit;
    }

    /**
     *
     * @return the time limit
     */
    public double getTimeLimit() {
        return this.timeLimit;
    }

    @Override
    public double modifyVelocity(double velocityIncrement) {
        // Maybe upon pick up flag make slightly slower.
        return 0;
    }

    @Override
    public String toString() {
        return "Capture the Flag";
    }

    /**
     * Get the items needed for the items
     * @return the items needed
     */
    @Override
    public List<String> getItems() {
        return items;
    }

    /**
     * Add Flag as an item to be used for this Mode
     */
    @Override
    public void setItems() {
        items.add("Flag");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scorePoints() {
        // TODO
        flagScore++;
    }
}
