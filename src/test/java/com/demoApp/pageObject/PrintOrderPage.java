package com.demoApp.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PrintOrderPage {

	WebDriver driver;
	
	@FindBy(xpath = "(//button/span[contains(text(),'Print')])[2]")
	WebElement printButton;
	
	public PrintOrderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickPrintButton() {
		printButton.click();
	}
}
