package framework;

import java.io.File;

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

	public static WebDriver driverInit(String platform, String browser, String appUrl, File f,
									   Boolean runSL, Boolean headless){
		if (runSL){
			return SauceLabsUtils.sauceLabsConfig(f, browser, platform, appUrl);
		} else {
			switch(browser){
				case PropsSystem.firefox: return firefoxDriver(appUrl, headless);
				case PropsSystem.chrome: return chromeDriver(appUrl, headless);
				case PropsSystem.safari: return safariDriver(appUrl);
				default: return chromeDriver(appUrl, headless);
			}
		}
	}

	public static DesiredCapabilities setCaps(String browser, String platform){
		DesiredCapabilities caps;
		
		switch (browser){
			case PropsSystem.firefox:
				caps = DesiredCapabilities.firefox();
				caps.setCapability("version", "52.0");
				
				switch (platform){
					case "Windows 10": caps.setCapability("platform", platform); return caps;
					default: return null;
				}
			case PropsSystem.chrome:
				caps = DesiredCapabilities.chrome();
				caps.setCapability("version", "58");
				
				switch (platform){
					case "Windows 10": caps.setCapability("platform", platform); return caps;
				default: return null;
			}
			default: return null;
		}
	}

	//Chrome WebDriver
	public static WebDriver chromeDriver(String app, Boolean headless){
		ChromeOptions co = new ChromeOptions();
		co.setAcceptInsecureCerts(true);

		if(headless){
			co.addArguments("--headless");
		}

		WebDriverManager.chromedriver().setup();
		WebDriver d = new ChromeDriver(co);
		d.get(app);
		return d;
	}
	
	//Safari WebDriver - need a developer account
	public static WebDriver safariDriver(String app){
		System.setProperty("webdriver.safari.noinstall", "true");
		WebDriver d = new SafariDriver();
		d.get(app);
		return d;
	}

	//Firefox WebDriver
	public static WebDriver firefoxDriver(String app, Boolean headless){
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);

		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);

		FirefoxOptions fo = new FirefoxOptions();
		fo.setProfile(profile);

		if (headless){
			fo.setHeadless(true);
		}

		WebDriverManager.firefoxdriver().setup();
		
		WebDriver d = new FirefoxDriver(fo);
		d.get(app);
		return d;
	}
}
