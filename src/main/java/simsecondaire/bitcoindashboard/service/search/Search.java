package simsecondaire.bitcoindashboard.service.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Abstract representation of a search. Contains a list of SearchQueries.
 * 
 * @author FP
 * 
 */
public class Search {

	private List<SearchQuery<?, ?>> queries;
	private Set<String> domains;

	/**
	 * Creates an empty search with an empty list of queries.
	 */
	public Search() {
		this.queries = new LinkedList<SearchQuery<?, ?>>();
		this.domains = new HashSet<String>();
	}

	/**
	 * Create a search.
	 * 
	 * @param string
	 *            Search string
	 * @param domains
	 *            valid domains, must not contain null
	 */
	public Search(String string, Set<String> domains) {
		this.queries = parse(string, domains);
		this.domains = domains;
	}

	/**
	 * Parses a search string and returns a list of {@link SearchQuery}s.
	 * 
	 * @param string
	 *            Search string
	 * @param domains
	 *            valid domains, must not contain null; a domain should not
	 *            contain another domain as a substring, the behaviour for this
	 *            is not defined
	 * @return a list of {@link SearchQuery}s or an empty list if none can be
	 *         detected
	 */
	private List<SearchQuery<?, ?>> parse(String string, Set<String> domains) {

		List<SearchQuery<?, ?>> queries = new LinkedList<SearchQuery<?, ?>>();

		StringTokenizer tokenizer = new StringTokenizer(string);

		while (tokenizer.hasMoreTokens()) {

			String token = tokenizer.nextToken();
			String domain = null;
			SearchInstructionSymbol instruction = null;
			List<String> values = new LinkedList<String>();

			// look for prefixes
			for (String p : domains) {
				if (token.startsWith(p)) {
					domain = p;
					token = token.substring(p.length());
					// remove the domain we already know it
					break;
				}
			}

			// look for instructions
			for (SearchInstructionSymbol instr : SearchInstructionSymbol
					.values()) {
				if (token.startsWith(instr.getString())) {
					instruction = instr;
					// remove from token
					token = token.substring(instr.getString().length());

					break;
				}
			}

			// look for values (might have sub-tokens (but without prefixes or
			// instructions, the domain and instructions apply to all
			// sub-tokens))

			if (token.startsWith(SearchStructureSymbol.BOUNDS_MARKER
					.getString())) {

				token = token.substring(1); // drop the leading BOUNDS_MARKER

				// check if this token already contains the end marker
				if (token.endsWith(SearchStructureSymbol.BOUNDS_MARKER
						.getString())) {
					token = token.substring(0, token.length() - 1); // drop the
																	// trailing
																	// BOUNDS_MARKER
					values.add(token);
				} else {
					values.add(token);
					// look for the end
					while (tokenizer.hasMoreTokens()) {
						token = tokenizer.nextToken();
						if (token.endsWith(SearchStructureSymbol.BOUNDS_MARKER
								.getString())) {
							token = token.substring(0, token.length() - 1); // drop
																			// the
																			// trailing
																			// BOUNDS_MARKER
							values.add(token);
							break;
						} else {
							values.add(token);
						}
					}
				}

			} else {
				values.add(token);
			}

			if (instruction == SearchInstructionSymbol.FUZZY) {
				for (String val : values) {
					queries.add(new SearchQuery<String, String>(domain,
							instruction, val));
				}
			} else {
				StringBuilder builder = new StringBuilder();
				Iterator<String> iterator = values.iterator();
				while (iterator.hasNext()) {
					String s = iterator.next();
					builder.append(s);
					if (iterator.hasNext()) {
						builder.append(SearchStructureSymbol.DELIMETER);
					}
				}
				queries.add(new SearchQuery<String, String>(domain, instruction, builder
						.toString()));
			}

		}

		return queries;

	}

	/**
	 * Rebuilds the search with the given string. Reuses the prefixes defined on
	 * creation.
	 * 
	 * @param string
	 *            search string
	 */
	public void update(String string) {
		this.queries = parse(string, this.domains);
	}

	/**
	 * Replaces a query by a new one. If the old query does not exist, nothing
	 * happens.
	 * 
	 * @param oldQuery
	 *            the old query to repclae
	 * @param newQuery
	 *            the new query to pelace the old one with
	 */
	public void replaceQuery(SearchQuery<?,?> oldQuery, SearchQuery<?,?> newQuery) {
		if (this.queries.contains(oldQuery)) {
			this.queries.set(this.queries.indexOf(oldQuery), newQuery);
		}
	}

