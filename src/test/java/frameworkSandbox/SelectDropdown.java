package frameworkSandbox;

import framework.Commands;
import framework.Drivers;
import framework.PropsSystem;
import gateways.sauceLabs.SauceLabsUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;

@Listeners(CheckResultsListener.class)
public class SelectDropdown {

    public static WebDriver d;

    @BeforeTest
    public static void beforeTest(){
//        d = Drivers.safariDriver("https://www.newegg.com/");
//        d = Drivers.safariDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.firefoxDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.chromeDriver("http://sl-test.herokuapp.com/guinea_pig/file_upload");
        File f = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        String [] kv = ReadFile.readFile(f, "sauceLabs");
        d = Drivers.driverInit("Windows 10", PropsSystem.chrome, "https://www.newegg.com/", kv[0], kv[1], true, false);
        d.manage().window().maximize();
    }

    @Test
    public void testSauce() throws Exception {
        Commands.waitForSecs(5000);
        Commands.waitForURL(d, "newegg");

        WebElement elMainDrop = d.findElement(By.id("haQuickSearchStore"));
        Select elMainDropSel = new Select(d.findElement(By.id("haQuickSearchStore")));
//        Select mainDrop = new Select(elMainDrop);

        WebElement elTrendingNow = d.findElement(By.className("trend-keys-title"));

        Commands.selectOption(elMainDrop, "Gaming", "Click this");
        Commands.assertTextContains(elTrendingNow, "TRENDING", "Assert this");
    }
}
