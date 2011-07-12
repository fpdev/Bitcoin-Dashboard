package simsecondaire.bitcoindashboard.entities;

import java.util.Date;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import simsecondaire.bitcoindashboard.service.LocalizationService;

public class MarketData {

	protected String dataSource;

	protected String symbol;
	protected CurrencyUnit currencyUnit;

	protected Date lastTrade;
	protected long numOfTrades;

	protected BigMoney volume;
	protected BigMoney currencyVolume;

	protected BigMoney high;
	protected BigMoney low;
	protected BigMoney open;
	protected BigMoney close;
	protected BigMoney previousClose;

	protected BigMoney bid;
	protected BigMoney ask;

	/**
	 * @param symbol
	 * @param currency
	 */
	public MarketData(String symbol, CurrencyUnit currencyUnit) {
		super();
		this.symbol = symbol;
		this.currencyUnit = currencyUnit;
	}

	/**
	 * @param source
	 * @param symbol
	 * @param currency
	 * @param lastTrade
	 * @param numOfTrades
	 * @param volume
	 * @param currencyVolume
	 * @param high
	 * @param low
	 * @param open
	 * @param close
	 * @param previousClose
	 * @param bid
	 * @param ask
	 */
	public MarketData(String source, String symbol, CurrencyUnit currencyUnit,
			Date lastTrade, long numOfTrades, BigMoney volume,
			BigMoney currencyVolume, BigMoney high, BigMoney low,
			BigMoney open, BigMoney close, BigMoney previousClose,
			BigMoney bid, BigMoney ask) {
		super();
		this.symbol = symbol;
		this.currencyUnit = currencyUnit;
		this.lastTrade = lastTrade;
		this.numOfTrades = numOfTrades;
		this.volume = volume;
		this.currencyVolume = currencyVolume;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.previousClose = previousClose;
		this.bid = bid;
		this.ask = ask;
	}

	/**
	 * @return the source of the data
	 */
	public String getSource() {
		return dataSource;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the currencyUnit
	 */
	public CurrencyUnit getCurrencyUnit() {
		return currencyUnit;
	}

	/**
	 * @return the lastTrade
	 */
	public Date getLastTrade() {
		return lastTrade;
	}

	/**
	 * @return the numOfTrades
	 */
	public long getNumOfTrades() {
		return numOfTrades;
	}

	/**
	 * @return the volume
	 */
	public BigMoney getVolume() {
		return volume;
	}

	/**
	 * @return the currencyVolume
	 */
	public BigMoney getCurrencyVolume() {
		return currencyVolume;
	}

	/**
	 * @return the high
	 */
	public BigMoney getHigh() {
		return high;
	}

	/**
	 * @return the low
	 */
	public BigMoney getLow() {
		return low;
	}

	/**
	 * @return the open
	 */
	public BigMoney getOpen() {
		return open;
	}

	/**
	 * @return the close
	 */
	public BigMoney getClose() {
		return close;
	}

	/**
	 * @return the previousClose
	 */
	public BigMoney getPreviousClose() {
		return previousClose;
	}

	/**
	 * @return the bid
	 */
	public BigMoney getBid() {
		return bid;
	}

	/**
	 * @return the ask
	 */
	public BigMoney getAsk() {
		return ask;
	}

	/**
	 * A string representation of this object. In this case returns a list of
	 * the attributes (symbol, high, low etc.) and their values.
	 */
	@Override
	public String toString() {
		LocalizationService localizer = LocalizationService.getInstance();
		StringBuilder builder = new StringBuilder();

		builder.append(localizer.getStaticText("dataSource", "data source"));
		builder.append(": \n");
		builder.append(dataSource);

		builder.append(localizer.getStaticText("symbol", "symbol"));
		builder.append(": \n");
		builder.append(symbol);

		builder.append(localizer.getStaticText("high", "high"));
		builder.append(": \n");
		builder.append(high);

		builder.append(localizer.getStaticText("low", "low"));
		builder.append(": \n");
		builder.append(low);

		builder.append(localizer.getStaticText("lastTrade", "last trade"));
		builder.append(": \n");
		builder.append(lastTrade);
		
		builder.append(localizer.getStaticText("bid", "bid"));
		builder.append(": \n");
		builder.append(bid);
		
		builder.append(localizer.getStaticText("ask", "ask"));
		builder.append(": \n");
		builder.append(ask);
		
		builder.append(localizer.getStaticText("currencyUnit", "currency"));
		builder.append(": \n");
		builder.append(currencyUnit);

		builder.append(localizer.getStaticText("numOfTrades",
				"number of trades"));
		builder.append(": \n");
		builder.append(numOfTrades);

		builder.append(localizer.getStaticText("volume", "volume"));
		builder.append(": \n");
		builder.append(volume);

		builder.append(localizer.getStaticText("currencyVolume",
				"currency volume"));
		builder.append(": \n");
		builder.append(currencyVolume);

		builder.append(localizer.getStaticText("open", "open"));
		builder.append(": \n");
		builder.append(open);

		builder.append(localizer.getStaticText("close", "close"));
		builder.append(": \n");
		builder.append(close);

		builder.append(localizer.getStaticText("previousClose",
				"previous close"));
		builder.append(": \n");
		builder.append(previousClose);

		return builder.toString();
	}

}
