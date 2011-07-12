package simsecondaire.bitcoindashboard.service;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ConfigurationService {

	public static enum Options {
		LOCALE_LANGUAGE("locale.language"), LOCALE_COUNTRY("locale.country");

		private String string;

		Options(String string) {
			this.string = string;
		}

		/**
		 * @return the string associated with this option
		 */
		public String getString() {
			return string;
		}

		/**
		 * @return the string associated with this option
		 */
		@Override
		public String toString() {
			return string;
		}

	}

	private static final Logger LOGGER = Logger
			.getLogger(ConfigurationService.class);
	private static PropertiesConfiguration defaultConfig = null;
	private static PropertiesConfiguration userConfig = null;
	private static ResourceLoader loader = new DefaultResourceLoader();

	private ConfigurationService() {

	}

	/**
	 * Retrieves a singleton instance of the default configuration.
	 * 
	 * @return a singleton instance of Configuration
	 */
	public static Configuration getDefaultConfiguration() {

		if (defaultConfig == null) {
			try {
				defaultConfig = new PropertiesConfiguration("config.properties");
			} catch (ConfigurationException e) {
				LOGGER.error("failed to load default config", e);
				defaultConfig = buildDefaultConfiguration();
			}
		}
		defaultConfig.setAutoSave(true);
		return defaultConfig;

	}

	/**
	 * Builds a default configuration with hardcoded values and tries to save
	 * it. Adds a log entry if saving the file failed.
	 * 
	 * @return default Configuration file
	 */
	private static PropertiesConfiguration buildDefaultConfiguration() {
		LOGGER.info("building default config");
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.setBasePath("");
		config.setFileName("config.properties");
		config.addProperty(Options.LOCALE_LANGUAGE.getString(), "de");
		config.addProperty(Options.LOCALE_COUNTRY.getString(), "AT");
		try {
			config.save();
		} catch (ConfigurationException e) {
			LOGGER.error("could not save default configuration", e);
		}
		return config;
	}

	/**
	 * Retrieves a singleton instance.
	 * 
	 * @return a singleton instance of Configuration
	 */
	public static Configuration getUserConfiguration() {
		if (userConfig == null) {
			try {
				userConfig = new PropertiesConfiguration(
						"userconfig.properties");
			} catch (ConfigurationException e) {
				LOGGER.error("failed to load user config", e);
				userConfig = buildUserConfiguration();
			}
		}
		userConfig.setAutoSave(true);
		return userConfig;
	}

	/**
	 * Builds a user configuration based on the default configuration and tries
	 * to save it. Adds a log entry if saving the file failed.
	 * 
	 * @return default Configuration file
	 */
	private static PropertiesConfiguration buildUserConfiguration() {
		LOGGER.info("building user config");
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.copy(getDefaultConfiguration());
		config.setBasePath("");
		config.setFileName("userconfig.properties");
		try {
			config.save("userconfig.properties");
		} catch (ConfigurationException e) {
			LOGGER.error("failed to save user configuration", e);
		}
		return config;
	}

	/**
	 * Tries to get a URL for a given path-string.
	 * 
	 * @param path
	 *            path
	 * @return the URL to locate the resource or null if no URL is found to
	 *         locate the resource
	 */
	@SuppressWarnings("unused")
	private URL locateResource(String path) {
		Resource res = loader.getResource(path);
		java.net.URL URL = null;
		try {
			URL = res.getURL();
			return URL;
		} catch (IOException e) {
			LOGGER.error("failed to lookup file " + path, e);
			return null;
		}
	}

}
