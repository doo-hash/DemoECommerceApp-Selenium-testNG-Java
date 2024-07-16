package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.demoApp.pageObject.CreateAccountPage;
import com.demoApp.pageObject.CustomerPage;
import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LogoutSuccessPage;

public class CreateAccountPageTest extends BaseClass {
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
	
	@Ignore
	@Test
	public void createNewAccountTest() {

		logger.info("****************Verify Create Account Test with created user credentials Starts****************");

		SoftAssert softAssert = new SoftAssert();
		HomePage homePage = new HomePage(driver);
		homePage.clickCreateAccountElement();
		logger.info("URL opened!");

		//navigated to create account page
		CreateAccountPage createAccountPage = new CreateAccountPage(driver);
		
		wait.until(d -> createAccountPage.isUrl());
		logger.info(driver.getCurrentUrl());
		
		//type Input values
		createAccountPage.setFirstNameInput("John");
		createAccountPage.setLastNameInput("Drew");
		createAccountPage.setEmailInput("johnDrew@test.com");
		createAccountPage.setPasswordInput("Password123");
		createAccountPage.setConfirmPasswordInput("Password123");
		logger.info("creating account");
		
		System.out.println(createAccountPage.getFirstNameInputValue());
		System.out.println(createAccountPage.getLastNameInputValue());
		System.out.println(createAccountPage.getEmailInputValue());
		System.out.println(createAccountPage.getPasswordInputValue());
		System.out.println(createAccountPage.getConfirmPasswordInputValue());
		
		//click submit button
		createAccountPage.clickCreateAccountButton();
		logger.info("create account button clicked!");

		//upon successful registration, navigated to customer page
		CustomerPage customerPage = new CustomerPage(driver);
		wait.until(d -> customerPage.isMessageElementPresent());
		logger.info(driver.getCurrentUrl());
		logger.info("account created! You are in customer page right now.");

		//print message to console
		System.out.println(customerPage.getCreateAccountSuccessMessage());
		
		//check welcome message
		softAssert.assertTrue(customerPage.isWelcomeElementPresent());
		softAssert.assertTrue(customerPage.getWelcomeMessage().contains("John Drew"));
		System.out.println(customerPage.getWelcomeMessage());
		logger.info("Checking Welcome message!");

		//check contact info
		softAssert.assertTrue(customerPage.iscontactElementPresent());
		softAssert.assertTrue(customerPage.getContactInfo().contains("John Drew"));
		softAssert.assertTrue(customerPage.getContactInfo().contains("johnDrew@test.com"));
		System.out.println(customerPage.getContactInfo());
		logger.info("Checking contact information!");

		//click account dropdown
		customerPage.clickAccountDropdown();
		wait.until(d -> customerPage.isDropdownVisible());
		logger.info("Account dropdown is open now!");
		
		//click logout link
		customerPage.clickLogoutLink();
		logger.info("logged out!");

		//redirected to logout success page
		LogoutSuccessPage logoutSuccessPage = new LogoutSuccessPage(driver);
		wait.until(d -> logoutSuccessPage.isUrl());
		logger.info(driver.getCurrentUrl());
		
		assertTrue(logoutSuccessPage.isSignoutMessagePresent());
		assertTrue(logoutSuccessPage.isRedirectMessagePresent());
		
		System.out.println(logoutSuccessPage.getSignOutMessage());
		System.out.println(logoutSuccessPage.getRedirectMessage());

		//redirect to home page
		wait.until(d -> homePage.isUrl());
		logger.info(driver.getCurrentUrl());

		softAssert.assertAll();
		logger.info("****************Verify Create Account Test with new user credentials Ends****************");

	}
	
	
	@Test
	public void createNewAccountFailedTest() {

		logger.info("****************Verify Create Account Test with created user credentials Starts****************");

		logger.info(driver.getCurrentUrl());
		HomePage homePage = new HomePage(driver);
		homePage.clickCreateAccountElement();
		logger.info("URL opened!");

		//navigated to create account page
		CreateAccountPage createAccountPage = new CreateAccountPage(driver);
		
		wait.until(d -> createAccountPage.isUrl());
		logger.info(driver.getCurrentUrl());
		logger.info("create account page opened!");
		
		//type Input values
		createAccountPage.setFirstNameInput("John");
		createAccountPage.setLastNameInput("Drew");
		createAccountPage.setEmailInput("johnDrew@test.com");
		createAccountPage.setPasswordInput("Password123");
		createAccountPage.setConfirmPasswordInput("Password123");
		logger.info("creating account");
				
		//click submit button
		createAccountPage.clickCreateAccountButton();
		logger.info("create account button clicked!");

		//upon failed registration, failed message is displayed
		wait.until(d -> createAccountPage.isMessageElementPresent());
		logger.info("account creation failed!");

		//print message to console
		System.out.println(createAccountPage.getCreateAccountFailMessage());
		logger.info("****************Verify Create Account Test with created user credentials Ends****************");
				
	}

	
	
}
