package com.maveric.AppiumProject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class AmazonAppAndroid {

	public static void main(String[] args) throws Exception {
		
//	1.Install amazon android application
	DesiredCapabilities dc = new DesiredCapabilities();
	dc.setCapability("browserstack.user", "evelinpearl_6QMlvB");
    	dc.setCapability("browserstack.key", "r1DPxhYfp17qscEAhez4");
	dc.setCapability("app", "bs://b54033041b0517e521829dd19506d595d1b70f4e");
		
	//device
	dc.setCapability("device", "Google Pixel 3");
	dc.setCapability("os_version", "9.0");
		
	//build
	dc.setCapability("project", "UI Automator");
	dc.setCapability("build", "browserstack-build-1");
	dc.setCapability("name", "UI Automator");
	AndroidDriver<WebElement> ad = new AndroidDriver<WebElement>(new URL("http://hub.browserstack.com/wd/hub"), dc);
	ad.setLocation(new Location(13.08,80.27,6.40));
	System.out.println("connected");
        ad.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

// 	2. Complete Sign in process	
   
	ad.findElement(MobileBy.xpath("//*[@text='Already a customer? Sign in']")).click();
        ad.findElement(MobileBy.xpath("//*[@resource-id='ap_email_login']")).sendKeys("evelinpearl300499@gmail.com");  
        ad.findElement(MobileBy.xpath("//*[@text='Continue']")).click();
        ad.findElement(MobileBy.xpath("//*[@resource-id='ap_password']")).sendKeys("evelin");
        ad.findElement(MobileBy.xpath("//*[@resource-id='signInSubmit']")).click();
        
// 	3. Search for a product "Laptop" and select the top most one.
	
	SoftAssert sf = new SoftAssert();
        List<WebElement> list= ad.findElements(MobileBy.xpath("//*[@text='Search' or @text='What are you looking for?']"));
        if(list.size()>0) {
        	ad.findElement(MobileBy.xpath("//*[@text='Search' or @text='What are you looking for?']")).click();
        	ad.findElement(MobileBy.xpath("//*[@text='Search' or @text='What are you looking for?']")).sendKeys("Laptop");
        }else {
        	sf.assertTrue(ad.findElement(MobileBy.xpath("//*[@text='NO, THANKS']")).isDisplayed());
        	sf.assertAll();
        	ad.findElement(MobileBy.xpath("//*[@text='Search' or @text='What are you looking for?']")).click();
             	ad.findElement(MobileBy.xpath("//*[@text='Search' or @text='What are you looking for?']")).sendKeys("Laptop");
        }
        ad.findElement(MobileBy.xpath("//*[@text='laptop']")).click();
        ad.findElement(MobileBy.xpath("(//*[@class='android.widget.TextView'])[9]")).click();
        ad.navigate().back();
        String item = ad.findElement(MobileBy.xpath("//android.view.View[2]/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View[1]")).getText();
        System.out.println(item);
		
//      4. Add the product to cart and validate the product in the cart.
		
        String add = "Add to Cart";
        ad.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+ add + "\").instance(0))");
        ad.findElement(MobileBy.xpath("//*[@text='Add to Cart']")).click();
        ad.findElement(MobileBy.xpath("//*[@content-desc='Cart']")).click();
        String item1 = ad.findElement(MobileBy.xpath("//*[@text='"+item+"']")).getText();
        System.out.println(item1);
		
//      5. Go to cart and proceed to buy.
		
        if(item.equals(item1)) {	
        ad.findElement(MobileBy.AndroidUIAutomator("UiSelector().textContains(\"Proceed to Buy\")")).click();
        }
        
// 	6. Add a new address.

        String addNew = "Add a New Address";
        ad.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+ addNew + "\").instance(0))");
        ad.findElement(MobileBy.xpath("//*[@text='Add a New Address']")).click();
        String nameField ="Full name";
        ad.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+ nameField + "\").instance(0))");      
        ad.findElement(MobileBy.xpath("//*[@resource-id='address-ui-widgets-enterAddressFullName']")).sendKeys("Eva");
        ad.findElement(MobileBy.xpath("//*[@resource-id='address-ui-widgets-enterAddressPhoneNumber']")).sendKeys("2345645645");
        String building = "Flat, House no., Building, Company, Apartment";
        ad.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+ building + "\").instance(0))");
        ad.findElement(MobileBy.xpath("//*[@resource-id='address-ui-widgets-enterAddressLine1']")).sendKeys("No 174, Sai ghar apartment,Sivam street");
        ad.findElement(MobileBy.xpath("//*[@resource-id='address-ui-widgets-enterAddressLine2']")).sendKeys("Kattupakkam");
        String address = "Use this address";
        ad.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+ address + "\").instance(0))");      
     
// 	7. Choose the Address newly added as the address to be delivered.

	ad.findElement(MobileBy.xpath("//*[@text='Use this address']")).click();
        ad.findElement(MobileBy.xpath("//*[@text='Use this address']")).click();

//	8. Choose your Delivery Option.
//	The pay on delievery option is disabled
        ad.quit();
       
	}	
}
