package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.peer.BallPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import robocode.BattleRules;
import robocode.control.RobotSpecification;;

public class SoccerMode extends ClassicMode implements IMode {
	
	/* Bounds for the goal - possibly to be altered at a later date */
	public static final double GOALXMIN = 200;
	public static final double GOALXMAX = 700;
	public static final double GOALYMIN = 250;
	public static final double GOALYMAX = 400;
	
	// This stores the ball(s) in a list for use in updateRobotScans
	private List<RobotPeer> ball;
	private List<RobotPeer> robots;
	
	private boolean roundOver = false;
	
	private final String description = "Robocode soccer.";
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Soccer Mode";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String addModeRobots(String selectedRobots) {
		return selectedRobots + ", robots.theBall*";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public double[][] computeInitialPositions(String initialPositions,
			BattleRules battleRules, Battle battle, int robotsCount) {
		double[][] initialRobotPositions = null;
		roundOver = false;
		
		int count = (robotsCount % 2 == 0 ? robotsCount : robotsCount - 1);
		
		initialRobotPositions = new double[(count + 1)][3];
 		
 		double height = battleRules.getBattlefieldHeight();
 		double width = battleRules.getBattlefieldWidth();
 		
 		int teamSize = count / 2;
 		
 		// Horizontal spacing between columns of robots.
 		double xOffset = ((width / 2)) / (1 + Math.max(1, Math.ceil(teamSize / 3.0)));

 		for(int i = 0; i < teamSize; i++) {
 			// Team 1 Initial Positions (Left side of field).
 			initialRobotPositions[i][0] = (width / 2) - (((i/3) + 1) * xOffset);
 			initialRobotPositions[i][1] = (0.2 * height) + (((0.9 * height) / 3) * (i % 3));
 			initialRobotPositions[i][2] = (Math.PI / 2.0);
 			
 			// Team 2 Initial Positions (Right side of field).
 			initialRobotPositions[(i + teamSize)][0] = (width / 2) + (((i / 3) + 1) * xOffset);
 			initialRobotPositions[(i + teamSize)][1] = (0.2 * height) + (((0.9 * height) / 3) * (i % 3));
 			initialRobotPositions[(i + teamSize)][2] = 3 * (Math.PI / 2.0);
 		}
 		
 		// Ball starting position..
 		initialRobotPositions[count][0] = (width / 2);
 		initialRobotPositions[count][1] = (height / 2);
 		initialRobotPositions[count][2] = 0;
 		
 		return initialRobotPositions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPeers(BattlePeers peers,
			RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			IRepositoryManager repositoryManager) {
		Hashtable<String, Integer> duplicates = new Hashtable<String, Integer>();
		int teamSize = peers.getBattle().getRobotsCount() / 2;
		// Counts the number of duplicates for each robot being used.
		for(int i = 0; i < peers.getBattle().getRobotsCount(); i++) {
			RobotSpecification spec = battlingRobotsList[i];
			String name = spec.getName();
			
			if(duplicates.contains(name)) {
				duplicates.put(name, duplicates.get(name) + 1);
			} else {
				duplicates.put(name, 1);
			}
		}
		
		// Create teams 1 and 2.
		TeamPeer team1 = new TeamPeer("Team 1", null, 0);
		TeamPeer team2 = new TeamPeer("Team 2", null, 1);
		TeamPeer ballTeam = new TeamPeer("Ball", null, 2);
		
		peers.addContestant(team1);
		peers.addContestant(team2);
		
		for(int j = 0; j < peers.getBattle().getRobotsCount(); j++) {
			RobotSpecification spec = battlingRobotsList[j];
			RobotPeer robot = null;
			
			if(j < teamSize) {
				robot = new RobotPeer(peers.getBattle(), hostManager, spec, 
						duplicates.get(spec.getName()), team1, j);
			} else {
				robot = new RobotPeer(peers.getBattle(), hostManager, spec, 
						duplicates.get(spec.getName()), team2, j);
			}
			
			peers.addRobot(robot);
		}
		
		// Create the ball robot and add it to the appropriate peer lists/team.
		RobotSpecification ballSpec = 
				repositoryManager.loadSelectedRobots("soccer.BallBot*")[0];
		BallPeer ball = new BallPeer(peers.getBattle(), hostManager, ballSpec, 0, ballTeam, peers.getRobots().size());
		this.ball = new ArrayList<RobotPeer>();
		this.ball.add(ball);

		ballTeam.add(ball);
		peers.addContestant(ballTeam);
		peers.addRobot(ball);
		robots = peers.getRobots();
	}
	
	/**
	 * Perform scan dictates the scanning behaviour of robots. One parameter
	 * List<RobotPeer> is iterated over an performScan called on each robot.
	 * Useful for making some robots invisible to radar.
	 * In this mode it makes the ball the only element visible to radar.
	 */
	@Override
	public void updateRobotScans(List<RobotPeer> robots) {
		// Scan after moved all
		
        for (RobotPeer robotPeer : getRobotsAtRandom(robots)) {
        	if (robotPeer.isBall()) {
        		double x = robotPeer.getX();
        		double y = robotPeer.getY();
        		if (((x < GOALXMIN) || (x > GOALXMAX))
						&& ((y < GOALYMAX) || (y > GOALYMIN))) {
					roundOver = true;
				} else {
					roundOver = false;
				}
        	}
            robotPeer.performScan(ball);
        }
	}
	
	@Override
	public boolean isRoundOver(int endTimer, int time) {
		if (roundOver) {
			for (RobotPeer robotPeer : robots) {
				if (!(robotPeer.isBall())) {
					robotPeer.kill();
				}
			}
		}
		return endTimer > 5*time;
	}
	
	/*@Override
	public List<IRenderable> createRenderables() {
		List<IRenderable> objs = new ArrayList<IRenderable>(); 
		RenderObject obj = new RenderObject ("flag", "/net/sf/robocode/ui/images/flag.png");
		//obj.setTranslate(robots.get(0).getX(), robots.get(0).getY());	
		//obj.setColour(Color.BLUE);
		objs.add(obj);
		return objs;
	}
	
	private int counter = 0; 
	private float degree = 0;
	
	@Override
	public void updateRenderables(List<IRenderable> objects) {
		counter += System.currentTimeMillis();
		Iterator<IRenderable> itr = objects.iterator();
		while (itr.hasNext()) {
			IRenderable obj = (IRenderable)itr.next();
			if (obj.getType() == RenderableType.SPRITE) {
				RenderObject objs = (RenderObject)obj;
				objs.setTranslate(robots.get(0).getBoundingBox().getCenterX(), robots.get(0).getBoundingBox().getCenterY());				
			}
			//obj.toggleHide();
			//itr.remove();
		}	
	}*/
	
	@Override
	public void setGuiOptions() {
		super.uiOptions = new GuiOptions(false, false);
	}
}
