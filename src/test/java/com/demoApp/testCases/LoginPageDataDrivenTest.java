package com.demoApp.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demoApp.pageObject.HomePage;
import com.demoApp.pageObject.LoginPage;
import com.demoApp.pageObject.LogoutSuccessPage;
import com.demoApp.utilities.ReadExcelFile;

public class LoginPageDataDrivenTest extends BaseClass {
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
	
	@Test(dataProvider = "LoginDataProvider")
	public void verifyLoginTest(String email, String password, String welcomeName) throws IOException, InterruptedException {

		logger.info("*************************VerifyLogin TestCase Starts**************************");

		String homeUrl = driver.getCurrentUrl();
		logger.info(homeUrl);
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
		loginPage.setEmailInput(email);
		logger.info("Entered email address");
		loginPage.setPasswordInput(password);
		logger.info("Enter password");
				
		String emailInput = loginPage.getEmailInputValue();
		String psdInput = loginPage.getPasswordInputValue();
		
		//click submit button
		loginPage.clickLoginButton();
		logger.info("login submit button clicked!");

		if(emailInput.isBlank() || psdInput.isBlank()) {
			logger.info("Login with Empty Credntials");
			wait.until(d -> loginPage.isEmailErrorPresent());
			
			String emailErr = loginPage.getEmailErrorMessage();
			String psdErr = loginPage.getPasswordErrorMessage();
		
			System.out.println(emailErr);
			System.out.println(psdErr);
			
			logger.info("Email and Password required fields message is visible!");			
			
			//back to home page
			driver.get(homeUrl);
			
		}
		else if (loginPage.getErrorMessage().length() != 0) {

			logger.info("Login with invalid Credntials");
			
			String errMsg = loginPage.getErrorMessage();
			System.out.println(errMsg);
			
			logger.info("Error message is visible");
			
			//back to home page
			driver.get(homeUrl);
		}
		else {
//			Thread.sleep(5000);
			wait.until(d -> homePage.getWelcomeMessage().contains(welcomeName));
			logger.info(driver.getCurrentUrl());
			
			String userNameText = homePage.getWelcomeMessage();
			
			if(userNameText.contains(welcomeName)) {
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
		}
		

		logger.info("*************************VerifyLoginTest Ends**************************");
		
	}
	

	@DataProvider(name = "LoginDataProvider")
	public String[][] loginDataProvider() throws IOException{
		String fileName = System.getProperty("user.dir") + "\\TestData\\loginTestData.xlsx";
		
		int rows = ReadExcelFile.getRowCount(fileName, "LoginTestData");
		int columns = ReadExcelFile.getColumnCount(fileName, "LoginTestData");
		
		String[][] testData = new String[rows - 1][columns];
		
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				testData[i-1][j] = ReadExcelFile.getCellValue(fileName, "LoginTestData", i, j);
			}
		}
		
		return testData;
	}
	
	
}
