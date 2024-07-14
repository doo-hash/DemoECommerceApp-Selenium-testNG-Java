package com.demoApp.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demoApp.utilities.HighlightElementClass;

public class HomePage {

	WebDriver driver;
	HighlightElementClass highlightElementClass = new HighlightElementClass();

	@FindBy(linkText = "Create an Account")
	WebElement createAccount;
	
	//constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//click create an account link
	public void clickCreateAccountElement() {
		highlightElementClass.highlightElement(driver, createAccount);
		createAccount.click();;
	}
	
}
