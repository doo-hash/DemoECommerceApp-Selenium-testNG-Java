package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.ProductPage;
import com.demoApp.pageObject.SearchProductPage;

public class ProductSearchTest extends BaseClass {
	
	@Test(priority = 1)
	public void searchProductTest() throws IOException {
		HomePage homePage = new HomePage(driver);
		
		logger.info("*****SearchProductTest Starts*****");

		String searchEntry = "Selene Yoga Hoodie";

		//Enter product name in search input
		homePage.setSearchInputData(searchEntry + Keys.ENTER);
		logger.info("Entered product name in search input and clicked enter!");
		
		logger.info("*****SearchProductTest Ends*****");
	}

	
	@Test(priority = 2)
	public void findSearchedEntryProductTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		SearchProductPage searchProductPage = new SearchProductPage(driver, wait);
		
		logger.info("*****FindSearchedEntryProductTest Starts*****");

		String searchEntry = "Selene Yoga Hoodie";
		
		String searchurl = "https://magento.softwaretestingboard.com/catalogsearch/result/?q=Selene+Yoga+Hoodie";		
		System.out.println(driver.getCurrentUrl());
		wait.until(d -> driver.getCurrentUrl().equals(searchurl));
		logger.info("Redirected to searched product page!");
				
		assertTrue(searchProductPage.searchValuePresent(searchEntry));
		
		searchProductPage.getSearchedProduct();
		assertTrue(searchProductPage.searchedProductPresent(searchEntry));
		logger.info("searched product found!");

		
		logger.info("*****FindSearchedEntryProductTest Ends*****");
	}
	
	
	@Test(priority = 3)
	public void goToProductPageTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

		SearchProductPage searchProductPage = new SearchProductPage(driver, wait);
		ProductPage productPage = new ProductPage(driver);
		
		logger.info("*****FindSearchedEntryProductTest Starts*****");

		String searchEntry = "Selene Yoga Hoodie";

		searchProductPage.clickAddToCartButton();
		logger.info("add to button clicked button clicked!");

		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/selene-yoga-hoodie.html"));
		logger.info(driver.getCurrentUrl());
		
		assertTrue(productPage.getProductName().contains(searchEntry));
		logger.info("Product page opened!");
		
		logger.info("*****FindSearchedEntryProductTest Ends*****");
	}
	
}
