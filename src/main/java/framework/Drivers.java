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

public class Drivers {

	/**
	 * Initializes the web driver according to the given parameters. This method determines if the test will be run
	 * locally or on SauceLabs and determines which type of driver to use.
	 *
	 * @param  platform		the intended operating system that the test will run in
	 * @param  browser		the intended browser that the test will run in
	 * @param  appAddress	the url that the browser will load
	 * @param  slUser	   	the username that will be passed to SauceLabs
	 * @param  slPass		the password that will be passed to SauceLabs
	 * @param  runSL		the flag for running SauceLabs; true if test will run on SauceLabs, false if test will run
	 *                      	locally
	 * @param  headless		the flag for running in headless mode; true if test will run on headless mode, false if
	 *                      	test will run in the browser
	 */
	public static WebDriver driverInit(String platform, String browser, String appAddress, String slUser,
									   String slPass, Boolean runSL, Boolean headless){
		if (runSL){
			return SauceLabsUtils.sauceLabsConfig(slUser, slPass, browser, platform, appAddress);
		} else {
			switch(browser){
				case "firefox": return firefoxDriver(appAddress, headless);
				case "chrome": return chromeDriver(appAddress, headless);
				case "safari": return safariDriver(appAddress);
				default: return chromeDriver(appAddress, headless);
			}
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
		driver.get(appAddress);
		return driver;
	}
	
	/**
	 * Sets up the Safari web driver
	 *
	 * @param  appAddress	the url that the browser will load
	 */
	public static WebDriver safariDriver(String appAddress){
		System.setProperty("webdriver.safari.noinstall", "true");
		WebDriver driver = new SafariDriver();
		driver.get(appAddress);
		return driver;
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
		driver.get(appAddress);
		return driver;
	}
}
