package com.ui.tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.api.tests.Test_CityWeather;
import com.base.TestBase;
import com.pageObjects.NDTVHomePage;
import com.pageObjects.WeatherPage;
import com.utils.TestUtil;
import com.utils.WeatherComparator;

public class Test_AddCityWeather extends TestBase {

	private String city = CONFIG.getProperty("city");
	private String baseURL = CONFIG.getProperty("baseURL");
	WeatherPage weather = null;
	boolean resultFlag = false;

	@BeforeTest
	@Parameters("browser")
	public void initDriver(@Optional String browser) {
		if (browser == null)
			browser = CONFIG.getProperty("browser");
		super.initDriver(browser);
	}

	@Test(priority = 1)
	public void checkNavigatingToWeatherSection() {
		NDTVHomePage home = new NDTVHomePage(getDriver());

		APP_LOGS.debug("Launching website: " + baseURL);
		openURL(baseURL);
		home.closeAddIfPresent();
		home.navigateToNewsPage();
		home.closeNotificationConfirmation();
		Assert.assertTrue(home.isNavigatedToPage("https://www.ndtv.com"), "Error in opening NDTV home page");

		APP_LOGS.debug("Navigating to weather section..");
		home.navigateToPage("WEATHER");
		Assert.assertTrue(home.isNavigatedToPage("weather"), "Error in opening weather page..");
	}

	@Test(dependsOnMethods = { "checkNavigatingToWeatherSection" })
	public void searchAndPinCityOnMap() {
		APP_LOGS.debug("Search and pin city on map..");
		weather = new WeatherPage(getDriver());
		resultFlag = weather.searchAndPinCity(city);
		Assert.assertTrue(resultFlag, "Error in pinning city on weather map..");
	}

	@Test(dependsOnMethods = { "searchAndPinCityOnMap" })
	public void checkCityWeatherDetails() {
		APP_LOGS.debug("Check city on the map reveals the weather details..");
		resultFlag = weather.cityWeatherDetailsDisplayed(city);
		Assert.assertTrue(resultFlag, "Error in displaying city weather details..");
	}

	@Test(dependsOnMethods = { "searchAndPinCityOnMap" })
	public void compareUiAndAPITemperatures() {
		APP_LOGS.debug("Compare city UI and API temperature details..");
		WeatherComparator uiWC = new WeatherComparator();
		float tempInDegreesUI = TestUtil.getNumberOnly(weather.getCityWeatherInfo("Temp in Degrees"));
		uiWC.setTempInDegrees(tempInDegreesUI);
		WeatherComparator uiAPI = new WeatherComparator();
		Test_CityWeather.getCityWeather();
		float tempInDegreesAPI = Float.parseFloat(Test_CityWeather.response.get("list[0].main.temp").toString());
		uiAPI.setTempInDegrees(tempInDegreesAPI);
		float variance = Float.parseFloat(CONFIG.getProperty("temperatureVariance"));
		Assert.assertTrue(
				WeatherComparator.compareWithVariance(uiWC.getTempInDegrees(), uiAPI.getTempInDegrees(), variance),
				"UI and API temperatures are not within variance difference..");
	}

	@AfterTest
	private void tearDown() {
		quitDriver();
	}

	@AfterMethod
	public void testResult(ITestResult result) {
		super.testResult(result);
	}

}
