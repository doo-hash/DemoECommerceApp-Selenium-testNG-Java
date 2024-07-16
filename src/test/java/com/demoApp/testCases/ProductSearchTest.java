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
	
	
	@Test(priority = 1)
	public void clickLoginLinkTest() throws IOException {
		logger.info("*************************VerifyProductSearchTest Starts**************************");
		logger.info("Verfiy product search test execution started...");

		logger.info("*****ClickLoginLinkTest Starts*****");
		logger.info(driver.getCurrentUrl());
		logger.info("Home page opened!");

		HomePage homePage = new HomePage(driver);

		homePage.clickLoginElement();
		logger.info("login link clicked!");
		
		logger.info("*****ClickLoginLinkTest Ends*****");
	}
	
	
	@Test(priority = 2)
	public void enterValidLoginCredentialsTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		LoginPage loginPage = new LoginPage(driver);
		
		logger.info("*****EnterValidLoginCredentialsTest Starts*****");

		//navigated to login page		
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

		logger.info("*****EnterValidLoginCredentialsTest Ends*****");
	}
	
	@Test(priority = 3)
	public void searchProductTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		HomePage homePage = new HomePage(driver);
		
		logger.info("*****SearchProductTest Starts*****");

		wait.until(d -> homePage.getWelcomeMessage().contains("John"));
		logger.info(driver.getCurrentUrl());
		
		logger.info("Checked username!");
		String userNameText = homePage.getWelcomeMessage();
		assertTrue(userNameText.contains("John Drew"));

		String searchEntry = "Selene Yoga Hoodie";

		//Enter product name in search input
		homePage.setSearchInputData(searchEntry + Keys.ENTER);
		logger.info("Entered product name in search input and clicked enter!");
		
		logger.info("*****SearchProductTest Ends*****");
	}
	
	@Test(priority = 4)
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
	
	
	@Test(enabled = false)
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
	
	@Test(priority = 5)
	public void homePageLogoutTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		
		HomePage homePage = new HomePage(driver);

		logger.info("*****HomePageLogoutTest Starts*****");
		
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

		logger.info("*****HomePageLogoutTest Ends*****");

		logger.info("*************************VerifyProductSearchTest Ends**************************");
		
	}


}
