package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.RenderImage;
import net.sf.robocode.battle.item.Flag;
import net.sf.robocode.battle.item.ItemDrop;

/**
 * Basic Construct for the CTF mode:
 *  Contains methods for initialising settings
 *
 *
 * @author Team Telos
 *
 */
public class FlagMode extends ClassicMode {

	/* Point limit for when to finish the game */
    private double pointLimit;
    /* Time limit for when to finish the game */
    private double timeLimit;
    
    List<? extends ItemDrop> itemsG = new ArrayList<ItemDrop>();
    @SuppressWarnings("unchecked")
	List<ItemDrop> items = (List<ItemDrop>) itemsG;
    
    /* The actual flag we need */
    private Flag flag;
    
    /* Location of the file TODO */
    private String imageFile;
    
    /* List of CustomObjects used */
    List<IRenderable> objects = new ArrayList<IRenderable>();
    
    /* The current turn since last flag update */
    private int turnsSinceLastFlagUpdate;
    /* How often the Flag should be moved */
    private final int UPDATE_FLAG_TURNS = 100;

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
     * @return The items needed to be spawned/used
     */
    @Override
    public List<? extends ItemDrop> getItems() {
        return items;
    }

    /**
     * Add Flag as an item to be used for this Mode
     */
    @Override
    public void setItems(Battle battle) {
    	/* Add the flag to the items */
    	flag = new Flag(false, Integer.MAX_VALUE, 0.0, true, battle, null);
        items.add(flag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scoreTurnPoints() {
    	flag.getCarrier().getRobotStatistics().scoreFlag();
    }
    
    /**
     * Robots will respawn instantly on death in a random place.  To be used 
     * with a time limit or point limit to prevent games going on forever.
     * Time limit will be set by default if respawns are on.
     */
    public boolean respawnsOn() {
    	return true;
    }
    
    /**
     * Rounds will last 2 mins at 30 turns per second (3600 turns)
     */
    public int turnLimit() {
    	return 2*30*60;  // 2 min. at 30 turns per sec (default)
    }
    
    /**
     * Add the Flag to the board
     */
    @Override
	public List<IRenderable> createRenderables() {    	
    	/* Add the object and print it */
    	objects.add(new RenderImage("Flag", imageFile, flag.getXLocation(), flag.getYLocation()));
    	
    	return objects;
    }
    
    /**
     * If the Flag is not with a robot and the amount of turns is less than the
     * specified update value, continue.
     * 
     * If the Flag is not with a robot and the amount of turns is greater than the
     * specified update value, randomly move the Flag.
     * 
     * If the Flag is with a robot, keep the Flag attached to the Robot.
     */
    @Override
    public void updateRenderables(List<IRenderable> customObject) {
		/* Check if the flag is with a robot */
    	if (flag.getCarrier() == null) {
    		/* Not with a robot so check to see if it should be moved */
    		if (this.turnsSinceLastFlagUpdate > UPDATE_FLAG_TURNS) {
    			/* Change the location */
    			flag.updateToRandomLocation();
    			
    			/* Only one item but for future */
    			for (IRenderable obj: objects) {
    				if (obj.getName() == "Flag") {
    				    obj.setTranslate(flag.getXLocation(), flag.getYLocation());
    				}
    			}	
    			
    			/* Update since last update */
    			this.turnsSinceLastFlagUpdate++;
    		} else {
    			/* With a robot so set the location to be the carrier */
    			for (IRenderable obj: objects) {
    				if (obj.getName() == "Flag") {
    				    obj.setTranslate(flag.getCarrier().getX(), flag.getCarrier().getY());
    				}
    			}
    			
    			/* Set update since last update to 0 */
    			this.turnsSinceLastFlagUpdate = 0;
    		}
    	}
	}
}
