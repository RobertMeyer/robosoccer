package net.sf.robocode.battle;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import robocode.control.RobotSpecification;
import net.sf.robocode.battle.peer.ContestantPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;

/**
 * BattlePeers is a class that models the creation of RobotPeers. It takes the following parameters:
 * 
 * @param battlingRobotsList List of robots to be sorted.
 * @param hostManager Host manager.
 * @param repositoryManager Allows creation of RobotSpecification objects allowing users to 
 * add predefined robots to the battle.
 * 
 * In addition, it defines the following fields:
 * @param robots List of RobotPeers objects.
 * @param contestants List of ContestantPeer objects.
 */
public class BattlePeers {
	
	private List<RobotPeer> robots = new ArrayList<RobotPeer>();
	private List<ContestantPeer> contestants = new ArrayList<ContestantPeer>();
	private IHostManager hostManager;
	private IRepositoryManager repositoryManager;
	private Battle battle;
	private RobotPeer botzillaRobot;
	private ContestantPeer botzillaContestant;

	public BattlePeers(Battle battle, RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			IRepositoryManager repositoryManager) {
		this.hostManager = hostManager;
		this.repositoryManager = repositoryManager;
		this.battle = battle;
        
        battle.getBattleMode().createPeers(this, battlingRobotsList, hostManager, repositoryManager);
	}
	

	/**
	 * The initial setup for robot and contestant lists based on the given
	 * list of robots, battleRobotsList. Overriding this will allow for 
	 * predefined team creation. Original implementation taken from Battle.
	 * 
	 * @param battlingRobotsList List of robots to be sorted. (same battlingRobotsList as the one passed
	 * to the constructor)
	 */
	public void createPeers(RobotSpecification[] battlingRobotsList) {
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

	public List<RobotPeer> getRobots() {
		return robots;
	}

	public List<ContestantPeer> getContestants() {
		return contestants;
	}
	
	public Battle getBattle(){
		return battle;
	}
	
	public IHostManager getHostManager(){
		return hostManager;
	}

	public void addRobot(RobotPeer peer) {
		botzillaRobot = peer;
		robots.add(peer);
	}
	
	public void addContestant(ContestantPeer peer) {
		botzillaContestant = peer;
		contestants.add(peer);
	}
	
	public void removeBotzilla() {
		robots.remove(botzillaRobot);
		contestants.remove(botzillaContestant);
		System.out.println("botzilla is totally removed?");
	}
	
	public void removeRobots(ArrayList<RobotPeer> robotsToRemove){
		for(RobotPeer peer : robotsToRemove){
			robots.remove(peer);
		}
	}
	
	public void cleanup() {
		if (contestants != null) {
			contestants.clear();
			contestants = null;
		}

		if (robots != null) {
			robots.clear();
			robots = null;
		}

	}
	
}
