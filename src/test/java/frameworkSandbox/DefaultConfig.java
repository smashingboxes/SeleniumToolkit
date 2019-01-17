package frameworkSandbox;

import framework.Drivers;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class DefaultConfig {

    public static String platform = "Windows 10";
    public static String browser = "chrome";
    public static String appAddress;
    public static Boolean headless = true;

    private static String[] kv = getSLCreds();

    public static Boolean runSauceLabs = true;
    public static String sauceLabsUser = kv[0];
    public static String sauceLabsPass = kv[1];

    public static WebDriver defaultConfig(){
        return Drivers.driverInit(browser, appAddress, headless);
    }

    public static WebDriver defaultSauceLabsConfig(){
        return Drivers.driverInit(platform, browser, appAddress, runSauceLabs, sauceLabsUser, sauceLabsPass, headless);
    }

    private static String[] getSLCreds(){
        File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        return ReadFile.readFile(file, "sauceLabs");
    }
}
