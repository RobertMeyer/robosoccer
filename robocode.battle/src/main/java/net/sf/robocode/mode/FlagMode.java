package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleResultsTableModel;
import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.RenderObject;
import net.sf.robocode.battle.item.Flag;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.RobotStatistics;

/**
 * Basic Construct for the CTF mode:
 *  Contains methods for initialising settings
 *
 *
 * @author Team Telos
 *
 */
public class FlagMode extends ClassicMode {
	/* Custom Results Table */
	private BattleResultsTableModel resultsTable;
	
	/* Point limit for when to finish the game */
    private double pointLimit;
    /* Time limit for when to finish the game */
    private double timeLimit;
    
    List<? extends ItemDrop> itemsG = new ArrayList<ItemDrop>();
    @SuppressWarnings("unchecked")
	List<ItemDrop> items = (List<ItemDrop>) itemsG;
    
    /* The actual flag we need */
    private Flag flag;
    
    /* Location of the file */
    private String imageFile = "/net/sf/robocode/ui/images/flag.png";
    
    /* List of CustomObjects used */
    List<IRenderable> objects = new ArrayList<IRenderable>();
    
    /* The current turn since last flag update */
    private int turnsSinceLastFlagUpdate;
    /* How often the Flag should be moved */
    private final int UPDATE_FLAG_TURNS = 500;

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
    	flag = new Flag(false, Integer.MAX_VALUE, 100, true, battle, null);
    	items.add(flag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scoreTurnPoints() {
    	if (flag.getCarrier() != null) {
    		flag.getCarrier().getRobotStatistics().scoreFlag();
    	}
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
     * On the death of a robot that is going to respawn, drop the flag if they're
     * the carrier
     */
    public void onRespawnDeath(RobotPeer robot) {
		if (robot == flag.getCarrier()) {
			/* The carrier so drop the flag */
			flag.setCarrier(null);
			flag.setXLocation(robot.getX());
			flag.setYLocation(robot.getY());
			// Recreate the flag
			// this.createRenderables();
		}
	}
    
    /**
     * Rounds will last 2 mins at 30 turns per second (3600 turns)
     */
    public int turnLimit() {
    	return 800;  // 2 min. at 30 turns per sec (default)
    }
    
    /**
     * Add the Flag to the board
     */
    @Override
	public List<IRenderable> createRenderables() {    	
    	/* Add the object and print it */
    	//PATCH: flag.getXLocation(), flag.getYLocation() are invalid until the flag is spawned (-50,-50).
    	flag.updateToRandomLocation();
    	objects.add(new RenderObject("Flag", imageFile, flag.getXLocation(), flag.getYLocation()));
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
    			
    			this.turnsSinceLastFlagUpdate = 0;
    		}
    		
    		/* Update since last update */
			this.turnsSinceLastFlagUpdate++;
    	}  else {
			/* With a robot, so set the location to be the carrier */
			for (IRenderable obj: objects) {
				if (obj.getName() == "Flag") {
				    obj.setTranslate(flag.getCarrier().getX(), flag.getCarrier().getY());
				}
			}
			
			/* Set update since last update to 0 */
			this.turnsSinceLastFlagUpdate = 0;
		}
	}

    /**
     * Setup for FlagMode to just display the rank, the name, the total score
     * and the flag points
     */
    public void setCustomResultsTable() {
    	if (resultsTable == null) {
			resultsTable = new BattleResultsTableModel();
		}
    	
    	resultsTable.showOverallRank(true);
    	resultsTable.showRobotName(true);
    	resultsTable.showTotalScore(true, "Flag Score");
    	
    	resultsTable.setTitle("Flag Results");
    }
    
    /**
     * {@inheritDoc}
     */
    public BattleResultsTableModel getCustomResultsTable() {
    	return resultsTable;
    }

    /**
	 * Set Overall Score to only contain the Flag Score
	 * @param robotStatistics
	 * @return HashMap containing the scores
	 */
	public Double getCustomOverallScore(RobotStatistics robotStatistics) {
		Double scores = 0.0;
		scores += robotStatistics.showFlagScore();
		return scores;
	}
}
