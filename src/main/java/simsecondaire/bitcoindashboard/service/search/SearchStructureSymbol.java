package simsecondaire.bitcoindashboard.service.search;

/**
 * A symbol that provides information about the structure of a search string.
 * 
 * @author FP
 * 
 */
public enum SearchStructureSymbol {
	DELIMETER(" "), BOUNDS_MARKER("\"");

	private String string;

	/**
	 * Sets the given string as the a meta token.
	 * 
	 * @param string
	 *            meta token
	 */
	SearchStructureSymbol(String string) {
		this.string = string;
	}

	/**
	 * Return the prefix-meta-string/token.
	 * @return the prefix-meta-string/token
	 */
	public String getString() {
		return string;
	}

	/**
	 * Return the prefix-meta-string/token.
	 * @return the prefix-meta-string/token
	 */
	@Override
	public String toString() {
		return string;
	}

}