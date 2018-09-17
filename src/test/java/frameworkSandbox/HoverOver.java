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

public class HoverOver {

    public static WebDriver d;

    @BeforeTest
    public static void beforeTest(){
        File f = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        d = Drivers.sauceLabsConfig(f, PropsSystem.chrome, "Windows 10", "https://www.amazon.com/");
        d.manage().window().maximize();
        PageFactory.initElements(d, HoverOver.class);
    }

    @Test
    public void testSauce() throws Exception {
        Commands.waitForSecs(5000);
//        Commands.waitForURL(d, "newegg");
        Commands.waitForURL(d, "amazon");
        hoverDepartments();
    }

    @FindBy(className = "nav-line-2")
    public static List<WebElement> headerOpts;

    public static void hoverDepartments(){
        String desc = "Hover over departments";
        Commands.hoverOver(d, headerOpts.get(6), desc);
    }
}
