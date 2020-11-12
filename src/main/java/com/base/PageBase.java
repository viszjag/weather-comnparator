package com.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Class PageBase. This Class will contain reusable methods across all pages
 */
public abstract class PageBase {

	/** The driver. */
	protected WebDriver driver = null;
	protected WebDriverWait wait = null;

	/**
	 * Instantiates a this.driver
	 * 
	 * @param driver
	 */
	public PageBase(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	/**
	 * Wrapper method to check WebElement is Present and Displayed
	 * 
	 * @param WebElement
	 */
	public boolean isPresentAndDisplayed(By locator) {
		try {
			WebElement element = waitForElementToBePresent(locator, 5);
			if (element != null)
				return element.isDisplayed();
			else
				return false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Wrapper method for WebDriverWait (ExpectedCondition = visibilityOf)
	 * 
	 * @param WebElement
	 * @param timeInSeconds
	 */
	public WebElement waitForElementToBeVisible(WebElement element, int timeInSeconds) {
		wait = new WebDriverWait(driver, timeInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wrapper method for WebDriverWait (ExpectedCondition =
	 * presenceOfElementLocated)
	 * 
	 * @param Locator
	 * @param timeInSeconds
	 */
	public WebElement waitForElementToBePresent(By locator, int timeInSeconds) {
		wait = new WebDriverWait(driver, timeInSeconds);
		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (NoSuchElementException e) {
			return null;
		} catch (TimeoutException e) {
			return null;
		}
	}

	/**
	 * Check whether page URL contains the page name
	 * 
	 * @param pageName
	 * @param true     if navigated to intended page else false
	 */
	public boolean isNavigatedToPage(String pageName) {
		if (driver.getCurrentUrl().toLowerCase().contains(pageName.toLowerCase()))
			return true;
		else
			return false;
	}

	/**
	 * Close Add if present
	 * 
	 */
	public void closeAddIfPresent() {
		if (waitForElementToBePresent(By.xpath("//img[contains(@src,'close.png')]"), 5) != null)
			driver.findElement(By.xpath("//img[contains(@src,'close.png')]")).click();
	}

	/**
	 * Close notification box
	 * 
	 */
	public void closeNotificationConfirmation() {
		if (waitForElementToBePresent(By.linkText("No Thanks"), 20) != null) {
			driver.findElement(By.linkText("No Thanks")).sendKeys(Keys.ESCAPE);// to stop infinite background pageload
			driver.findElement(By.linkText("No Thanks")).click();
		}
	}

	/**
	 * Wrapper method for Thread.sleep()
	 * 
	 * @param timeInSeconds the time in seconds
	 */
	public void waitInSeconds(int timeInSeconds) {
		try {
			Thread.sleep(timeInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
