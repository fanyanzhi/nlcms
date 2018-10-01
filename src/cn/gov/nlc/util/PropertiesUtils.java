package cn.gov.nlc.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	private static Properties prop = new Properties();
	
	static {
		loadFile("/properties/configuration.properties");
	}

	public static boolean loadFile(String fileName) {
		try {
			prop.load(PropertiesUtils.class.getResourceAsStream(fileName));
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) {
		String s = PropertiesUtils.getPropertyValue("aleph");
		System.out.println(s);
	}
	
}
