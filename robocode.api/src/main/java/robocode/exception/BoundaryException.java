package robocode.exception;

/**
 * 
 * @author Laurence McLean 42373414
 *
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
