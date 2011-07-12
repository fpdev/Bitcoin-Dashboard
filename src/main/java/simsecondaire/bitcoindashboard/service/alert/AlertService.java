package simsecondaire.bitcoindashboard.service.alert;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import simsecondaire.bitcoindashboard.entities.MarketData;
import simsecondaire.bitcoindashboard.service.BackgroundService;
import simsecondaire.bitcoindashboard.service.BitcoinchartscomMarketDataService;

public class AlertService implements BackgroundService {

	private class Loader extends Thread {

		private BitcoinchartscomMarketDataService service;

		public Loader() {
			service = BitcoinchartscomMarketDataService.getInstance();
			service.start();
		}

		@Override
		public void run() {

			try {
				MarketData data = service.getBySymbol("mtgoxUSD");
				if (data != null && data.getLow().getAmount().doubleValue() < 16.0) { // TODO do not use null
					try {
						Desktop.getDesktop().open(new File("V:/bitcoin_alert.exe"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Thread.sleep(900000); // TODO make configurable
			} catch (InterruptedException e) {
				// TODO
			} 

		}

	}

	private static AlertService instance;
	private Loader loader;
	private List<Alert> alerts;

	private AlertService() {
		loader = new Loader();
		alerts = new LinkedList<Alert>();
	}
	
	public static AlertService getInstance() {
		if (instance == null) instance = new AlertService(); 
		return instance;
	}
	
	public void start() {
		loader.start();
	}

	public void stop() {
		loader.interrupt();
	}
	
	public void registerAlert(Alert alert) {
		this.alerts.add(alert);
	}

}
