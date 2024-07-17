package com.demoApp.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	
	Properties properties;
	String configFilePath = "Configuration/config.properties";
	
	//constructor
	public ReadConfig() {
		try {
			//reading properties from config.properties file
			properties = new Properties();
			FileInputStream inputStream = new FileInputStream(configFilePath);
			properties.load(inputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//get baseURL from properties
	public String getBaseURL() {
		String url = properties.getProperty("baseURL");
		if(url != null)
			return url;
		else
			throw new RuntimeException("baseURL not specified in config file;");
	}

	//get browserName from properties
	public String getBrowserName() {
		String browserName = properties.getProperty("browserName");
		if(browserName != null)
			return browserName;
		else
			throw new RuntimeException("browserName not specified in config file;");
	}
	
	//get User Email from properties
	public String getUserEmail() {
		String userEmail = properties.getProperty("userEmail");
		if(userEmail != null)
			return userEmail;
		else
			throw new RuntimeException("userEmail not specified in config file;");
	}
	
	//get Password from properties
	public String getPassword() {
		String password = properties.getProperty("password");
		if(password != null)
			return password;
		else
			throw new RuntimeException("password not specified in config file;");
	}
	
	
	//get User Name from properties
	public String getUserName() {
		String userName = properties.getProperty("userName");
		if(userName != null)
			return userName;
		else
			throw new RuntimeException("userName not specified in config file;");
	}
	
}
