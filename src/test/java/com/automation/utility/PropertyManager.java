/*
 * Returns value from config file or from variable jenkins
 */
package com.automation.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.automation.base.SerenityBase;

public class PropertyManager extends SerenityBase {
	private static PropertyManager _instance = null;
	public Properties testData;
	public InputStream fileConfig = null;

	public PropertyManager() {

		try {
			this.testData = new Properties();
			this.fileConfig = getClass().getClassLoader()
					.getResourceAsStream("configFiles/propertiesFile/config.properties");
			if (this.fileConfig != null) {
				this.testData.load(this.fileConfig);
				this.fileConfig.close();
			} else {
				APP_LOG.info("Error on reading config file");
			}

		}

		catch (IOException e)

		{
			APP_LOG.error("Error" + e);
		}

		finally {
			if (this.fileConfig != null) {
				try {
					this.fileConfig.close();
				} catch (IOException e) {
					APP_LOG.error("Func:  Error occured while closing config file" + e);
				}
			}

		}

	}

	public static synchronized PropertyManager getInstance() {

		if (_instance == null) {
			_instance = new PropertyManager();
		}
		return _instance;
	}

	public String getValueForKey(String key) {
		return this.testData.getProperty(key);

	}

	public String valueFromConfig(String key) {

		try {
			if (System.getenv(key) == null) {
				System.out.println(" loaded value: " + this.getValueForKey(key) + " from Config properties");
				return this.getValueForKey(key);

			}

		} catch (NullPointerException | SecurityException e) {
			System.out.println("Error while getting value from config because of :-" + e);

		}

		return null;
	}

}
