package gateways.sauceLabs;

import framework.Drivers;
import gateways.GatewayProps;
import gateways.GatewayUtils;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
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
        RemoteWebDriver d = null;

        if (!slUser.equals(null)){
            Assert.fail("Please provide a SauceLabs username");
        } else if (!slPass.equals(null)){
            Assert.fail("Please provide a SauceLabs password");
        } else {
            try {
                final String URL = "https://" + slUser + ":" + slPass + "@ondemand.saucelabs.com:443/wd/hub";
                d = new RemoteWebDriver(new URL(URL), Drivers.setCaps(browser, platform));
                d.setFileDetector(new LocalFileDetector());
                d.get(appAddress);
                System.out.println("title of page is: " + d.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return d;
    }
}
