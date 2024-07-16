package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoApp.pageObject.CheckoutPage;
import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LoginPage;
import com.demoApp.pageObject.LogoutSuccessPage;
import com.demoApp.pageObject.ProductPage;
import com.demoApp.pageObject.SearchProductPage;

public class BuyProductTest extends BaseClass {
	
	
	@Test
	public void verifyBuyProductTest() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		
		logger.info("*************************VerifyBuyProductTest Starts**************************");

		logger.info("Verfiy buy product test execution started...");
		logger.info(driver.getCurrentUrl());
		logger.info("URL opened!");

		HomePage homePage = new HomePage(driver);
		SearchProductPage searchProductPage = new SearchProductPage(driver, wait);
		CheckoutPage checkoutPage = new CheckoutPage(driver, wait);
		
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

		searchProductPage.selectLSize();
		logger.info("selected size!");

		searchProductPage.selectProductColor();
		logger.info("selected color!");
		
		searchProductPage.clickAddToCartButton();
		logger.info("add to cart button clicked!");

		assertTrue(searchProductPage.isProductAddedToCartAlertPresent());
		logger.info("product added to cart alert message!");
		
		assertTrue(searchProductPage.iscartCountUpdated());
		logger.info("cart count updated!");

		searchProductPage.clickCartButton();
		logger.info("cart button clicked!");

		searchProductPage.clickCheckOutButton();
		logger.info("checkOut button clicked!");
		
		
		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/checkout/#shipping"));
		logger.info(driver.getCurrentUrl());
		logger.info("checkout page opened!");

		checkoutPage.waitUntilShippingAddressVisible();
		checkoutPage.isShippingAddressDisplayed();
		logger.info("shipping address selected!");
		
		checkoutPage.clickToSeeOrders();
		logger.info("order summary list!");
		
		checkoutPage.clickNextButton();
		logger.info("next button clicked!");
		
		assertTrue(checkoutPage.isBillingAddressSelected());
		logger.info("billing address selected!");
		
		checkoutPage.clickPlaceOrderButton();
		logger.info("place order button clicked!");
		
		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/checkout/onepage/success/"));
		logger.info(driver.getCurrentUrl());
		logger.info("order confirmation page opened!");
		
		
//		assertTrue(productPage.getProductName().contains(searchEntry));
//		logger.info("Product page opened!");
//		
//		//logout
//		homePage.clickAccountDropdown();
//		wait.until(d -> homePage.isDropdownVisible());
//		logger.info("Account dropdown is open now!");
//		
//		//click logout link
//		homePage.clickLogoutLink();
//		logger.info("logged out!");
//
//		//redirected to logout success page
//		LogoutSuccessPage logoutSuccessPage = new LogoutSuccessPage(driver);
//		logger.info(driver.getCurrentUrl());
//		
//		assertTrue(logoutSuccessPage.isSignoutMessagePresent());			
//
//		//redirect to home page
//		wait.until(d -> homePage.isUrl());
//		logger.info(driver.getCurrentUrl());
//		Assert.assertTrue(true);
		
		logger.info("*************************VerifyProductSearchTest Ends**************************");
		
	}


}
