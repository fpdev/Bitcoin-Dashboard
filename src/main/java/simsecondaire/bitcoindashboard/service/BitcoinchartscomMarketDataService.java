package simsecondaire.bitcoindashboard.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.joda.money.MoneyException;

import simsecondaire.bitcoindashboard.entities.BitchartMarketData;
import simsecondaire.bitcoindashboard.entities.MarketData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class BitcoinchartscomMarketDataService extends MarketDataService
		implements BackgroundService {

	private class Loader extends Thread {

		private final Logger LOGGER = Logger.getLogger(Loader.class);
		private LocalizationService localizer;
		private URL url;
		private URLConnection connection;

		public Loader() {

			localizer = LocalizationService.getInstance();

			try {
				url = new URL("http://bitcoincharts.com/t/markets.json"); // TODO
																			// make
																			// configurable
				connection = url.openConnection();
				connection.setDoInput(true);
				connection.setUseCaches(false);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void run() {

			try {

				while (true) {

					try {

						BufferedReader reader = new BufferedReader(
								new InputStreamReader(
										connection.getInputStream()));

						StringBuilder string = new StringBuilder();
						String buffer;

						while ((buffer = reader.readLine()) != null) {
							string.append(buffer);
						}

						Gson gson = new Gson();
						JsonParser parser = new JsonParser();
						JsonArray array = parser.parse(string.toString())
								.getAsJsonArray();
						for (JsonElement elem : array) {
							BitchartMarketData marketData = gson.fromJson(elem,
									BitchartMarketData.class);
							LOGGER.debug("retrieved BitchartMarketData: "
									+ marketData);
							try {
								MarketData genericMarketData = marketData
										.toMarketData();
								symbolsDataMap.put(
										genericMarketData.getSymbol(),
										genericMarketData);
							} catch (MoneyException e) {
								// TODO Auto-generated catch block
								LOGGER.warn("unknown currency", e);
							}
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Thread.sleep(900000); // TODO make configurable

				}

			} catch (InterruptedException e) {
				// TODO
			}

		}

	}

	private static BitcoinchartscomMarketDataService instance;
	private Map<String, MarketData> symbolsDataMap;
	private Loader loader;
	private boolean started;

	private BitcoinchartscomMarketDataService() {
		symbolsDataMap = new ConcurrentHashMap<String, MarketData>();
		loader = new Loader();
	}

	public static BitcoinchartscomMarketDataService getInstance() {
		if (instance == null)
			instance = new BitcoinchartscomMarketDataService();
		return instance;
	}

	@Override
	public MarketData getBySymbol(String symbol) {
		return symbolsDataMap.get(symbol);
	}

	public void start() {
		synchronized (this) {
			if (!started) {
				loader.start();
				started = true;
			}
		}
	}

	public void stop() {
		synchronized (this) {
			if (started) {
				loader.interrupt();
				started = false;
			}
		}
	}

}
