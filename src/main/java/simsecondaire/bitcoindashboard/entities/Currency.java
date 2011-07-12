package simsecondaire.bitcoindashboard.entities;


public enum Currency {
	EUR("EUR", "€", 2), BTC("BTC", "BTC", 3), SLL("SLL", "SLL", 2), USD("USD", "$", 2);

	private String code;
	private String symbol;
	private int decimalPlaces;

	Currency(String code, String symbol, int decimalPlaces) {
		this.code = code;
		this.symbol = symbol;
		this.decimalPlaces = decimalPlaces;
	}

	public String getCode() {
		return code;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	/**
	 * Tries to find a currency that matches the given code. Ignores case.
	 * @param code
	 * @return Currency with the given code (case ignored) or null if none found.
	 */
	public static Currency getByCode(String code) {
		// TODO (optional) improve performance using hash map
		for (Currency cur: Currency.values()) {
			if (cur.getCode().equals(code))
				return cur;
		}
		return null;
	}

	/**
	 * Returns the code of this currency.
	 */
	@Override
	public String toString() {
		return code;
	}
	
}
