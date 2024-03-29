/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen & Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.battle.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.robocode.io.Logger;
import static net.sf.robocode.io.Logger.logError;
import robocode.control.events.*;

/**
 * @author Flemming N. Larsen (original)
 * @author Pavel Savara (original)
 * @since 1.6.1
 */
public class BattleEventDispatcher implements IBattleListener {
    // This list is guaranteed to be thread-safe, which is necessary as it will be accessed
    // by both the battle thread and battle manager thread. If this list is not thread-safe
    // then ConcurentModificationExceptions will occur from time to time.

    private final List<IBattleListener> listeners = new CopyOnWriteArrayList<IBattleListener>();

    public BattleEventDispatcher() {
    }

    public void addListener(IBattleListener listener) {
        assert (listener != null);

        listeners.add(listener);
    }

    public void removeListener(IBattleListener listener) {
        assert (listener != null);
        listeners.remove(listener);
    }

    @Override
    public void onBattleStarted(BattleStartedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattleStarted(event);
            } catch (Throwable ex) {
                logError("onBattleStarted " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onBattleCompleted(BattleCompletedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattleCompleted(event);
            } catch (Throwable ex) {
                logError("onBattleCompleted " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onBattleFinished(BattleFinishedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattleFinished(event);
            } catch (Throwable ex) {
                logError("onBattleFinished " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onBattlePaused(BattlePausedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattlePaused(event);
            } catch (Throwable ex) {
                logError("onBattlePaused " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onBattleResumed(BattleResumedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattleResumed(event);
            } catch (Throwable ex) {
                logError("onBattleResumed " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onRoundStarted(RoundStartedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onRoundStarted(event);
            } catch (Throwable ex) {
                logError("onRoundStarted " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onRoundEnded(event);
            } catch (Throwable ex) {
                logError("onRoundEnded " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onTurnStarted(TurnStartedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onTurnStarted(event);
            } catch (Throwable ex) {
                logError("onTurnStarted " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onTurnEnded(event);
            } catch (Throwable ex) {
                logError("onTurnEnded " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onBattleMessage(BattleMessageEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattleMessage(event);
            } catch (Throwable ex) {
                logError("onBattleMessage " + listener.getClass(), ex);
            }
        }
    }

    @Override
    public void onBattleError(BattleErrorEvent event) {
        for (IBattleListener listener : listeners) {
            try {
                listener.onBattleError(event);
            } catch (Throwable ex) {
                Logger.realErr.println(listener.getClass() + " " + ex.getMessage());
            }
        }
    }
}
