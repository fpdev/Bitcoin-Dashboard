/**
 * 
 */
package simsecondaire.bitcoindashboard.service.alert;

/**
 * @author FP
 *
 */
public class AlertActionException extends Exception {

	private static final long serialVersionUID = 7158747685296429020L;

	/**
	 * 
	 */
	public AlertActionException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public AlertActionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public AlertActionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AlertActionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
