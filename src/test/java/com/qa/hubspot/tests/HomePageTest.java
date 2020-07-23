package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;


public class HomePageTest {
WebDriver driver;
BasePage basePage;
HomePage homePage;
Properties prop;
LoginPage loginPage;
Credentials userCred;

@BeforeTest
public void setUp() throws InterruptedException{
	
	basePage= new BasePage();
	prop=basePage.init_properties();
	String browserName= prop.getProperty("browser");
	driver=basePage.init_driver(browserName);
	driver.get(prop.getProperty("url"));
	loginPage= new LoginPage(driver);
	userCred= new Credentials(prop.getProperty("username"),prop.getProperty("password"));
	homePage= loginPage.doLogin(userCred);
	Thread.sleep(5000);
	
}
@Test(priority=1)
public void getTitle(){
	
	String title= homePage.getHomePageTitle();
	System.out.println(title);
	Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
}

@Test(priority=2)

public void homeHeader(){
	String header= homePage.getHomePageHeader();
	System.out.println(header);
	Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER);
	
}
@Test(priority=3)

public void verifyLoggedInUser(){
	
	String accountName= homePage.getLoggedInUserAccountName();
	System.out.println("Account name is "+ accountName);
	Assert.assertEquals(accountName,prop.getProperty("accountname"));
}


@AfterTest
public void tearDown(){
	driver.close();
}
	


}
