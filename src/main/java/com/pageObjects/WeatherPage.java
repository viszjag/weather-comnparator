package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.base.PageBase;

/**
 * Class WeatherPage. Contains all methods and WebElements related to Weather
 * Page
 */
public class WeatherPage extends PageBase {

	public WeatherPage(WebDriver driver) {
		super(driver);
	}

	/** City search box */
	@FindBy(id = "searchBox")
	private WebElement citySearchBox;

	/** map canvas */
	@FindBy(id = "map_canvas")
	private WebElement mapCanvas;

	/**
	 * Search and pin city on weather map
	 *
	 * @param cityName
	 * @return boolean, return true if city is searched and pinned on map else false
	 */
	public boolean searchAndPinCity(String cityName) {
		waitForElementToBeVisible(mapCanvas, 10);
		return searchCity(cityName) && pinCity(cityName);
	}

	/**
	 * Search city on weather map
	 *
	 * @param cityName
	 * @return boolean, return true if city is searched successfully
	 */
	public boolean searchCity(String cityName) {
		citySearchBox = waitForElementToBeVisible(citySearchBox, 5);
		citySearchBox.clear();
		citySearchBox.sendKeys(cityName);
		return isPresentAndDisplayed(By.xpath("//*[@id='" + cityName + "']"));
	}

	/**
	 * Pin city on weather map
	 *
	 * @param cityName
	 * @return boolean, return true if city is pinned on map else false
	 */
	public boolean pinCity(String cityName) {
		waitInSeconds(5);// wait for map to load
		if (!driver.findElement(By.xpath("//*[@id='" + cityName + "']")).isSelected())
			driver.findElement(By.xpath("//*[@id='" + cityName + "']")).click();
		return isPresentAndDisplayed(By.xpath("//*[@id='map_canvas']//*[.='" + cityName + "']"));
	}

	/**
	 * Check if city is present on map with temperature information
	 *
	 * @param cityName
	 * @return boolean, return true if city is pinned on map with temperature
	 *         information else false
	 */
	public boolean cityTemperatureDisplayed(String cityName) {
		return isPresentAndDisplayed(By.xpath("//*[@title='" + cityName + "']/*[@class='temperatureContainer']"));
	}

	/**
	 * Check if city is present on map with temperature information
	 *
	 * @param cityName
	 * @return boolean, return true if city is pinned on map with temperature
	 *         information else false
	 */
	public boolean cityWeatherDetailsDisplayed(String cityName) {
		waitForElementToBePresent(By.xpath("//*[@class='outerContainer' and @title='" + cityName + "']"), 5).click();
		waitInSeconds(2);
		return isPresentAndDisplayed(By
				.xpath("//*[@id=\"map_canvas\"]//*[@class='leaflet-popup-content']/*[contains(.,'" + cityName + "')]"))
				&& isPresentAndDisplayed(By
						.xpath("//*[@id=\"map_canvas\"]//*[@class='leaflet-popup-content']/*[contains(.,'Condition')]"))
				&& isPresentAndDisplayed(
						By.xpath("//*[@id=\"map_canvas\"]//*[@class='leaflet-popup-content']/*[contains(.,'Wind')]"))
				&& isPresentAndDisplayed(By
						.xpath("//*[@id=\"map_canvas\"]//*[@class='leaflet-popup-content']/*[contains(.,'Humidity')]"))
				&& isPresentAndDisplayed(By.xpath(
						"//*[@id=\"map_canvas\"]//*[@class='leaflet-popup-content']/*[contains(.,'Temp in Degrees')]"))
				&& isPresentAndDisplayed(By.xpath(
						"//*[@id=\"map_canvas\"]//*[@class='leaflet-popup-content']/*[contains(.,'Temp in Fahrenheit')]"));
	}

	/**
	 * Get City weather info attributes by label
	 *
	 * @param attribute name
	 * @return String, both label and vzlue
	 */
	public String getCityWeatherInfo(String label) {
		return waitForElementToBePresent(
				By.xpath("//*[@id='map_canvas']//*[@class='leaflet-popup-content']//b[contains(.,'" + label + "')]"), 5)
				.getText().trim();
	}

}
