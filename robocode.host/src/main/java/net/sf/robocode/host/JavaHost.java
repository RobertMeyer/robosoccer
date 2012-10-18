/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.host;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import net.sf.robocode.host.proxies.*;
import net.sf.robocode.host.security.RobotClassLoader;
import net.sf.robocode.io.Logger;
import static net.sf.robocode.io.Logger.logError;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.repository.RobotType;
import net.sf.robocode.security.HiddenAccess;
import robocode.Droid;
import robocode.Robot;
import robocode.control.RobotSpecification;
import robocode.robotinterfaces.*;

/**
 * @author Pavel Savara (original)
 */
public class JavaHost implements IHost {
	
	public IRobotClassLoader createLoader(IRobotRepositoryItem robotRepositoryItem) {
		return new RobotClassLoader(robotRepositoryItem.getClassPathURL(), robotRepositoryItem.getFullClassName());
	}

    @Override
	public IHostingRobotProxy createRobotProxy(IHostManager hostManager, RobotSpecification robotSpecification, IRobotStatics statics, IRobotPeer peer) {
		IHostingRobotProxy robotProxy;
		final IRobotRepositoryItem specification = (IRobotRepositoryItem) HiddenAccess.getFileSpecification(
				robotSpecification);

		if (specification.isTeamRobot()) {
			robotProxy = new TeamRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isAdvancedRobot()) {
			robotProxy = new AdvancedRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isStandardRobot()) {
			robotProxy = new StandardRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isJuniorRobot()) {
			robotProxy = new JuniorRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else {
			throw new AccessControlException("Unknown robot type");
		}
		return robotProxy;
	}

    @Override
	public String[] getReferencedClasses(IRobotRepositoryItem robotRepositoryItem) {
		IRobotClassLoader loader = null;

		try {
			loader = createLoader(robotRepositoryItem);
			loader.loadRobotMainClass(true);
			return loader.getReferencedClasses();

		} catch (ClassNotFoundException e) {
			Logger.logError(e);
			return new String[0];
		} finally {
			if (loader != null) {
				loader.cleanup();
			}
		}
	}

    @Override
	public RobotType getRobotType(IRobotRepositoryItem robotRepositoryItem, boolean resolve, boolean message) {
		IRobotClassLoader loader = null;

		try {
			loader = createLoader(robotRepositoryItem);
			Class<?> robotClass = loader.loadRobotMainClass(resolve);

			if (robotClass == null || java.lang.reflect.Modifier.isAbstract(robotClass.getModifiers())) {
				// this class is not robot
				return RobotType.INVALID;
			}
			return checkInterfaces(robotClass, robotRepositoryItem);

		} catch (Throwable t) {
			if (message) {
				logError("Got an error with " + robotRepositoryItem.getFullClassName() + ": " + t); // just message here
				if (t.getMessage() != null && t.getMessage().contains("Bad version number in .class file")) {
					logError("Maybe you run robocode with Java 1.5 and robot was compiled for later Java version ?");
				}
			}
			return RobotType.INVALID;
		} finally {
			if (loader != null) {
				loader.cleanup();
			}
		}
	}

    @Override
   	public int getMinionType(IRobotRepositoryItem robotRepositoryItem, boolean message) {
   		IRobotClassLoader loader = null;

   		try {
   			loader = createLoader(robotRepositoryItem);
   			Class<?> robotClass = loader.loadRobotMainClass(false);

   			if (robotClass == null || java.lang.reflect.Modifier.isAbstract(robotClass.getModifiers())) {
   				// this class is not a robot
   				return -1;
   			}
   			if(!IMinionRobot.class.isAssignableFrom(robotClass)) {
   				// not a minion.
   				return -1;
   			}
   			//Create an instance of the minion to get it's type.
   			IMinionRobot robot = (IMinionRobot)loader.createRobotInstance();
   			return robot.getMinionType();   			

   		} catch (Throwable t) {
   			if (message) {
   				logError("Got an error with " + robotRepositoryItem.getFullClassName() + ": " + t); // just message here
   				if (t.getMessage() != null && t.getMessage().contains("Bad version number in .class file")) {
   					logError("Maybe you run robocode with Java 1.5 and robot was compiled for later Java version ?");
   				}
   			}
   			return -1;
   		} finally {
   			if (loader != null) {
   				loader.cleanup();
   			}
   		}
   	}
    
	private RobotType checkInterfaces(Class<?> robotClass, IRobotRepositoryItem robotRepositoryItem) {
		boolean isJuniorRobot = false;
		boolean isStandardRobot = false;
		boolean isInteractiveRobot = false;
		boolean isPaintRobot = false;
		boolean isAdvancedRobot = false;
		boolean isTeamRobot = false;
		boolean isDroid = false;
		boolean isHouseRobot = false;
		boolean isFreezeRobot = false;
        boolean isBall = false;
        boolean isBotzilla = false;
        boolean isZombie = false;
        boolean isDispenser = false;
        boolean isMinion = false;

        if (Droid.class.isAssignableFrom(robotClass)) {
            isDroid = true;
        }

        if (ITeamRobot.class.isAssignableFrom(robotClass)) {
            isTeamRobot = true;
        }

		if (ITeamRobot.class.isAssignableFrom(robotClass)) {
			isTeamRobot = true;
		}

		if (IAdvancedRobot.class.isAssignableFrom(robotClass)) {
			isAdvancedRobot = true;
		}
		
		if (IHouseRobot.class.isAssignableFrom(robotClass)) {
			isHouseRobot = true;
		}
		
		if (IFreezeRobot.class.isAssignableFrom(robotClass)) {
			isFreezeRobot = true;
		}

        if (IBall.class.isAssignableFrom(robotClass)) {
			isBall = true;
		}
        
        if (IBotzilla.class.isAssignableFrom(robotClass)) {
        	isBotzilla = true;
        }   
        
        if (IZombie.class.isAssignableFrom(robotClass)) {
        	isZombie = true;
        }
        
        if (IDispenser.class.isAssignableFrom(robotClass)) {
        	isDispenser = true;
        }

        if(IMinionRobot.class.isAssignableFrom(robotClass)) {
        	isMinion = true;
        }
        if (IInteractiveRobot.class.isAssignableFrom(robotClass)) {
            // in this case we make sure that robot don't waste time
            if (checkMethodOverride(robotClass, Robot.class, "getInteractiveEventListener")
                    || checkMethodOverride(robotClass, Robot.class, "onKeyPressed", KeyEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onKeyReleased", KeyEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onKeyTyped", KeyEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseClicked", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseEntered", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseExited", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMousePressed", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseReleased", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseMoved", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseDragged", MouseEvent.class)
                    || checkMethodOverride(robotClass, Robot.class, "onMouseWheelMoved", MouseWheelEvent.class)) {
				isInteractiveRobot = true;
			}
		}

		if (IPaintRobot.class.isAssignableFrom(robotClass)) {
			if (checkMethodOverride(robotClass, Robot.class, "getPaintEventListener")
                    || checkMethodOverride(robotClass, Robot.class, "onPaint", Graphics2D.class)) {
                isPaintRobot = true;
            }
        }

        if (Robot.class.isAssignableFrom(robotClass) && !isAdvancedRobot) {
            isStandardRobot = true;
        }

        if (IJuniorRobot.class.isAssignableFrom(robotClass)) {
            isJuniorRobot = true;
            if (isAdvancedRobot) {
                throw new AccessControlException(
                        robotRepositoryItem.getFullClassName()
                        + ": Junior robot should not implement IAdvancedRobot interface.");
            }
        }

        if (IBasicRobot.class.isAssignableFrom(robotClass)) {
            if (!(isAdvancedRobot || isJuniorRobot)) {
                isStandardRobot = true;
            }
        }
        return new RobotType(isJuniorRobot, isStandardRobot, isInteractiveRobot, isPaintRobot, isAdvancedRobot,
                             isTeamRobot, isDroid, isHouseRobot, isFreezeRobot, isBall, isBotzilla, isZombie, isDispenser, isMinion);
    	}

    private boolean checkMethodOverride(Class<?> robotClass, Class<?> knownBase, String name, Class<?>... parameterTypes) {
        if (knownBase.isAssignableFrom(robotClass)) {
            final Method getInteractiveEventListener;

            try {
                getInteractiveEventListener = robotClass.getMethod(name, parameterTypes);
            } catch (NoSuchMethodException e) {
                return false;
            }
            if (getInteractiveEventListener.getDeclaringClass().equals(knownBase)) {
                return false;
            }
        }
        return true;
    }
}
