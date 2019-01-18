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
	    checkAvailableBrowsers(browser);
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
	 * Note: PC platform options are: Windows 10, Windows 8.1, Windows 8, Windows 7, Linux
	 * Note: MAC platform options are:
	 * 		macOS 10.14 = Mojave
	 * 		macOS 10.13	= High Sierra
	 * 		macOS 10.12 = Sierra
	 * 		OS X 10.11 = El Capitan
	 * 		OS X 10.10 = Yosemite
	 * Note: Chrome version options are: 71.0 to 26.0
	 * Note: Firefox version options are: 64.0 to 4.0 (in addition to 25.0b2 and 21.0b1)
	 *
	 * @param  platform		the intended operating system that the test will run in
	 * @param  browser		the intended browser that the test will run in
	 */
	public static DesiredCapabilities setCaps(String browser, String platform){
		DesiredCapabilities caps;
		
		switch (browser){
			case "firefox":
				caps = DesiredCapabilities.firefox();
				caps.setCapability("version", "60.0"); break;
			case "chrome":
				caps = DesiredCapabilities.chrome();
				caps.setCapability("version", "69.0"); break;
            case "safari":
                caps = DesiredCapabilities.safari();break;
            default: caps = DesiredCapabilities.chrome(); caps.setCapability("version", "69.0");
                break;
        }

        checkAvailablePlatforms(platform);
        caps.setCapability("platform", platform);
        return caps;
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
	private static WebDriver checkAppAddress(WebDriver driver, String appAddress){
		if (appAddress == null){ Assert.fail("Please provide an application URL."); }
		driver.get(appAddress);
		return driver;
	}

    /**
     * Checks if {@param platform} matches a supported platform in SauceLabs.
     *
     * @param  platform		the intended operating system that the test will run in
     */
	private static void checkAvailablePlatforms(String platform){
		Boolean found = false;

		for(String thisP : listOfPlatforms()){
		    if (platform.equals(thisP)){
		        found = true;
		        break;
            }
        }

        if (!found){
		    Assert.fail("You must enter a valid platform that is handled by SauceLabs. Here are the available " +
                    "options: \n * For PC platforms: \"Windows 10\", \"Windows 8.1\", \"Windows 8\", \"Windows 7\", " +
                    "\"Linux\" \n * For MAC platforms: \"macOS 10.14\" (Mojave), \"macOS 10.13\" (High Sierra), " +
                    "\"macOS 10.12\" (Sierra), \"OS X 10.11\" (El Capitan), \"OS X 10.10\" (Yosemite)");
        }
	}

    public static void checkAvailableBrowsers(String browser){
        Boolean found = false;

        for(String thisB : listOfBrowsers()){
            if (browser.equals(thisB)){
                found = true;
                break;
            }
        }

        if (!found){
            Assert.fail("You must enter a valid platform that is handled by SauceLabs. Here are the available " +
                    "options: \"chrome\", \"firefox\", \"safari\"");
        }
    }

    /**
     * A list of platforms supported by SauceLabs
     */
	private static String[] listOfPlatforms(){
	    return new String[]{"Windows 10", "Windows 8.1", "Windows 8", "Windows 7", "Linux",
                "macOS 10.14", "macOS 10.13", "macOS 10.12", "OS X 10.11", "OS X 10.10"};
    }

    /**
     * A list of browsers supported by the Toolkit
     */
    private static String[] listOfBrowsers(){
	    return new String[]{"firefox", "chrome", "safari"};
    }

//    private static void checkPlatFormBrowserCompatability(String platform){
//        if (System.getProperty("os.name").contains("Windows") && platform.equals("safari")){
//            Assert.fail("Browser/Platform mismatch! ");
//        }
//    }
}
