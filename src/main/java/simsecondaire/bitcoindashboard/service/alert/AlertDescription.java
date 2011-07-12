package simsecondaire.bitcoindashboard.service.alert;

import java.util.Date;

import simsecondaire.bitcoindashboard.entities.MarketData;
import simsecondaire.bitcoindashboard.service.search.Search;

public class AlertDescription {

	private MarketData dataSnapshot;
	private Search trigger;
	private Date time;
	
	/**
	 * @param dataSnapshot
	 * @param trigger
	 * @param time
	 */
	public AlertDescription(MarketData dataSnapshot, Search trigger, Date time) {
		this.dataSnapshot = dataSnapshot;
		this.trigger = trigger;
		this.time = time;
	}

	/**
	 * @return the dataSnapshot
	 */
	public MarketData getDataSnapshot() {
		return dataSnapshot;
	}

	/**
	 * @return the trigger
	 */
	public Search getTrigger() {
		return trigger;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return time.toString() + ":\n\n" + dataSnapshot + "\n\n" + trigger; 
	}
	
}
