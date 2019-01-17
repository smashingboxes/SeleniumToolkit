package frameworkSandbox;

import framework.Commands;
import framework.Drivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;

@Listeners(CheckResultsListener.class)
public class SelectDropdown {

    public static WebDriver d;

    @BeforeTest
    public static void beforeTest(){
        DefaultConfig.appAddress = "https://www.newegg.com/";
        d = DefaultConfig.defaultSauceLabsConfig();
    }

    @Test
    public void testSauce() throws Exception {
        Commands.waitForSeconds(5000);
        Commands.waitForURL(d, "newegg");

        WebElement elMainDrop = d.findElement(By.id("haQuickSearchStore"));
        Select elMainDropSel = new Select(d.findElement(By.id("haQuickSearchStore")));
//        Select mainDrop = new Select(elMainDrop);

        WebElement elTrendingNow = d.findElement(By.className("trend-keys-title"));

        Commands.selectOption(elMainDrop, "Gaming", "Click this");
        Commands.assertTextContains(elTrendingNow, "TRENDING", "Assert this");
    }
}
