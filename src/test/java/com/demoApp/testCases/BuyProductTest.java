package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoApp.pageObject.CheckoutPage;
import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LoginPage;
import com.demoApp.pageObject.LogoutSuccessPage;
import com.demoApp.pageObject.OrderDetailsReceiptPage;
import com.demoApp.pageObject.OrderSuccessPage;
import com.demoApp.pageObject.PrintOrderPage;
import com.demoApp.pageObject.SearchProductPage;
import com.demoApp.utilities.ReadConfig;

public class BuyProductTest extends BaseClass {

	
	@Test
	public void clickLoginLinkTest() throws IOException {
		logger.info("*************************VerifyBuyProductTest Starts**************************");
		logger.info("Verfiy buy product test execution started...");

		logger.info("*****ClickLoginLinkTest Starts*****");
		logger.info(driver.getCurrentUrl());
		logger.info("Home page opened!");

		HomePage homePage = new HomePage(driver);

		homePage.clickLoginElement();
		logger.info("login link clicked!");
		
		logger.info("*****ClickLoginLinkTest Ends*****");
	}
	
	@Test
	public void loginSuccessTest() throws Exception {
		ReadConfig readConfig = new ReadConfig();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		
		logger.info("*******LoginSuccessTest Starts********");

		//navigated to login page		
		wait.until(d -> loginPage.isUrl());
		logger.info(driver.getCurrentUrl());
		logger.info("login page opened!");
		
		//type Input values
		loginPage.setEmailInput(readConfig.getUserEmail());
		logger.info("Entered email address");
		loginPage.setPasswordInput(readConfig.getPassword());
		logger.info("Enter password");
				
		//click submit button
		loginPage.clickLoginButton();
		logger.info("login submit button clicked!");
		
		//upon successful login, navigated back to home page
		wait.until(d -> homePage.getWelcomeMessage().contains("John"));
		logger.info(driver.getCurrentUrl());
		
		String userNameText = homePage.getWelcomeMessage();
		
		if(userNameText.contains(readConfig.getUserName())) {

			logger.info("verify login test -- passed");
		}else {
			logger.info("verify login test -- failed");
			captureScreenshot(driver, "loginSuccessTest");
			throw new Exception("User Name not found, user login failed!");
		}

		logger.info("*******LoginSuccessTest Ends********");

	}

	
	@Test
	public void searchProductTest() throws IOException {
		HomePage homePage = new HomePage(driver);
		
		logger.info("*****SearchProductTest Starts*****");

		String searchEntry = "Selene Yoga Hoodie";

		//Enter product name in search input
		homePage.setSearchInputData(searchEntry + Keys.ENTER);
		logger.info("Entered product name in search input and clicked enter!");
		
		logger.info("*****SearchProductTest Ends*****");
	}
	
	@Test
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
	
	
	@Test
	public void productAddToCartTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		SearchProductPage searchProductPage = new SearchProductPage(driver, wait);
		
		logger.info("*****ProductAddToCartTest Starts*****");

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
		
