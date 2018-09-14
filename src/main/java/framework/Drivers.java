package framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import static org.testng.Assert.fail;

public class Drivers {

	public static WebDriver checkSauceLabs(String platform, String browser, String appUrl, File f, Boolean runSL){
		WebDriver d = runSauceLabs(browser, platform, appUrl, f, runSL);

		if (d.equals(null)){
			fail("The WebDriver is null. Please check code for Drivers.");
		}

		d.manage().window().maximize();

		return d;
	}

	private static WebDriver runSauceLabs(String browser, String platform, String appAddress, File f, Boolean runSL){
		WebDriver d = null;

		if (runSL){
			d = Drivers.sauceLabsConfig(f, browser, platform, appAddress);

			if (d.equals(null)){
				switch(browser){
					case "firefox": return firefoxDriver(appAddress);
					case "chrome": return chromeDriver(appAddress);
					default: return d;
				}
			}
		} else {
			switch(browser){
				case "firefox": return firefoxDriver(appAddress);
				case "chrome": return chromeDriver(appAddress);
				default: return d;
			}
		}

		return d;
	}

	//SauceLabs Config for RemoteWebDriver
	public static WebDriver sauceLabsConfig(File f, String browser, String platform, String appAddress){
		String[] kv = readFile(f);
		RemoteWebDriver d = null;

		try{
			if (!kv.equals(null)){
				final String URL = "https://" + kv[0] + ":" + kv[1] + "@ondemand.saucelabs.com:443/wd/hub";
				d = new RemoteWebDriver(new URL(URL), setCaps(browser, platform));
				d.setFileDetector(new LocalFileDetector());
				d.get(appAddress);
				System.out.println("title of page is: " + d.getTitle());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return d;
	}
	
	@SuppressWarnings("resource")
	private static String[] readFile(File f){
		String[] kv = new String[2];
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));  
			String readLine = "";

	        System.out.println("Reading file using Buffered Reader");

			while ((readLine = br.readLine()) != null) {
				String[] line = readLine.split("=");
				
				if(line[0].equals("username")){
					kv[0] = line[1];
				} else if (line[0].equals("accessKey")){
					kv[1] = line[1];
				}
			} 
			
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		if (!kv[0].equals(null) && !kv[1].equals(null)){
			return kv;
		} else {
			return null;
		}
	}
	
	private static DesiredCapabilities setCaps(String browser, String platform){
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

	//Chrome WebDriver
	public static WebDriver chromeDriver(String app){
		System.setProperty("webdriver.chrome.driver", "webDrivers/chromedriver242");
		WebDriver d = new ChromeDriver();
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
	
	//Firefox WebDriver - need to run on compatible FF version
	//Also, there is a known issue with geckodriver
	public static WebDriver firefoxDriver(String app){
		//Configure to use GeckoDriver
		System.setProperty("webdriver.gecko.driver", "webDrivers/geckodriver0_19_1");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		
		WebDriver d = new FirefoxDriver();
		d.get(app);
		return d;
	}
}
