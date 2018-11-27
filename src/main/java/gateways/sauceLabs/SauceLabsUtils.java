package gateways.sauceLabs;

import framework.Drivers;
import gateways.GatewayProps;
import gateways.GatewayUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SauceLabsUtils {

    //SauceLabs Config for RemoteWebDriver
    public static WebDriver sauceLabsConfig(File f, String browser, String platform, String appAddress){
        String[] kv = GatewayUtils.readFile(f, GatewayProps.sauceLabs);
        RemoteWebDriver d = null;

        try{
            if (!kv.equals(null)){
                final String URL = "https://" + kv[0] + ":" + kv[1] + "@ondemand.saucelabs.com:443/wd/hub";
                d = new RemoteWebDriver(new URL(URL), Drivers.setCaps(browser, platform));
                d.setFileDetector(new LocalFileDetector());
                d.get(appAddress);
                System.out.println("title of page is: " + d.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return d;
    }
}
