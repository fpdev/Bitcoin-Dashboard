package simsecondaire.bitcoindashboard;

import simsecondaire.bitcoindashboard.service.alert.AlertService;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		System.setProperty("org.joda.money.CurrencyUnitDataProvider",
				"simsecondaire.bitcoindashboard.service.CustomCurrencyUnitDataProvider");
		
		AlertService service = AlertService.getInstance();
		service.start();

	}
}
