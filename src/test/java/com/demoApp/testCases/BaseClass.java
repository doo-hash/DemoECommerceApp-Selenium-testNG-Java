package com.demoApp.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.demoApp.utilities.ReadConfig;

public class BaseClass {

	ReadConfig config = new ReadConfig();
	String baseUrl = config.getBaseURL();
	String browserName = config.getBrowserName();
	
	public static WebDriver driver;
	public static Logger logger;
	
	@BeforeClass
	public void setup() {
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default:
			driver = null;
			break;
		}
		
		//maximize the window
		driver.manage().window().maximize();

		//implicit wait of 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//open website with the given url
		driver.get(baseUrl);
		
		//logging
		logger = LogManager.getLogger("DemoECommerceApp-Selenium-testNG-Java");
	}
	
	@AfterClass
	public void tearDown() {
//		driver.quit();
	}

	//method to capture screenshot
	public void captureScreenshot(WebDriver driver, String testName) throws IOException {
		//convert webdriver object to takesScreenshot interface
		TakesScreenshot screenshot = ((TakesScreenshot)driver);
		
		//call getScreenshotAs method to create image file
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		
		File destFile = new File(System.getProperty("user.dir") + "//Screenshots//" + testName + ".png");
		
		//copy image file to destination
		FileUtils.copyFile(srcFile, destFile);
		
	}
}
