package simsecondaire.bitcoindashboard.service.alert;

public interface AlertAction {

	void execute(AlertDescription instructions) throws AlertActionException;
	
}
