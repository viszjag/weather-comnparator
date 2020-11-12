package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.base.PageBase;

/**
 * Class NDTVHomePage. Contains all methods and WebElements related to Home Page
 */
public class NDTVHomePage extends PageBase {

	public NDTVHomePage(WebDriver driver) {
		super(driver);
	}

	/** Top navigation more icon */
	By topNavMore = By.id("h_sub_menu");

	/** Back to NDTV.com button */
	By backToNdtvNews = By.xpath("//a[@href='https://www.ndtv.com/news']/img[@alt='back']");

	/**
	 * Navigate to Menu
	 *
	 * @param menuName
	 * @return pageURL
	 */
	public String navigateToPage(String menuName) {
		if (!isPresentAndDisplayed(By.linkText(menuName))) {
			waitForElementToBePresent(topNavMore, 10).click();
		}
		waitForElementToBePresent(By.linkText(menuName), 5).click();
		// return pageURL for validation
		return driver.getCurrentUrl();
	}

	/**
	 * Navigate to NDTV news page
	 *
	 */
	public void navigateToNewsPage() {
		if (isPresentAndDisplayed(backToNdtvNews))
			driver.findElement(backToNdtvNews).click();
	}
}