		logger.info("*****ProductAddToCartTest Ends*****");
	}
	
	
	@Test
	public void proceedToCheckoutTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		SearchProductPage searchProductPage = new SearchProductPage(driver, wait);
		
		logger.info("*****ProceedToCheckoutTest Starts*****");

		searchProductPage.clickCartButton();
		logger.info("cart button clicked!");

		searchProductPage.clickCheckOutButton();
		logger.info("checkOut button clicked!");
		
		logger.info("*****ProceedToCheckoutTest Ends*****");
	}
	
	
	@Test(dependsOnMethods = "loginSuccessTest")
	public void checkBillingAddressTest() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		CheckoutPage checkoutPage = new CheckoutPage(driver, wait);
		
		logger.info("*****CheckBillingAddressTest Starts*****");

		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/checkout/#shipping"));
		logger.info(driver.getCurrentUrl());
		logger.info("checkout page opened!");
		Thread.sleep(1000);

		checkoutPage.waitUntilShippingAddressVisible();
		checkoutPage.isShippingAddressDisplayed();
		logger.info("shipping address selected!");
		
		logger.info("*****CheckBillingAddressTest Ends*****");
	}
	

	@Test(dependsOnMethods = "loginSuccessTest")
	public void checkOrderDetailsTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		CheckoutPage checkoutPage = new CheckoutPage(driver, wait);
		
		logger.info("*****CheckOrderDetailsTest Starts*****");

		checkoutPage.clickToSeeOrders();
		logger.info("order summary list!");
		
		checkoutPage.clickNextButton();
		logger.info("next button clicked!");
		
		logger.info("*****CheckOrderDetailsTest Ends*****");
	}
	
	@Test(dependsOnMethods = {"loginSuccessTest", "checkOrderDetailsTest"})
	public void placeOrderTest() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		CheckoutPage checkoutPage = new CheckoutPage(driver, wait);
		
		logger.info("*****PlaceOrderTest Starts*****");

		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/checkout/#payment"));
		logger.info(driver.getCurrentUrl());
		
		Thread.sleep(2000);
		assertTrue(checkoutPage.isBillingAddressSelected());
		logger.info("billing address selected!");
		
		checkoutPage.clickPlaceOrderButton();
		logger.info("place order button clicked!");
		
		logger.info("*****PlaceOrderTest Ends*****");
	}
	
	
	@Test(dependsOnMethods = {"loginSuccessTest", "checkOrderDetailsTest","placeOrderTest"})
	public void orderSuccessandReceiptTest() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		OrderSuccessPage orderSuccessPage = new OrderSuccessPage(driver, wait);
		OrderDetailsReceiptPage orderDetailsReceiptPage = new OrderDetailsReceiptPage(driver, wait);

		String searchEntry = "Selene Yoga Hoodie";

		logger.info("*****OrderSuccessandReceiptTest Starts*****");

		wait.until(d -> driver.getCurrentUrl().equals("https://magento.softwaretestingboard.com/checkout/onepage/success/"));
		logger.info(driver.getCurrentUrl());
		logger.info("order confirmation page opened!");

		String orderNumber = orderSuccessPage.getOrderNumber();
		logger.info("order number is: " + orderNumber);
		
		orderSuccessPage.clickOrderNumber();
		logger.info("click order number link to get order details and receipt!");

		wait.until(d -> driver.getCurrentUrl().contains("https://magento.softwaretestingboard.com/sales/order/view/order_id/"));
		logger.info("product order receipt page opened!");

		String orderID = orderDetailsReceiptPage.getOrderID();
		assertTrue(orderID.contains(orderNumber));
		logger.info("product order id is same!");

		String orderDate = orderDetailsReceiptPage.getOrderDate();
		Calendar calendar = Calendar.getInstance();
		Date now = new Date();
		System.out.println(now);
		calendar.setTime(now);
		int date = calendar.DAY_OF_MONTH;
		int year = calendar.YEAR;
		String month = getMonthForInt(calendar.MONTH);
		String dateformat = month +" "+date+","+year;
		System.out.println(orderDate);
		System.out.println(dateformat);
		
		String productName = orderDetailsReceiptPage.getProductName();
		assertTrue(productName.contains(searchEntry));
		logger.info("product and search entry are same!");

		orderDetailsReceiptPage.scrollDownToOrederedItems();
		logger.info("scroll down to see order details!");

		orderDetailsReceiptPage.scrollDownToOrederInfo();
		logger.info("scroll down to see billing address!");

		logger.info("*****OrderSuccessandReceiptTest Ends*****");
	}
	
	
	@Test(dependsOnMethods = {"loginSuccessTest", "checkOrderDetailsTest","placeOrderTest","orderSuccessandReceiptTest"})
	public void printOrderTest() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		OrderDetailsReceiptPage orderDetailsReceiptPage = new OrderDetailsReceiptPage(driver, wait);

		logger.info("*****PrintOrderTest Starts*****");

		orderDetailsReceiptPage.clickPrintOrder();
		logger.info("clicked print order!");

		Object[] windowHandles = driver.getWindowHandles().toArray();
		
		driver.switchTo().window(windowHandles[1].toString());
		wait.until(d -> driver.getCurrentUrl().contains("https://magento.softwaretestingboard.com/sales/order/print/order_id/"));
		logger.info(driver.getCurrentUrl());
		logger.info("switched to print order page!");
		
		System.out.println(driver.getWindowHandles().toArray().length);
//		Object[] newWindowHandles = driver.getWindowHandles().toArray();
//		driver.switchTo().window(newWindowHandles[2].toString());
//		logger.info("switched to print page!");

//		PrintOrderPage printOrderPage = new PrintOrderPage(driver);				
//		printOrderPage.clickCancelButton();
//		logger.info("cancelled the order print!");

//		Object[] curentWindowHandles = driver.getWindowHandles().toArray();
//		driver.switchTo().window(curentWindowHandles[1].toString());
//		logger.info("switched to print order page!");
		
//		printOrderPage.scrollToBottom();
//		logger.info("scrolled to bottom of print order page!");


		driver.close();
		logger.info("closed print order page!");

		logger.info("*****PrintOrderTest Ends*****");
	}
	
	
	@Test(dependsOnMethods = {"loginSuccessTest", "checkOrderDetailsTest","placeOrderTest","orderSuccessandReceiptTest"})
	public void logoutTest() throws IOException, InterruptedException, ParseException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		
		HomePage homePage = new HomePage(driver);
		OrderDetailsReceiptPage orderDetailsReceiptPage = new OrderDetailsReceiptPage(driver, wait);

		logger.info("*****LogoutTest Starts*****");
		
		Object[] curentWindowHandles = driver.getWindowHandles().toArray();
		driver.switchTo().window(curentWindowHandles[0].toString());
				
		//logout
		orderDetailsReceiptPage.clickAccountDropdown();
		wait.until(d -> orderDetailsReceiptPage.isDropdownVisible());
		logger.info("Account dropdown is open now!");
		
		//click logout link
		orderDetailsReceiptPage.clickLogoutLink();
		logger.info("logged out!");

		//redirected to logout success page
		LogoutSuccessPage logoutSuccessPage = new LogoutSuccessPage(driver);
		logger.info(driver.getCurrentUrl());
		
		assertTrue(logoutSuccessPage.isSignoutMessagePresent());			

		//redirect to home page
		wait.until(d -> homePage.isUrl());
		logger.info(driver.getCurrentUrl());
		Assert.assertTrue(true);

		logger.info("*****LogoutTest Ends*****");
		
		logger.info("*************************VerifyProductSearchTest Ends**************************");
		
	}

	String getMonthForInt(int num) {
	    String month = "";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (num >= 0 && num <= 11) {
	        month = months[num];
	    }
	    return month;
	}

}
