package gateways.sauceLabs;

import framework.Drivers;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

public class SauceLabsUtils {

    /**
     * SauceLabs Config for RemoteWebDriver
     * Retrieves API credentials for SauceLabs and loads up the given {@param appAddress} while initiating a new
     * remote web driver in SauceLabs.
     *
     * @param  slUser	    the username that will be passed to SauceLabs
     * @param  slPass	    the password that will be passed to SauceLabs
     * @param  browser	    the intended browser that the test will run in
     * @param  platform	    the intended operating system that the test will run in
     * @param  appAddress   the url that the browser will load
     */
    public static WebDriver sauceLabsConfig(String slUser, String slPass, String browser, String platform,
                                            String appAddress) {
        RemoteWebDriver driver = null;

        if (slUser != null){
            Assert.fail("Please provide a SauceLabs username");
        } else if (slPass != null){
            Assert.fail("Please provide a SauceLabs password");
        } else {
            try {
                final String URL = "https://" + slUser + ":" + slPass + "@ondemand.saucelabs.com:443/wd/hub";
                driver = new RemoteWebDriver(new URL(URL), Drivers.setCaps(browser, platform));
                driver.setFileDetector(new LocalFileDetector());
                driver.get(appAddress);
                System.out.println("title of page is: " + driver.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return driver;
    }
}
