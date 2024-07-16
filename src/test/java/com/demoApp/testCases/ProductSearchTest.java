package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LoginPage;
import com.demoApp.pageObject.LogoutSuccessPage;
import com.demoApp.pageObject.ProductPage;
import com.demoApp.pageObject.SearchProductPage;

public class ProductSearchTest extends BaseClass {
	
	
	@Test
	public void verifyProductSearchTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		
		logger.info("*************************VerifyProductSearchTest Starts**************************");

		logger.info("Verfiy product search test execution started...");
		logger.info(driver.getCurrentUrl());
		logger.info("URL opened!");

		HomePage homePage = new HomePage(driver);
		SearchProductPage searchProductPage = new SearchProductPage(driver, wait);
		ProductPage productPage = new ProductPage(driver);

		homePage.clickLoginElement();
		logger.info("login link clicked!");

		//navigated to login page
		LoginPage loginPage = new LoginPage(driver);
		
		wait.until(d -> loginPage.isUrl());
		logger.info(driver.getCurrentUrl());
		logger.info("login page opened!");
		
		//type Input values
		loginPage.setEmailInput("johnDrew@test.com");
		logger.info("Entered email address");
		loginPage.setPasswordInput("Password123");
		logger.info("Enter password");
				
		//click submit button
		loginPage.clickLoginButton();
		logger.info("login submit button clicked!");

		wait.until(d -> homePage.getWelcomeMessage().contains("John"));
		logger.info(driver.getCurrentUrl());
		
		logger.info("Checked username!");
		String userNameText = homePage.getWelcomeMessage();
		assertTrue(userNameText.contains("John Drew"));

		String searchEntry = "Selene Yoga Hoodie";
		//Enter product name in search input
		homePage.setSearchInputData(searchEntry + Keys.ENTER);
		logger.info("Entered product name in search input and clicked enter!");
		
		String searchurl = "https://magento.softwaretestingboard.com/catalogsearch/result/?q=Selene+Yoga+Hoodie";		
		System.out.println(driver.getCurrentUrl());
		wait.until(d -> driver.getCurrentUrl().equals(searchurl));
		logger.info("Redirected to searched product page!");
				
		assertTrue(searchProductPage.searchValuePresent(searchEntry));
		
		searchProductPage.getSearchedProduct();
		assertTrue(searchProductPage.searchedProductPresent(searchEntry));
		logger.info("searched product found!");

		searchProductPage.clickAddToCartButton();
		logger.info("add to button clicked button clicked!");

		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/selene-yoga-hoodie.html"));
		logger.info(driver.getCurrentUrl());
		
		assertTrue(productPage.getProductName().contains(searchEntry));
		logger.info("Product page opened!");
		
		//logout
		homePage.clickAccountDropdown();
		wait.until(d -> homePage.isDropdownVisible());
		logger.info("Account dropdown is open now!");
		
		//click logout link
		homePage.clickLogoutLink();
		logger.info("logged out!");

		//redirected to logout success page
		LogoutSuccessPage logoutSuccessPage = new LogoutSuccessPage(driver);
		logger.info(driver.getCurrentUrl());
		
		assertTrue(logoutSuccessPage.isSignoutMessagePresent());			

		//redirect to home page
		wait.until(d -> homePage.isUrl());
		logger.info(driver.getCurrentUrl());
		Assert.assertTrue(true);
		
		logger.info("*************************VerifyProductSearchTest Ends**************************");
		
	}


}
