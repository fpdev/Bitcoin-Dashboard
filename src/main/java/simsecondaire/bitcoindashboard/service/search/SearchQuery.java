package simsecondaire.bitcoindashboard.service.search;

/**
 * An representation of a search query.
 * 
 * @author FP
 * @param R type of the return value from the domain
 * @param V type of the value that is compared to the values from the domain
 */
public class SearchQuery<R, V> {

	protected String domain;
	protected SearchInstructionSymbol instructionSymbol;
	protected V value;

	/**
	 * @param domain
	 *            domain, can be null if not specified
	 * @param instructionSymbol
	 *            how to be searched
	 * @param value
	 *            for what to search, must not be null
	 */
	public SearchQuery(String domain, SearchInstructionSymbol instructionSymbol,
			V value) {
		super();

		if (value == null) {
			throw new IllegalArgumentException(
					"the value of a search query must not be null");
		}

		this.domain = domain;
		this.instructionSymbol = instructionSymbol;
		this.value = value;
	}

	/**
	 * Return the symbol, might be null.
	 * @return the symbol, might be null
	 */
	public String getSymbol() {
		return domain;
	}

	/**
	 * Return the metaSymbol, might be null.
	 * @return the metaSymbol, might be null
	 */
	public SearchInstructionSymbol getInstructionSymbol() {
		return instructionSymbol;
	}

	/**
	 * Return the value, cannot be null.
	 * @return the value, cannot be null
	 */
	public V getValue() {
		return value;
	}

//	/**
//	 * 
//	 * @return whether theres only a value without
//	 *         {@link SearchStructureSymbol.DELIMETER} (ie. a single token) and
//	 *         no domain and no search instruction (meta symbol) specified
//	 */
//	public boolean isBareToken() {
//		return (domain == null && instructionSymbol == null && !value
//				.contains(SearchStructureSymbol.DELIMETER.getString()));
//	}

}
