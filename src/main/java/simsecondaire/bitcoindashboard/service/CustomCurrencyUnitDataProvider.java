package simsecondaire.bitcoindashboard.service;

import java.util.LinkedList;

import org.joda.money.CurrencyUnitDataProvider;

import simsecondaire.bitcoindashboard.entities.Currency;

public class CustomCurrencyUnitDataProvider extends CurrencyUnitDataProvider {

	@Override
	protected void registerCurrencies() throws Exception {
		for (Currency c: Currency.values()) {
			registerCurrency(c.getCode(), -1, c.getDecimalPlaces(), new LinkedList<String>());
		}
	}

}
