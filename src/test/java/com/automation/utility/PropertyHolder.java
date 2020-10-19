package com.automation.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PropertyHolder {

	static final Map<String, String> propertyMap = new HashMap<>();

	public static String getProperty(String attribute) {
		return propertyMap.get(attribute);
	}

	public static Set<Entry<String, String>> getAllProperty() {
		return propertyMap.entrySet();
	}

	public static void setProperty(String attribute, String value) {
		propertyMap.put(attribute, value);
	}

	public static void setProperties(Map<String, String> map) {
		propertyMap.putAll(map);
	}
	
	public static boolean booleanProperty(String attribute) {
		return propertyMap.containsKey(attribute);
	}
}