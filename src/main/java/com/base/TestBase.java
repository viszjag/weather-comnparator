package com.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import com.utils.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class TestBase {
	protected static String projectDir = System.getProperty("user.dir");

	protected static Logger APP_LOGS = null;
	public static Properties CONFIG = null;
	private static WebDriver driver = null;

	public TestBase() {
		initLogger();
		initConfig();
	}

	/**
	 * Initializes the Logger
	 */
	private void initLogger() {
		APP_LOGS = TestUtil.initializeLogger();
	}

	/**
	 * Initializes the Configuration Property files
	 */
	private void initConfig() {
		CONFIG = TestUtil.initConfig();
	}

	/**
	 * Initializes the browser driver
	 */
	public void initDriver(String browser) {
		String executionMode = CONFIG.getProperty("executionMode");
		if (executionMode.equalsIgnoreCase("local"))
			createLocalDriver(browser);
		else if (executionMode.equalsIgnoreCase("remote"))
			createRemoteDriver(browser);

		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	private void createLocalDriver(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			setDriver(new ChromeDriver());
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			setDriver(new FirefoxDriver());
		}
	}

	private void createRemoteDriver(String browser) {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("os", "Windows");
		caps.setCapability("os_version", "10");
		caps.setCapability("browser", browser);
		caps.setCapability("name", "Weather Report Tests");
		if (browser.equalsIgnoreCase("Chrome")) {
			try {
				setDriver(new RemoteWebDriver(new URL(generateRemoteURL()), caps));
			} catch (MalformedURLException e) {
				APP_LOGS.debug("Error in creating chrome remote driver..");
			}
		} else if (browser.equalsIgnoreCase("Firefox")) {
			try {
				setDriver(new RemoteWebDriver(new URL(generateRemoteURL()), caps));
			} catch (MalformedURLException e) {
				APP_LOGS.debug("Error in creating firefox remote driver..");
			}
		}
	}

	/**
	 * Generate remote url
	 * 
	 * @param URL, String
	 */
	private String generateRemoteURL() {
		String url = "";
		String userName = System.getenv("BROWSERSTACK_USERNAME");// set this variable on Jenkins
		if (userName == null)
			userName = CONFIG.getProperty("BROWSERSTACK_USERNAME");

		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");// set this variable on Jenkins
		if (accessKey == null)
			accessKey = CONFIG.getProperty("BROWSERSTACK_ACCESS_KEY");

		if (userName != null && accessKey != null)
			url = "https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
		return url;
	}

	/**
	 * Load a new web page in the current browser window.
	 * 
	 * @param URL
	 */
	protected void openURL(String URL) {
		getDriver().get(URL);
	}

	/**
	 * Quit driver to end Webdriver session
	 */
	protected void quitDriver() {
		getDriver().quit();
		setDriver(null);
		APP_LOGS.debug("Closing the browser..");
	}

	/**
	 * Log test result and take screenshot for failed test method
	 */
	public void testResult(ITestResult result) {
		APP_LOGS.debug("Method name: " + result.getMethod().getMethodName());
		APP_LOGS.debug("Success ?:" + result.isSuccess());
		if (!result.isSuccess()) {
			String methodName = result.getMethod().getMethodName();
			APP_LOGS.debug("Taking screen shot for " + methodName);
			TestUtil.takeScreenShot(methodName);
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		TestBase.driver = driver;
	}
}
