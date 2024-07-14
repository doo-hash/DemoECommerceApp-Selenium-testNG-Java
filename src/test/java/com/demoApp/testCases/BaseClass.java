package com.demoApp.testCases;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
		driver.quit();
	}
}
