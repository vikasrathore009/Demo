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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.automation.base.SerenityBase;
import com.automation.utility.PropertyHolder;
import com.automation.utility.PropertyManager;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AdidasUI_StepDef extends SerenityBase {
	PropertyManager p = new PropertyManager();
	public static long LOAD_TIMEOUT = 10;
	public static long REFRESH_RATE = 1;
	String laptopAmount;
	String PurchaseInfo;
	WebElement wE;
	

    @Given("^User navigates to \"([^\"]*)\" via \"([^\"]*)\" browser$")
    public void user_navigates_to_something_via_something_browser(String strArg1, String strArg2) throws Throwable {
    	String command="Taskkill /F /IM chrome.exe";
    	 Runtime.getRuntime().exec(command);
    	 APP_LOG.info("All the browser instances are killed successfully before execution");
    	System.setProperty("webdriver.chrome.driver",(System.getProperty("user.dir")+"/driver/chromedriver.exe"));
	     driver = new ChromeDriver();
	     driver.manage().window().maximize();
		driver.get(p.valueFromConfig("Demo_Online_Shop_URL"));
    	
    }
    
    @When("^User \"([^\"]*)\" a laptop \"([^\"]*)\" from category \"([^\"]*)\"$")
    public void user_something_a_laptop_something_from_category_something(String strArg1, String strArg2, String strArg3) throws Throwable {
    	
    	By category=By.xpath("//a[contains(text(),'"+strArg3+"')]");
    	By laptop=By.xpath("//a[contains(text(),'"+strArg2+"')]");
    	By addToCart=By.xpath("//a[contains(text(),'"+strArg1+"')]");
    	By Home=By.xpath("//a[contains(text(),'Home')]");
    	dynamicWait( ExpectedConditions.elementToBeClickable(category));
    	driver.findElement(category).click();
    	dynamicWait( ExpectedConditions.elementToBeClickable(laptop));
         driver.findElement(laptop).click();
         dynamicWait( ExpectedConditions.elementToBeClickable(addToCart));
         driver.findElement(addToCart).click();
         Thread.sleep(1000);
         driver.switchTo().alert().accept();
    
    }

    @And("^user navigates to \"([^\"]*)\"$")
    public void user_navigates_to_something(String strArg1) throws Throwable {
    	By locator=By.xpath("//a[contains(text(),'"+strArg1+"')]");
    	dynamicWait( ExpectedConditions.elementToBeClickable(locator));
    	driver.findElement(locator).click();
    	
    }
    
    @And("^user \"([^\"]*)\" the item \"([^\"]*)\" from cart$")
    public void user_something_the_item_something_from_cart(String strArg1, String strArg2) throws Throwable {
    	By locator=By.xpath("//td[contains(text(),'"+strArg2+"')]/ancestor::tr/td/a[contains(text(),'"+strArg1+"')]");
    	dynamicWait( ExpectedConditions.elementToBeClickable(locator));
    	driver.findElement(locator).click();
    }
    
    @And("^user \"([^\"]*)\" on \"([^\"]*)\"$")
    public void user_something_on_something(String strArg1, String strArg2) throws Throwable {
    	
    	if(strArg1.equals("move"))
    	{
    		By locator=By.xpath("//a[contains(text(),'"+strArg2+"')]");
    		dynamicWait( ExpectedConditions.elementToBeClickable(locator));
    	driver.findElement(locator).click();
    	}
    	else
    	{
    		By locator=By.xpath("//button[contains(text(),'"+strArg2+"')]");
    		dynamicWait( ExpectedConditions.elementToBeClickable(locator));
        	driver.findElement(locator).click();
    		
    	}
    }
    
    @And("^user fills the place order form using following details$")
    public void user_fills_the_place_order_form_using_following_details(DataTable table) throws Throwable {
    	List<Map<String, String>> TestData = table.asMaps(String.class, String.class);
		for (Map<String, String> MTestData : TestData) {
			for (Entry<String, String> m : MTestData.entrySet()) {
				By parentLocator  = By.xpath("//input[@id='" + m.getKey().toLowerCase()
						+ "']");
				wE=driver.findElement(parentLocator);
				dynamicWait( ExpectedConditions.elementToBeClickable(parentLocator));
				wE.sendKeys(m.getValue());
				APP_LOG.info(m.getKey() + " is entered successfully");

			}
		}
    }
    public static void dynamicWait(ExpectedCondition pageLoadCondition) throws InterruptedException 
    {
    	
    	Thread.sleep(1500);
    		Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(LOAD_TIMEOUT))
    				.pollingEvery(Duration.ofSeconds(REFRESH_RATE));
    	 wait.until(pageLoadCondition);
    }

    @And("^user fetches info for item \"([^\"]*)\" from cart$")
    public void user_fetches_info_for_item_something_from_cart(String strArg1) throws Throwable {
    	By locator=By.xpath("//h3[@id='totalp']");
    	dynamicWait( ExpectedConditions.elementToBeClickable(locator));
    	laptopAmount=driver.findElement(locator).getText();
    	APP_LOG.info("Laptop Price= "+laptopAmount);
    }
    
    
    @Then("^user fetches the purchase info$")
    public void user_fetches_the_purchase_info() throws Throwable {
       By LocatorPurchaseInfo=By.xpath("//h2[contains(text(),'Thank you for your purchase!')]/following-sibling::p[contains(text(),'Id')]");
       dynamicWait( ExpectedConditions.elementToBeClickable(LocatorPurchaseInfo));
        PurchaseInfo=driver.findElement(LocatorPurchaseInfo).getText();
       APP_LOG.info("Purchase Info = "+PurchaseInfo);
       
    }
    
    @And("^user verifies \"([^\"]*)\"$")
    public void user_verifies_something(String strArg1) throws Throwable {
       if(PurchaseInfo.contains(laptopAmount))
       {
    	   APP_LOG.info(" Purchase amount equals expected");
    	   Assert.assertEquals(1, 1);
       }
       else
       {
    	   APP_LOG.info(" Purchase amount miismatch");
    	   Assert.assertEquals(1, 2);
       }
    }
    @And("^user quits the browser$")
    public void user_quits_the_browser() throws Throwable {
        driver.quit();
    }
}


