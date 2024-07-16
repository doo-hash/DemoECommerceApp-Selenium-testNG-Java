package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LoginPage;
import com.demoApp.pageObject.LogoutSuccessPage;

public class LoginPageTest extends BaseClass {
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
	
	@Test
	public void verifyLoginTest() throws IOException {

		logger.info("*************************VerifyLoginTest Starts**************************");

		logger.info("Verfiy login test execution started...");
		logger.info(driver.getCurrentUrl());
		logger.info("URL opened!");

		HomePage homePage = new HomePage(driver);
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
		
		String userNameText = homePage.getWelcomeMessage();
		
		if(userNameText.contains("John Drew")) {
			//upon successful login, navigated to customer page
			logger.info("verify login test -- passed");
			//logout
			homePage.clickAccountDropdown();
			wait.until(d -> homePage.isDropdownVisible());
			logger.info("Account dropdown is open now!");
			
			//click logout link
			homePage.clickLogoutLink();
			logger.info("logged out!");

			//redirected to logout success page
			LogoutSuccessPage logoutSuccessPage = new LogoutSuccessPage(driver);
			wait.until(d -> logoutSuccessPage.isUrl());
			logger.info(driver.getCurrentUrl());
			
			assertTrue(logoutSuccessPage.isSignoutMessagePresent());			

			//redirect to home page
			wait.until(d -> homePage.isUrl());
			logger.info(driver.getCurrentUrl());
			Assert.assertTrue(true);
		}else {
			logger.info("verify login test -- failed");
			captureScreenshot(driver, "verifyLoginTest");
			Assert.assertTrue(false);
		}
		logger.info("*************************VerifyLoginTest Ends**************************");
		
	}


}
