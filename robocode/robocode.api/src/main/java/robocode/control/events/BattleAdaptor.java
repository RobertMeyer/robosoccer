/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *******************************************************************************/
package robocode.control.events;

/**
 * An abstract adapter class for receiving battle events by implementing the {@link IBattleListener}.
 * The methods in this class are empty. This class exists as convenience for creating listener objects.
 * <p/>
 * This is handy class to use when implementing the IBattleListener.
 * It saves you from implementing empty handlers for battle events you are not interested in handling.
 * <p/>
 * <b>Example:</b>
 * <pre>
 *   private class BattleObserver extends BattleAdaptor {
 *       boolean isReplay;
 *
 *       public void onBattleStarted(BattleStartedEvent event) {
 *           isReplay = event.isReplay();
 *       }
 *
 *       public void onBattleCompleted(BattleCompletedEvent event) {
 *       if (!isReplay) {
 *           printResultsData(event);
 *       }
 *   }
 * </pre>
 *
 * @see IBattleListener
 *
 * @author Flemming N. Larsen (original)
 * @since 1.6.2
 */
public abstract class BattleAdaptor implements IBattleListener {

    /**
     * Creates a BattleAdaptor.
     */
    public BattleAdaptor() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattleStarted(final BattleStartedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattleFinished(final BattleFinishedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattleCompleted(final BattleCompletedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattlePaused(final BattlePausedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattleResumed(final BattleResumedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRoundStarted(final RoundStartedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRoundEnded(final RoundEndedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTurnStarted(final TurnStartedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTurnEnded(final TurnEndedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattleMessage(final BattleMessageEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBattleError(final BattleErrorEvent event) {
    }
}
