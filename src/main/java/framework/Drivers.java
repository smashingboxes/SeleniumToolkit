package framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Drivers {
	
	//SauceLabs Config for RemoteWebDriver
	public static WebDriver sauceLabsConfig(File f, String browser, String platform, String appAddress){
		String[] kv = readFile(f);
		WebDriver d = null;
		
		try{
			if (!kv.equals(null)){
				final String URL = "https://" + kv[0] + ":" + kv[1] + "@ondemand.saucelabs.com:443/wd/hub";
				d = new RemoteWebDriver(new URL(URL), setCaps(browser, platform));
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
					case "Windows": caps.setCapability("platform", platform); return caps;
					default: return null;
				}
			case "chrome":
				caps = DesiredCapabilities.chrome();
				caps.setCapability("version", "58");
				
				switch (platform){
					case "Windows": caps.setCapability("platform", platform); return caps;
				default: return null;
			}
			default: return null;
		}
	}

	//Chrome WebDriver
	public static WebDriver chromeDriver(WebDriver d, String app){
		System.setProperty("webdriver.chrome.driver", "webDrivers/chromedriver");
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
		System.setProperty("webdriver.gecko.driver", "webDrivers/geckodriver");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		
		d = new FirefoxDriver();
		d.get(app);
		return d;
	}
}
