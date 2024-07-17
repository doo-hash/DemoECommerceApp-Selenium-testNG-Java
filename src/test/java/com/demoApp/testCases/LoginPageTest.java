package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LoginPage;
import com.demoApp.pageObject.LogoutSuccessPage;
import com.demoApp.utilities.ReadConfig;

public class LoginPageTest extends BaseClass {
		
	@Test
	public void clickLoginInHomePageTest() throws IOException {
		HomePage homePage = new HomePage(driver);

		logger.info("*************************VerifyLoginTest Starts**************************");
		logger.info("Verfiy login test execution started...");
		
		logger.info("*******ClickLoginInHomePageTest Starts********");
		
		logger.info(driver.getCurrentUrl());
		logger.info("Home page opened!");

		homePage.clickLoginElement();
		logger.info("login link clicked!");

		logger.info("*******ClickLoginInHomePageTest Ends********");

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
	
	
	@Test(dependsOnMethods = {"loginSuccessTest"})
	public void logoutSuccessTest() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

		HomePage homePage = new HomePage(driver);
		LogoutSuccessPage logoutSuccessPage = new LogoutSuccessPage(driver);
		
		logger.info("*******LogoutSuccessTest Starts********");
		
		//logout
		homePage.clickAccountDropdown();
		wait.until(d -> homePage.isDropdownVisible());
		logger.info("Account dropdown is open now!");
		
		//click logout link
		homePage.clickLogoutLink();
		logger.info("logged out!");

		//redirected to logout success page
		wait.until(d -> logoutSuccessPage.isUrl());
		logger.info(driver.getCurrentUrl());
		logger.info("Redirected to logout success page!");
		
		assertTrue(logoutSuccessPage.isSignoutMessagePresent());			

		//redirect to home page
		wait.until(d -> homePage.isUrl());
		logger.info(driver.getCurrentUrl());
		logger.info("Redirected to home page!");

		logger.info("*******LogoutSuccessTest Ends********");

		logger.info("*************************VerifyLoginTest Ends**************************");

	}
	

}
