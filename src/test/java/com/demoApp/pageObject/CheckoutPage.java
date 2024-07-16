package com.demoApp.pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoApp.utilities.HighlightElementClass;

public class CheckoutPage {

	WebDriver driver;
	WebDriverWait wait;
	Actions actions;
	HighlightElementClass highlightElement = new HighlightElementClass();
	JavascriptExecutor executor;

	
	@FindBy(xpath = "(//div[contains(@class,'step-title')])[1]")
	WebElement shippingAddressText;
	
	@FindBy(xpath = "//div[contains(@class,'selected-item')]")
	WebElement shippingAddress;
	
	@FindBy(xpath = "//div[contains(@class,'opc-block-summary')]")
	WebElement orderSummary;
	
	@FindBy(xpath = "//div[contains(@class,'opc-block-summary')]//div[contains(@class,'title')]")
	WebElement orderListButton;
	
	@FindBy(xpath = "//div[contains(@class,'opc-block-summary')]//li[contains(@class,'product-item')]")
	WebElement productDetails;
	
	@FindBy(xpath = "//div[contains(@class,'opc-block-summary')]//div[contains(@class,'options')]")
	WebElement viewDetailsButton;
	
	@FindBy(xpath = "//button[contains(@class,'continue')]")
	WebElement nextButton;
	
	
	//Constructor
	public CheckoutPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = (JavascriptExecutor) driver;

		PageFactory.initElements(driver, this);
	}
	
	
	//wait until shipping address text is displayed
	public void waitUntilShippingAddressVisible() {
		wait.until(d -> shippingAddressText.isDisplayed());
		highlightElement.highlightElement(driver, shippingAddressText);
	}
	
	//check shipping address is displayed
	public boolean isShippingAddressDisplayed() {
		highlightElement.highlightElement(driver, shippingAddress);
		return shippingAddress.isDisplayed();
	}

	//click to check orders
	public void clickToSeeOrders() {
		highlightElement.highlightElement(driver, orderSummary);
		highlightElement.highlightElement(driver, orderListButton);
		orderListButton.click();
		executor.executeScript("window.scrollBy(0,50);");
		viewDetailsButton.click();
		highlightElement.highlightElement(driver, productDetails);
	}
	
	//click to check orders
	public void clickNextButton() {
		executor.executeScript("window.scrollBy(0,100);");
		wait.until(d -> nextButton.isDisplayed());
		highlightElement.highlightElement(driver, nextButton);
		nextButton.click();
	}
}

