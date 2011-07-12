package simsecondaire.bitcoindashboard.service.search;

/**
 * A symbol that instructs how to search.
 * 
 * @author FP
 * 
 */
public enum SearchInstructionSymbol {
	EQUALS("=="), FUZZY("~=");

	private String string;

	/**
	 * Sets the given prefix string as the a prefix meta token. Strips any
	 * whitespace and makes the string lower-case.
	 * 
	 * @param string
	 *            the prefix
	 */
	SearchInstructionSymbol(String string) {
		string = string.trim();
		string = string.replaceAll("[ \t\n\r\f]", "");
		string = string.toLowerCase();
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
