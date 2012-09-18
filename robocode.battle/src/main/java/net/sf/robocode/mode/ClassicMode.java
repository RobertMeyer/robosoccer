package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.ItemDrop;
import robocode.BattleRules;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import net.sf.robocode.battle.*;
import net.sf.robocode.battle.peer.*;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;

/**
 *
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Classic Mode";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return "Original robocode mode.";
	}
	
	public JPanel getRulesPanel() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Hashtable<String, Object> getRulesPanelValues() {
		return null;
	}
	
	// ----- Mode-specific methods below this line ------
	
	/**
	 * {@inheritDoc}
	 */
	public double modifyVelocity(double velocityIncrement, BattleRules rules) {
		return modifyVelocity(velocityIncrement);
	}
	
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
	}
	
	/**
	 * Returns a list of ItemDrop's to 
	 * spawn in the beginning of the round
	 * @return List of items
	 */
	public List<? extends ItemDrop> getItems() {
		return new ArrayList<ItemDrop>();
	}
	
	/**
	 * Create a list of ItemDrop's to
	 * spawn in the beginning of the round
	 * @param battle The Battle to add the items to
	 */
	public void setItems(Battle battle) {
		/* No items needed for Classic Mode */
	}

	/**
	 * Increments the score for the mode per turn
	 */
	public void scoreTurnPoints() {
		/* ClassicMode does not need a score method, optional for overriding */
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean respawnsOn() {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int turnLimit() {
		return 5*30*60; // 9000 turns is the default
	}
	
	/**
	 * Sets the starting positions for all robot objects.
	 * Original implementation taken from Battle.
	 * @param initialPositions String of initial positions. Parsed by 
	 * the original implementation found in Battle. Can be ignored for 
	 * custom implementations.
	 * @param battleRules Battle rules.
	 * @param robotsCount Size of battlingRobotsList
	 * @return Two dimensional array of coordinates containing
	 * the starting coordinates and heading for each robot.
	 */
	public double[][] computeInitialPositions(String initialPositions,
			BattleRules battleRules, int robotsCount) {
		double[][] initialRobotPositions = null;

        if (initialPositions == null || initialPositions.trim().length() == 0) {
            return null;
        }

        List<String> positions = new ArrayList<String>();

        Pattern pattern = Pattern.compile("([^,(]*[(][^)]*[)])?[^,]*,?");
        Matcher matcher = pattern.matcher(initialPositions);

        while (matcher.find()) {
            String pos = matcher.group();

            if (pos.length() > 0) {
                positions.add(pos);
            }
        }

        if (positions.isEmpty()) {
            return null;
        }

        initialRobotPositions = new double[positions.size()][3];

        String[] coords;
        double x, y, heading;

        for (int i = 0; i < positions.size(); i++) {
            coords = positions.get(i).split(",");

            final Random random = RandomFactory.getRandom();

            x = RobotPeer.WIDTH + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
            y = RobotPeer.HEIGHT + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
            heading = 2 * Math.PI * random.nextDouble();

            int len = coords.length;

            if (len >= 1) {
                // noinspection EmptyCatchBlock
                try {
                    x = Double.parseDouble(coords[0].replaceAll("[\\D]", ""));
                } catch (NumberFormatException e) {
                }

                if (len >= 2) {
                    // noinspection EmptyCatchBlock
                    try {
                        y = Double.parseDouble(coords[1].replaceAll("[\\D]", ""));
                    } catch (NumberFormatException e) {
                    }

                    if (len >= 3) {
                        // noinspection EmptyCatchBlock
                        try {
                            heading = Math.toRadians(Double.parseDouble(coords[2].replaceAll("[\\D]", "")));
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
            initialRobotPositions[i][0] = x;
            initialRobotPositions[i][1] = y;
            initialRobotPositions[i][2] = heading;
        }
        
        return initialRobotPositions;
	}

	/**
	 * The initial setup for robot and contestant lists based on the given
	 * list of robots, battleRobotsList. Overriding this will allow for 
	 * predefined team creation. Original implementation taken from Battle.
	 * @param battle The battle object associated with every game mode
	 * @param battlingRobotsList List of robots to be sorted.
	 * @param hostManager Host manager.
	 * @param robots List of RobotPeers objects.
	 * @param contestants List of ContestantPeer objects.
	 * @param repositoryManager Allows creation of RobotSpecification objects
	 * allowing users to add predefined robots to the battle.
	 */
	public void createPeers(Battle battle,
			RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			List<RobotPeer> robots, List<ContestantPeer> contestants,
			IRepositoryManager repositoryManager) {
		// create teams
        Hashtable<String, Integer> countedNames = new Hashtable<String, Integer>();
        List<String> teams = new ArrayList<String>();
        List<String> teamDuplicates = new ArrayList<String>();
        List<Integer> robotDuplicates = new ArrayList<Integer>();

        // count duplicate robots, enumerate teams, enumerate team members
        for (RobotSpecification specification : battlingRobotsList) {
            final String name = ((IRobotRepositoryItem) HiddenAccess.getFileSpecification(specification)).getUniqueFullClassNameWithVersion();

            if (countedNames.containsKey(name)) {
                int value = countedNames.get(name);

                countedNames.put(name, value == 1 ? 3 : value + 1);
            } else {
                countedNames.put(name, 1);
            }

            String teamFullName = HiddenAccess.getRobotTeamName(specification);

            if (teamFullName != null) {
                if (!teams.contains(teamFullName)) {
                    teams.add(teamFullName);
                    String teamName = teamFullName.substring(0, teamFullName.length() - 6);

                    if (countedNames.containsKey(teamName)) {
                        int value = countedNames.get(teamName);

                        countedNames.put(teamName, value == 1 ? 3 : value + 1);
                    } else {
                        countedNames.put(teamName, 1);
                    }
                }
            }
        }

        Hashtable<String, List<String>> teamMembers = new Hashtable<String, List<String>>();

        // name teams
        for (int i = teams.size() - 1; i >= 0; i--) {
            String teamFullName = teams.get(i);
            String name = teamFullName.substring(0, teamFullName.length() - 6);
            Integer order = countedNames.get(name);
            String newTeamName = name;

            if (order > 1) {
                newTeamName = name + " (" + (order - 1) + ")";
            }
            teamDuplicates.add(0, newTeamName);
            teamMembers.put(teamFullName, new ArrayList<String>());
            countedNames.put(name, order - 1);
        }

        // name robots
        for (int i = battlingRobotsList.length - 1; i >= 0; i--) {
            RobotSpecification specification = battlingRobotsList[i];
            String name = ((IRobotRepositoryItem) HiddenAccess.getFileSpecification(specification)).getUniqueFullClassNameWithVersion();
            Integer order = countedNames.get(name);
            int duplicate = -1;

            String newName = name;

            if (order > 1) {
                duplicate = (order - 2);
                newName = name + " (" + (order - 1) + ")";
            }
            countedNames.put(name, (order - 1));
            robotDuplicates.add(0, duplicate);

            String teamFullName = HiddenAccess.getRobotTeamName(specification);

            if (teamFullName != null) {
                List<String> members = teamMembers.get(teamFullName);

                members.add(newName);
            }
        }

        // create teams
        Hashtable<String, TeamPeer> namedTeams = new Hashtable<String, TeamPeer>();

        // create robots
        for (int i = 0; i < battlingRobotsList.length; i++) {
            RobotSpecification specification = battlingRobotsList[i];
            TeamPeer team = null;

            String teamFullName = HiddenAccess.getRobotTeamName(specification);

            // The team index and robot index depends on current sizes of the contestant list and robot list
            int teamIndex = contestants.size();
            int robotIndex = robots.size();

            if (teamFullName != null) {
                if (!namedTeams.containsKey(teamFullName)) {
                    String newTeamName = teamDuplicates.get(teams.indexOf(teamFullName));

                    team = new TeamPeer(newTeamName, teamMembers.get(teamFullName), teamIndex);

                    namedTeams.put(teamFullName, team);
                    contestants.add(team);

                } else {
                    team = namedTeams.get(teamFullName);
                    if (team != null) {
                        teamIndex = team.getTeamIndex();
                    }
                }
            }
            Integer duplicate = robotDuplicates.get(i);
            // TODO Follow back from here to RobotPeer etc, to
            RobotPeer robotPeer = new RobotPeer(battle, hostManager, specification, duplicate, team, robotIndex);

            robots.add(robotPeer);
            if (team == null) {
                contestants.add(robotPeer);
            }
        }
	}
	
	/**
	 * Perform scan dictates the scanning behaviour of robots. One parameter
	 * List<RobotPeer> is iterated over an performScan called on each robot.
	 * Useful for making some robots invisible to radar.
	 */
	public void updateRobotScans(List<RobotPeer> robotPeers) {
		// Scan after moved all
        for (RobotPeer robotPeer : robotPeers) {
            robotPeer.performScan(robotPeers);
        }
	}
	
	public boolean shouldRicochet() {
		return false;
	}
}
