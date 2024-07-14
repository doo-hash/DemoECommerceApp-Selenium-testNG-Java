package com.demoApp.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demoApp.utilities.HighlightElementClass;

public class CreateAccountPage {

	WebDriver driver;
	HighlightElementClass highlightElementClass = new HighlightElementClass();

	@FindBy(id = "firstname") WebElement firstNameInput;
	@FindBy(id = "lastname") WebElement lastNameInput;
	@FindBy(name = "email") WebElement emailInput;
	
	@FindBy(xpath = "//div[contains(@class,'password')]//input[contains(@id,'password')]") 
	WebElement passwordInput;

	@FindBy(name = "password_confirmation")
	WebElement confirmPasswordInput;

	@FindBy(className = "submit")
	WebElement createAccountButton;
	
	//constructor
	public CreateAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//set firstname input value
	public void setFirstNameInput(String firstName) {
		highlightElementClass.highlightElement(driver, firstNameInput);
		firstNameInput.sendKeys(firstName);
	}

	//set lastname input value
	public void setLastNameInput(String lastName) {
		highlightElementClass.highlightElement(driver, lastNameInput);
		lastNameInput.sendKeys(lastName);
	}

	//set email input value
	public void setEmailInput(String email) {
		highlightElementClass.highlightElement(driver, emailInput);
		emailInput.sendKeys(email);
	}

	//set password input value
	public void setPasswordInput(String password) {
		highlightElementClass.highlightElement(driver, passwordInput);
		passwordInput.sendKeys(password);
	}

	//set password-confirmation input value
	public void setConfirmPasswordInput(String confirmPassword) {
		highlightElementClass.highlightElement(driver, confirmPasswordInput);
		confirmPasswordInput.sendKeys(confirmPassword);
	}
	
	//get firstname input value
	public String getFirstNameInputValue() {
		return firstNameInput.getDomProperty("value");
	}

	//get lastname input value
	public String getLastNameInputValue() {
		return lastNameInput.getDomProperty("value");
	}

	//get email input value
	public String getEmailInputValue() {
		return emailInput.getDomProperty("value");
	}

	//get password input value
	public String getPasswordInputValue() {
		return passwordInput.getDomProperty("value");
	}

	//get password-confirmation input value
	public String getConfirmPasswordInputValue() {
		return confirmPasswordInput.getDomProperty("value");
	}

	//click create account button
	public void clickCreateAccountButton() {
		highlightElementClass.highlightElement(driver, createAccountButton);
		createAccountButton.click();
	}
	

}
