package simsecondaire.bitcoindashboard.entities;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.MoneyException;

public class BitchartMarketData {
	
	private static final Logger LOGGER = Logger.getLogger(BitchartMarketData.class);
	private static final String NAME = "bitcoincharts.com";
	
	private double previous_close;
	private double high;
	private long latest_trade;
	private double bid;
	private double volume;
	private String currency;
	private double low;
	private long n_trades;
	private double ask;
	private double close;
	private double open;
	private String symbol;
	private double currency_volume;
	
	public MarketData toMarketData() throws MoneyException {
		CurrencyUnit currencyUnit = CurrencyUnit.getInstance(this.currency);
		return new MarketData(BitchartMarketData.NAME, this.symbol, 
				currencyUnit, 
				new Date(this.latest_trade), 
				this.n_trades, 
				BigMoney.of(currencyUnit, this.volume), 
				BigMoney.of(currencyUnit, this.currency_volume), 
				BigMoney.of(currencyUnit, this.high), 
				BigMoney.of(currencyUnit, this.low), 
				BigMoney.of(currencyUnit, this.open), 
				BigMoney.of(currencyUnit, this.close), 
				BigMoney.of(currencyUnit, this.previous_close), 
				BigMoney.of(currencyUnit, this.bid), 
				BigMoney.of(currencyUnit, this.ask)
				);
	}
	
	@Override
	public String toString() {
		return "BitchartMarketData [previous_close=" + previous_close
				+ ", high=" + high + ", latest_trade=" + latest_trade
				+ ", bid=" + bid + ", volume=" + volume + ", currency="
				+ currency + ", low=" + low + ", n_trades=" + n_trades
				+ ", ask=" + ask + ", close=" + close + ", open=" + open
				+ ", symbol=" + symbol + ", currency_volume=" + currency_volume
				+ "]";
	}
	
}
