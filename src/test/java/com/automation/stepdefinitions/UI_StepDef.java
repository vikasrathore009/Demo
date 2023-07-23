package com.automation.stepdefinitions;

import static com.automation.utility.ResponseUtils.response;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.basic.BasicColorChooserUI.PropertyHandler;

import org.apache.tools.ant.property.GetProperty;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.base.SerenityBase;
import com.automation.utility.PropertyHolder;
import com.automation.utility.PropertyManager;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UI_StepDef extends SerenityBase {
	PropertyManager p = new PropertyManager();
	public static long LOAD_TIMEOUT = 10;
	public static long REFRESH_RATE = 1;
	String laptopAmount;
	String PurchaseInfo;
	WebElement wE;
	

    @Given("^User navigates to \"([^\"]*)\" via \"([^\"]*)\" browser$")
    public void user_navigates_to_something_via_something_browser(String strArg1, String strArg2) throws Throwable {
//    	String command="Taskkill /F /IM chrome.exe";
//    	 Runtime.getRuntime().exec(command);
//    	 APP_LOG.info("All the browser instances are killed successfully before execution");
    	
    	//Create a instance of ChromeOptions class
    	ChromeOptions options = new ChromeOptions();

    	//Add chrome switch to disable notification - "**--disable-notifications**"
    	options.addArguments("--disable-notifications");
    	        
    	//Set path for driver exe 
    	System.setProperty("webdriver.chrome.driver",(System.getProperty("user.dir")+"/driver/chromedriver.exe"));

    	//Pass ChromeOptions instance to ChromeDriver Constructor
    	 driver =new ChromeDriver(options);
//	     driver = new ChromeDriver();
	     driver.manage().window().maximize();
		driver.get(p.valueFromConfig("Demo_FB_URL"));
    	
    }
    
    
    @Given("^User performs login operation$")
    public void user_performs_login_operation() throws Throwable {
    	By userInput=By.xpath("//input[@data-testid='royal_email']");
    	By passwordInput=By.xpath("//input[@data-testid='royal_pass']");
    	By loginButton=By.xpath("//button[@data-testid='royal_login_button']");
    	waitForElementToBeClickable(driver, userInput, 10);
//    	dynamicWait( ExpectedConditions.elementToBeClickable(userInput));
    	driver.findElement(userInput).click();
    	driver.findElement(userInput).sendKeys("rathorevikash08@gmail.com");
    	waitForElementToBeClickable(driver, passwordInput, 10);
//    	dynamicWait( ExpectedConditions.elementToBeClickable(passwordInput));
    	driver.findElement(passwordInput).click();
    	driver.findElement(passwordInput).sendKeys("Welcome@01");
    	waitForElementToBeClickable(driver, loginButton, 10);
//    	dynamicWait( ExpectedConditions.elementToBeClickable(loginButton));
    	driver.findElement(loginButton).click();
    	
    }


    @Given("^User navigates to groups page$")
    public void user_navigates_to_groups_page()throws Throwable {
    	By groups=By.xpath("//span[text()='Groups']");
    	waitForElementToBeClickable(driver, groups, 10);
    	driver.findElement(groups).click();
    }

    @Given("^User opens a suggested group/$")
    public void user_opens_a_suggested_group()throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	By suggestedGroups=By.xpath("(//a[@aria-label='ignore this'])[1]/preceding-sibling::div");
    	By joinGroup=By.xpath("//span[text()='Join Group']");
    	waitForElementToBeClickable(driver, joinGroup, 10);
    	driver.findElement(suggestedGroups).click();
    }

    @Given("^User validates following$")
    public void user_validates_following(DataTable table) throws InterruptedException {
    	
    	List<String> TestData = table.asList(String.class);
		for (int i=1;i<TestData.size();i++){
			    String locatorName="//span[text()='"+TestData.get(i)+"']";
				By parentLocator  = By.xpath(locatorName);
				waitForElementToBeVisible(driver, parentLocator, 10);
				wE=driver.findElement(parentLocator);
//				dynamicWait( ExpectedConditions.visibilityOf(wE));
				APP_LOG.info(locatorName + " is visible");

			}
    	
    } 
    
    public void waitForElementToBeClickable(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void waitForElementToBeVisible(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

//    public static void dynamicWait(ExpectedCondition pageLoadCondition) throws InterruptedException 
//    {
//    	
//    	Thread.sleep(1500);
//    		Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(LOAD_TIMEOUT))
//    				.pollingEvery(Duration.ofSeconds(REFRESH_RATE));
//    	 wait.until(pageLoadCondition);
//    }

    @And("^user quits the browser$")
    public void user_quits_the_browser() throws Throwable {
        driver.quit();
    }
    
}


