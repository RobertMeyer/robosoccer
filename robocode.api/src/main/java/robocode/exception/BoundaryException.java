package robocode.exception;

/**
 * Exception thrown when there is a problem with Boundaries
 * @author Laurence McLean 42373414
 * @see HouseRobotBoundary
 */
public class BoundaryException extends Error {

	private static final long serialVersionUID = 1L;
	
	public BoundaryException() {
		super();
	}
	
	public BoundaryException(String message) {
		super(message);
	}
}
