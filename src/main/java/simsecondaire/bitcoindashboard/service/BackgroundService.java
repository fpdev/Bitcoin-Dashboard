package simsecondaire.bitcoindashboard.service;

/**
 * A service running in the background.
 * @author FP
 *
 */
public interface BackgroundService {

	/**
	 * Start this background service.
	 */
	void start();
	
	/**
	 * Stop this background service.
	 */
	void stop();
	
}
