package simsecondaire.bitcoindashboard.service;

import java.io.File;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import simsecondaire.bitcoindashboard.service.ConfigurationService.Options;

/**
 * Does the localization of all the strings of the application.
 */
public class LocalizationService {

	/**
	 * Enum of locales which are supported by this application. Once the
	 * corresponding property-files have been added, a new entry may be added.
	 * 
	 * @author FP
	 */
	public static enum SupportedLocales {
		ENGLISH(Locale.ENGLISH, "English"), GERMAN(Locale.GERMAN, "Deutsch");

		private static final Logger LOGGER = Logger
				.getLogger(SupportedLocales.class);
		private Locale locale;
		private String tellingName;

		/**
		 * Sets the support for a special locale.
		 * 
		 * @param locale
		 *            the local which ist from now on supported
		 * @param tellingName
		 *            a nice name for the locale
		 */
		SupportedLocales(Locale locale, String tellingName) {
			this.locale = locale;
			this.tellingName = tellingName;
		}

		/**
		 * @return the actual Locale objet
		 */
		public Locale getLocale() {
			LOGGER.debug("get locale from SupportedLocale \"" + name() + "\": "
					+ this.toString());
			return this.locale;
		}

		/**
		 * Gets the supported locale.
		 * 
		 * @return the language of this SupportedLocale
		 */
		public String getLanguage() {
			return locale.getLanguage();
		}

		/**
		 * Gets the current country.
		 * 
		 * @return the country of this SupportedLocale
		 */
		public String getCountry() {
			return locale.getCountry();
		}

		/**
		 * Retrieve SupportedLocale by language.
		 * 
		 * @param language
		 *            the language of the locale to retreive.
		 * @return the first SupportedLocale that matches the given language;
		 *         null if no such Object exists
		 */
		public static SupportedLocales get(String language) {
			for (SupportedLocales supLoc : SupportedLocales.values()) {
				if (supLoc.getLanguage().equals(language)) {
					return supLoc;
				}
			}
			return null;
		}

		/**
		 * Retrieve SupportedLocale by language & country.
		 * 
		 * @param language
		 *            the language of the locale to retreive.
		 * @param country
		 *            the countryof the locale to retreive.
		 * @return the first SupportedLocale that matches the given language and
		 *         country; null if no such Object exists
		 */
		public static SupportedLocales get(String language, String country) {
			for (SupportedLocales supLoc : SupportedLocales.values()) {
				if (supLoc.getLanguage().equals(language)
						&& supLoc.getCountry().equals(country)) {
					return supLoc;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return tellingName;
		}

	}

	/**
	 * String used if a requested String cannot be retrieved and no alternative
	 * is provided.
	 */
	protected static String defaultFallbackString = "???E???";
	protected static SupportedLocales defaultFallbackLocale = SupportedLocales.GERMAN;
	private static final Logger LOGGER = Logger
			.getLogger(LocalizationService.class);
	private static LocalizationService instance = null;
	// bundle for static text (labels, titles, column headers etc.)
	private static ResourceBundle staticTextBundle;

	/**
	 * Private constructor; singleton pattern creates an instance with the
	 * Locale defined in the user config or - if no valid supported Locale is
	 * specified - the default supported locale as specified in
	 * {@link SupportedLocales}.
	 */
	private LocalizationService() {
		this(getDefaultLocale());
	}

	/**
	 * @return a default SupportedLocales (specified in config, fallback:
	 *         DEFAULT)
	 */
	public static SupportedLocales getDefaultLocale() {
		String language = ConfigurationService.getUserConfiguration()
				.getString(Options.LOCALE_LANGUAGE.getString());
		SupportedLocales supLoc = SupportedLocales.get(language);
		if (supLoc == null) {
			LOGGER.warn("loading locale from config failed: " + supLoc);
			supLoc = defaultFallbackLocale;
		}
		return supLoc;
	}

	/**
	 * Private constructor; singleton pattern.
	 * 
	 * @param locale
	 *            locale to be used for this LocalizationService
	 */
	private LocalizationService(SupportedLocales locale) {
		LOGGER.debug("instantiate LocalisationService with locale " + locale
				+ "; our current file system position is "
				+ (new File(".").getAbsolutePath()));
		LocalizationService.staticTextBundle = ResourceBundle.getBundle(
				"StaticTextBundle", locale.getLocale());
	}

	/**
	 * Initializes a LocalizationService-instance with the default supported
	 * locale. Overrides any previous initialization.
	 */
	public static void initialize() {
		instance = new LocalizationService();
	}

	/**
	 * Initializes a LocalizationService-instance with the specified locale.
	 * Overrides any previous initialization.
	 * 
	 * @param locale
	 *            the locale to be used
	 * @throws MissingResourceException
	 *             if localization-resources are missing
	 */
	public static void initialize(SupportedLocales locale) {
		instance = new LocalizationService(locale);
	}

	/**
	 * Destroy this LocalizationService. One has to call initialize again after
	 * calling this method.
	 */
	public static void destroy() {
		instance = null;
	}

	/**
	 * Returns a singleton instance. Creates it if required.
	 * 
	 * @return a singleton instance if localization-resources are missing
	 */
	public static LocalizationService getInstance() {
		LOGGER.debug("get instance");
		initialize();
		return instance;
	}

	/**
	 * @return the defaultFallbackString
	 */
	public static String getDefaultFallbackString() {
		return defaultFallbackString;
	}

	/**
	 * @param defaultFallbackString
	 *            the defaultFallbackString to set
	 */
	public static void setDefaultFallbackString(String defaultFallbackString) {
		LocalizationService.defaultFallbackString = defaultFallbackString;
	}

	/**
	 * Get the localized text to a given key.
	 * 
	 * @param key
	 *            String that represents a symbol for which a localized String
	 *            representation shall be retrieved
	 * @return a String representing the localized text or the
	 *         {@link LocalizationService#defaultFallbackString} if loading the
	 *         string failed
	 */
	public String getStaticText(String key) {
		return getStaticText(key, LocalizationService.defaultFallbackString);
	}

	/**
	 * Get the localized text for a given key. Return a fallback String if the
	 * requested one doesn't exist.
	 * 
	 * @param key
	 *            String that represents a symbol for which a localized String
	 *            representation shall be retrieved
	 * @param fallback
	 *            String returned if the requested String doesn't exist
	 * @return a String representing the localized text or the fallback String
	 *         if loading the string failed
	 */
	public String getStaticText(String key, String fallback) {
		String string;
		try {
			string = staticTextBundle.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.error("static text missing, fallback", e);
			string = fallback;
		}
		return string;
	}

}
