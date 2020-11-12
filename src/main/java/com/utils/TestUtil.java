package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.base.TestBase;

public class TestUtil {
	public static Properties CONFIG = null;
	public static Logger APP_LOGS = null;
	private static String projectDir = System.getProperty("user.dir");

	/**
	 * Capture a screenshot and store it in .png format
	 */
	public static void takeScreenShot(String MethodName) {
		String executionMode = CONFIG.getProperty("executionMode");
		if (executionMode.equalsIgnoreCase("remote"))
			return;
		File scrFile = ((TakesScreenshot) TestBase.getDriver()).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(projectDir + "//Screenshots//" + MethodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the Logger
	 */
	public static Logger initializeLogger() {
		PropertyConfigurator.configure(projectDir + "//src//main//java//Config//log4j.properties");

		APP_LOGS = Logger.getLogger("WeatherLogger");
		APP_LOGS.debug("Initialized log file successfully!!");

		// to suppress warning message related to log4j initialization in system
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		return APP_LOGS;
	}

	/**
	 * Initializes the Configuration Property files
	 */
	public static Properties initConfig() {
		CONFIG = new Properties();
		String fileName = "config_env.properties";
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(projectDir + "//src//main//java//Config//" + fileName);
			CONFIG.load(inputStream);
		} catch (FileNotFoundException e1) {
			APP_LOGS.debug("Configuration file not found..");
		} catch (IOException e) {
			APP_LOGS.debug(e.getMessage());
		}
		return CONFIG;
	}

	/**
	 * Extract Number from a String
	 * 
	 * @param input String
	 * @return float, digits
	 */
	public static float getNumberOnly(String input) {
		String regex = "([0-9]+[.]*[0-9]*)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find())
			return Float.parseFloat(matcher.group());
		else
			return 0;
	}

}
