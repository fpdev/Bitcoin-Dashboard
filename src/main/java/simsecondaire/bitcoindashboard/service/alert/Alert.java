package simsecondaire.bitcoindashboard.service.alert;

import java.util.List;

import simsecondaire.bitcoindashboard.service.search.Search;

public class Alert {

	private Search trigger;
	private List<AlertAction> actions;
	
	/**
	 * @param trigger
	 * @param actions
	 */
	public Alert(Search trigger, List<AlertAction> actions) {
		this.trigger = trigger;
		this.actions = actions;
	}

	/**
	 * @return the trigger
	 */
	public Search getTrigger() {
		return trigger;
	}

	/**
	 * @return the actions
	 */
	public List<AlertAction> getActions() {
		return actions;
	}
	
}
