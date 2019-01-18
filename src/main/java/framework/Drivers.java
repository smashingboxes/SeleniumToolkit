package framework;

import gateways.sauceLabs.SauceLabsUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

public class Drivers {

	/**
	 * Initializes the web driver locally according to the given parameters
	 *
	 * @param  browser		the intended browser that the test will run in
	 * @param  appAddress	the url that the browser will load
	 * @param  headless		the flag for running in headless mode; true if test will run on headless mode, false if
	 *                      	test will run in the browser
	 */
	public static WebDriver driverInit(String browser, String appAddress, Boolean headless){
		switch(browser){
			case "firefox": return firefoxDriver(appAddress, headless);
			case "chrome": return chromeDriver(appAddress, headless);
			case "safari": return safariDriver(appAddress);
			default: return chromeDriver(appAddress, headless);
		}
	}

	/**
	 * Initializes the web driver in SauceLabs according to the given parameters
	 *
	 * @param  platform			the intended operating system that the test will run in
	 * @param  browser			the intended browser that the test will run in
	 * @param  appAddress		the url that the browser will load
	 * @param  runSauceLabs		the flag for running test in SauceLabs; true if test will run in SauceLabs, false if
	 *                          	test will run locally
	 * @param  slUser	   		the username that will be passed to SauceLabs
	 * @param  slPass			the password that will be passed to SauceLabs
	 * @param  headless			the flag for running in headless mode; true if test will run on headless mode, false if
	 *                      		test will run in the browser; does not matter if {@param runSauceLabs} is true
	 */
	public static WebDriver driverInit(String browser, String appAddress, String platform, Boolean runSauceLabs,
									   String slUser, String slPass, Boolean headless){

		if (runSauceLabs){
			return SauceLabsUtils.sauceLabsConfig(slUser, slPass, browser, platform, appAddress);
		} else {
			return driverInit(browser, appAddress, headless);
		}

	}

	/**
	 * Sets the capabilities for the web driver based on given {@param browser} and {@param platform}.
	 *
	 * @param  platform		the intended operating system that the test will run in
	 * @param  browser		the intended browser that the test will run in
	 */
	public static DesiredCapabilities setCaps(String browser, String platform){
		DesiredCapabilities caps;
		
		switch (browser){
			case "firefox":
				caps = DesiredCapabilities.firefox();
				caps.setCapability("version", "52.0");
				
				switch (platform){
					case "Windows 10": caps.setCapability("platform", platform); return caps;
					default: return null;
				}
			case "chrome":
				caps = DesiredCapabilities.chrome();
				caps.setCapability("version", "58");
				
				switch (platform){
					case "Windows 10": caps.setCapability("platform", platform); return caps;
				default: return null;
			}
			default: return null;
		}
	}

	/**
	 * Sets up the Chrome web driver
	 *
	 * @param  appAddress	the url that the browser will load
	 * @param  headless		the flag for running in headless mode; true if test will run on headless mode, false if
	 *                      	test will run in the browser
	 */
	public static WebDriver chromeDriver(String appAddress, Boolean headless){
		ChromeOptions co = new ChromeOptions();
		co.setAcceptInsecureCerts(true);

		if(headless){
			co.addArguments("--headless");
		}

		WebDriverManager.chromedriver().setup(); //Retrieves the most current and compatible chrome web driver
		WebDriver driver = new ChromeDriver(co);
		return checkAppAddress(driver, appAddress);
	}
	
	/**
	 * Sets up the Safari web driver
	 *
	 * @param  appAddress	the url that the browser will load
	 */
	public static WebDriver safariDriver(String appAddress){
		System.setProperty("webdriver.safari.noinstall", "true");
		WebDriver driver = new SafariDriver();
		return checkAppAddress(driver, appAddress);
	}

	/**
	 * Sets up the Firefox web driver
	 *
	 * @param  appAddress	the url that the browser will load
	 * @param  headless		the flag for running in headless mode; true if test will run on headless mode, false if
	 *                      	test will run in the browser
	 */
	public static WebDriver firefoxDriver(String appAddress, Boolean headless){
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);

		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);

		FirefoxOptions fo = new FirefoxOptions();
		fo.setProfile(profile);

		if (headless){
			fo.setHeadless(true);
		}

		WebDriverManager.firefoxdriver().setup(); //Retrieves the most current and compatible firefox web driver
		
		WebDriver driver = new FirefoxDriver(fo);
		return checkAppAddress(driver, appAddress);
	}

	/**
	 * Checks if {@param appAddress} is null. If not, then visit the given page
	 *
	 * @param  driver	the web driver through which the action will take place
	 * @param  appAddress	the url that the browser will load
	 */
	public static WebDriver checkAppAddress(WebDriver driver, String appAddress){
		if (appAddress == null){ Assert.fail("Please provide an application URL."); }
		driver.get(appAddress);
		return driver;
	}
}
