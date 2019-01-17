package frameworkSandbox;

import framework.Commands;
import framework.Drivers;
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
        DefaultConfig.appAddress = "https://www.amazon.com/";
        d = DefaultConfig.defaultConfig();
        PageFactory.initElements(d, HoverOver.class);
    }

    @Test
    public void testSauce() throws Exception {
        Commands.waitForSeconds(5000);
//        Commands.waitForURL(d, "newegg");
        Commands.waitForURL(d, "amazon");
        hoverAccountLists();
        clickSignIn();
        waitForPageLoad();
    }

    @FindBy(className = "nav-line-2")
    public static List<WebElement> headerOpts;

    public static void hoverAccountLists(){
        String desc = "Hover over departments";
        Commands.hoverOver(d, headerOpts.get(2), desc);
    }

    @FindBy(className = "nav-action-inner")
    public static List<WebElement> actions;

    public static void clickSignIn(){
        String desc = "Click on sign in";
        Commands.click(actions.get(0), desc);
    }

    @FindBy(name = "email")
    public static WebElement email;

    public static void waitForPageLoad(){
        String desc = "Wait for page load";
        Commands.waitForURL(d, "signin");
        Commands.waitForElement(d, email);
    }
}
