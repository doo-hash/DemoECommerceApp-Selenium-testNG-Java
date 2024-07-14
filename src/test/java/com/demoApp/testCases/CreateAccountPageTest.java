package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.demoApp.pageObject.CreateAccountPage;
import com.demoApp.pageObject.CustomerPage;
import com.demoApp.pageObject.HomePage;
import com.demoApp.utilities.HighlightElementClass;

public class CreateAccountPageTest extends BaseClass {
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	@Test
	public void createNewAccoutTest() {

		SoftAssert softAssert = new SoftAssert();
		HomePage homePage = new HomePage(driver);
		homePage.clickCreateAccountElement();
		logger.info("URL opened!");

		//navigated to create account page
		CreateAccountPage createAccountPage = new CreateAccountPage(driver);
		
		//type Input values
		createAccountPage.setFirstNameInput("John");
		createAccountPage.setLastNameInput("Drew");
		createAccountPage.setEmailInput("johnDrew@example.com");
		createAccountPage.setPasswordInput("Password123");
		createAccountPage.setConfirmPasswordInput("Password123");

		
		System.out.println(createAccountPage.getFirstNameInputValue());
		System.out.println(createAccountPage.getLastNameInputValue());
		System.out.println(createAccountPage.getEmailInputValue());
		System.out.println(createAccountPage.getPasswordInputValue());
		System.out.println(createAccountPage.getConfirmPasswordInputValue());
		
		//click submit button
		createAccountPage.clickCreateAccountButton();

		//upon successful registration, navigated to customer page
		CustomerPage customerPage = new CustomerPage(driver);
		wait.until(d -> customerPage.isMessageElementPresent());

		//print message to console
		System.out.println(customerPage.getCreateAccountSuccessMessage());
		
		//check welcome message
		softAssert.assertTrue(customerPage.isWelcomeElementPresent());
		softAssert.assertTrue(customerPage.getWelcomeMessage().contains("John Drew"));
		System.out.println(customerPage.getWelcomeMessage());
		
		//check contact info
		softAssert.assertTrue(customerPage.iscontactElementPresent());
		softAssert.assertTrue(customerPage.getContactInfo().contains("John Drew"));
		softAssert.assertTrue(customerPage.getContactInfo().contains("johnDrew@example.com"));
		System.out.println(customerPage.getContactInfo());
		
		softAssert.assertAll();
	}
}
