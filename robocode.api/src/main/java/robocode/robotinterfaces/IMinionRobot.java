package robocode.robotinterfaces;

public interface IMinionRobot extends IBasicRobot {
	/**
	 * This method is called by the game to notify this robot about advanced
	 * robot event. Hence, this method must be implemented so it returns your
	 * {@link IAdvancedEvents} listener.
	 *
	 * @return listener to advanced events or {@code null} if this robot should
	 *         not receive the notifications.
	 * @since 1.6
	 */
	IAdvancedEvents getAdvancedEventListener();
}