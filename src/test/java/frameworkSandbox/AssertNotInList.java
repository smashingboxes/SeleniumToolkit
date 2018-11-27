package frameworkSandbox;

import framework.Commands;
import framework.PropsSystem;
import gateways.sauceLabs.SauceLabsUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class AssertNotInList {

    public static WebDriver d;

    @BeforeTest
    public static void beforeTest(){
        File f = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        d = SauceLabsUtils.sauceLabsConfig(f, PropsSystem.chrome, "Windows 10", "https://xkcd.com/");
        d.manage().window().maximize();
        PageFactory.initElements(d, AssertNotInList.class);
    }

    @FindBy(className = "comicNav")
    public static List<WebElement> nav;

    public static void assertNotInList(){
        String desc = "Assert not in list";
        List<WebElement> navItems = nav.get(0).findElements(By.tagName("li"));
        Commands.assertNotInList(navItems, "heyhey", desc);
    }

    @Test
    public void testSauce() throws Exception {
        Commands.waitForSecs(5000);
//        Commands.waitForURL(d, "newegg");
        Commands.waitForURL(d, "xkcd");
        Commands.waitForEl(d, nav.get(0));
        assertNotInList();
    }
}
