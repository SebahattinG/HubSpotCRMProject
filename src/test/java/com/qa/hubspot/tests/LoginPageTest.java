package com.qa.hubspot.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Verify;
import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;



@Epic("Epic -101 : Create login features")
@Feature("US-501: Create test for login on HubSpot")
public class LoginPageTest {
	
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;
	
	
	@BeforeTest
	public void setup(){
		
		basePage= new BasePage();
		prop= basePage.init_properties();
		String browserName= prop.getProperty("browser");
		driver=basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		loginPage= new LoginPage(driver);
		userCred= new Credentials(prop.getProperty("username"),prop.getProperty("password"));
		
	
	}
	
	
	
	@Test(priority=1, enabled=true)
	@Description("Verify Login Page Title")
	@Severity(SeverityLevel.NORMAL)
	
	public void verifyLoginPageTitle(){	
	String title=	loginPage.getPageTitle();
	System.out.println("Login page title is "+ title);
	AssertJUnit.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	
	@Test(priority=2, enabled=true)
	@Description("Verify Sign Up Link")
	
	@Severity(SeverityLevel.BLOCKER)
	public void verifySignupLink(){
		AssertJUnit.assertTrue(loginPage.checkSignup());
		}
	
	
	@Test(priority=3, enabled= true)
	@Description("Login Test")
	@Severity(SeverityLevel.BLOCKER)
	public void LoginTest(){
		driver.manage().window().maximize();
		HomePage homePage =loginPage.doLogin(userCred);
		String accountName= homePage.getLoggedInUserAccountName();
		System.out.println("logged in account name "+ accountName);
		AssertJUnit.assertEquals(accountName, prop.getProperty("accountname"));
		
	}
	@DataProvider
	public Object[][] getLoginInvalidData(){
		Object data[][]= {{"bill@gmail.com", "test123456"},
						{"tilly@gamil.com"," "},
						{" ","test123456"},
						{"yummy","yummy"},
						{" "," "}};
	return data;
	}
	
	@Test(priority=4, dataProvider="getLoginInvalidData", enabled = false)
	@Description("Verify Login Page Title")
	@Severity(SeverityLevel.NORMAL)
	
	public void login_InvalidTestCase(String username, String pwd){
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		AssertJUnit.assertTrue(loginPage.checkLoginerrorMessage());
		
	}
		
	@AfterMethod
	@AfterTest
	public void tearDown(){
		driver.close();
	}
	
	
	

}