	/**
	 * Deletes all queries with the given symbol and any instruction symbol and
	 * inserts the given query at the end. The query will be inserted this way
	 * or the other. This method ensures that no other query with the same
	 * symbol exists. Queries which have no symbol defined will be ignored.
	 * 
	 * @param domain
	 *            the symbol to delete the queries
	 * @param query
	 *            the query to insert at the end
	 */
	public void replaceValuesByDomain(String domain, SearchQuery<?,?> query) {
		deleteQueriesByDomain(domain);
		this.queries.add(query);
	}

	/**
	 * Removes all queries for the given symbol.
	 * 
	 * @param domain
	 *            the symbol to delete the queries
	 */
	public void deleteQueriesByDomain(String domain) {
		ListIterator<SearchQuery<?,?>> iterator = this.queries.listIterator();
		while (iterator.hasNext()) {
			SearchQuery<?,?> q = iterator.next();
			if (q.getSymbol() != null && q.getSymbol().equals(domain)) {
				iterator.remove();
			}
		}
	}

	/**
	 * Return a list of {@link SearchQueries} on which this Search is based.
	 * 
	 * @return a list of {@link SearchQueries} on which this Search is based
	 */
	public List<SearchQuery<?,?>> getQueries() {
		return this.queries;
	}

	/**
	 * Gets all queries for a special symbol.
	 * 
	 * @param domain
	 *            the symbol to identify the queries
	 * @return a list of queries for the given symbol (!= null)
	 */
	public List<SearchQuery<?,?>> getQueriesByDomain(String domain) {
		List<SearchQuery<?,?>> list = new LinkedList<SearchQuery<?,?>>();
		for (SearchQuery<?,?> query : this.queries) {
			if (query.getSymbol() != null && query.getSymbol().equals(domain)) {
				list.add(query);
			}
		}
		return list;
	}

	/**
	 * Removes the first occurrence of the specified query from this Search, if
	 * it is present (optional operation). If this list does not contain the
	 * element, it is unchanged. More formally, removes the element with the
	 * lowest index i such that (o==null ? get(i)==null : o.equals(get(i))) (if
	 * such an element exists). Returns true if this list contained the
	 * specified element (or equivalently, if this list changed as a result of
	 * the call).
	 * 
	 * @param query
	 *            the query to remove
	 * @return true if this list contained the specified element
	 */
	public boolean removeQuery(SearchQuery<?,?> query) {
		return this.queries.remove(query);
	}

	/**
	 * Appends the specified query to the end of this search (optional
	 * operation).
	 * 
	 * @param query
	 *            query to be added
	 * @return true if this search changed as a result of the call
	 */
	public boolean addQuery(SearchQuery<?,?> query) {
		return this.queries.add(query);
	}

	/**
	 * Return the search-string that is equivalent to this search.
	 * 
	 * @return the search-string that is equivalent to this search
	 */
	public String getString() {
		StringBuilder builder = new StringBuilder();
		for (SearchQuery<?,?> query : this.queries) {
			if (query.getSymbol() != null) {
				builder.append(query.getSymbol());
			}
			if (query.getInstructionSymbol() != null) {
				builder.append(query.getInstructionSymbol());
			}
//			if (query.getSymbol() == null && query.isBareToken()) {
				builder.append(query.getValue());
//			} else {
//				builder.append(SearchStructureSymbol.BOUNDS_MARKER.getString()
//						+ query.getValue()
//						+ SearchStructureSymbol.BOUNDS_MARKER.getString());
//			}
			builder.append(SearchStructureSymbol.DELIMETER);
		}
		return builder.toString().trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((domains == null) ? 0 : domains.hashCode());
		result = prime * result + ((queries == null) ? 0 : queries.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Search)) {
			return false;
		}
		Search other = (Search) obj;
		if (domains == null) {
			if (other.domains != null) {
				return false;
			}
		} else if (!domains.equals(other.domains)) {
			return false;
		}
		if (queries == null) {
			if (other.queries != null) {
				return false;
			}
		} else if (!queries.equals(other.queries)) {
			return false;
		}
		return true;
	}

}