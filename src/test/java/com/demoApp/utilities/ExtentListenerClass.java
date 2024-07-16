package com.demoApp.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentListenerClass implements ITestListener {

	ExtentSparkReporter htmlReporter;
	ExtentReports reports;
	ExtentTest test;
	
	public void configureReport() {
		ReadConfig readConfig = new ReadConfig();
		String timestamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		String reportName = "DemoECommerceApp-Selenium-testNG-Java" + timestamp + ".html";
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//Reports//" + reportName);
		
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);
		
		//add system information/environment info to reports
		reports.setSystemInfo("Machine:", "testpc1");
		reports.setSystemInfo("OS", "windows 11");
		reports.setSystemInfo("browser:", readConfig.getBrowserName());
		reports.setSystemInfo("user name:", "test");

		//configuration to change look and feel of report
		htmlReporter.config().setDocumentTitle("Extent Listener Report");
		htmlReporter.config().setReportName("Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
	}
	

	//when test case get started, this method is called
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Started test method name is: " + result.getName());
	}

	//when test case get passed, this method is called
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Successfully executed test method name is: " + result.getName());
		
		test = reports.createTest(result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel("Successfully executed test method name is: " + result.getName(), ExtentColor.GREEN));
	}

	//When test case get failed, this method is called
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Failed Test Metod Name: " + result.getName());
		//create entry in html report
		test = reports.createTest(result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel("Failed test case name is: " + result.getName(), ExtentColor.RED));
		
		String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + result.getName() + ".png";
		
		File screenshotFile = new File(screenshotPath); 
		
		if(screenshotFile.exists()) {
			test.fail("Captured screenshot is below: " + test.addScreenCaptureFromPath(screenshotPath));
		}
		
	}
	
	
	//When test case get skipped, this method is called
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Skipped test method name: " + result.getName());
		
		test = reports.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel("Skipped test method name: " + result.getName(), ExtentColor.LIME));
	}

	//onstart method is called when any test starts
	@Override
	public void onStart(ITestContext context) {
		configureReport();
		System.out.println("On Start method invoked...");
	}


	//onFinish method is called after all tests are executed
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("On Finish method invoked...");
		//it is mandatory to call flush method to ensure information is written to the started reporter.
		reports.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

}
