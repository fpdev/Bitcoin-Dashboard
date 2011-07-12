package simsecondaire.bitcoindashboard.service;

import simsecondaire.bitcoindashboard.entities.MarketData;

public abstract class MarketDataService {

	public abstract MarketData getBySymbol(String symbol);
	
	
}
