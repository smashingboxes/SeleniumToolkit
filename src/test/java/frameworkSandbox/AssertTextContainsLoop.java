package frameworkSandbox;

import framework.Commands;
import framework.Drivers;
import framework.PropsSystem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class AssertTextContainsLoop {

    public static WebDriver d;

    @BeforeTest
    public static void beforeTest(){
//        d = Drivers.safariDriver("https://www.newegg.com/");
//        d = Drivers.safariDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.firefoxDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.chromeDriver("http://sl-test.herokuapp.com/guinea_pig/file_upload");
        File f = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        d = Drivers.sauceLabsConfig(f, PropsSystem.chrome, "Windows 10", "https://www.xkcd.com");
        d.manage().window().maximize();
        PageFactory.initElements(d, AssertTextContainsLoop.class);
    }

    @Test
    public static void test(){
        waitPageLoad();
        Commands.assertNotInList(nav, "Prev", "");
    }

    @FindBy(className = "comicNav")
    public static List<WebElement> nav = null;

    public static void waitPageLoad(){
        Commands.waitForURL(d, "xkcd");
        Commands.waitForEl(d, nav.get(0));
    }
}
