package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class Drivers {

	//Chrome WebDriver
	public static WebDriver chromeDriver(WebDriver d, String app){
		System.setProperty("webdriver.chrome.driver", "exe/chromedriver");
		d = new ChromeDriver();
		d.get(app);
		return d;
	}
	
	//Safari WebDriver - need a developer account
	public static WebDriver safariDriver(WebDriver d, String app){
		System.setProperty("webdriver.safari.noinstall", "true");
		d = new SafariDriver();
		d.get(app);
		return d;
	}
	
	//Firefox WebDriver - need to run on compatible FF version
	//Also, there is a known issue with geckodriver
	public static WebDriver firefoxDriver(WebDriver d, String app){
		//Configure to use GeckoDriver
		System.setProperty("webdriver.gecko.driver", "exe/geckodriver");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		
		d = new FirefoxDriver();
		d.get(app);
		return d;
	}
}
