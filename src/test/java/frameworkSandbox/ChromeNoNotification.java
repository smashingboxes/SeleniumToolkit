package frameworkSandbox;

import framework.Commands;
import framework.Drivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class ChromeNoNotification {

    public static WebDriver d;

//    public static WebDriver chromeDriver(String app){
////		ChromeOptions options = new ChromeOptions();
////		options.addArguments("--disable-notifications");
////		options.addArguments("--start-maximized");
////		options.addArguments("--disable-web-security");
////		options.addArguments("--no-proxy-server");
////
////		Map<String, Object> prefs = new HashMap<String, Object>();
////		prefs.put("credentials_enable_service", false);
////		prefs.put("profile.password_manager_enabled", false);
////		prefs.put("profile.default_content_setting_values.notifications", 2);
////
////		options.setExperimentalOption("prefs", prefs);
//
//        System.setProperty("webdriver.chrome.driver", "webDrivers/chromedriver242");
////		WebDriver d = new ChromeDriver(options);
//        WebDriver d = new ChromeDriver();
//        d.get(app);
//        return d;
//    }


    @BeforeTest
    public static void beforeTest(){
//        d = Drivers.safariDriver("https://www.newegg.com/");
//        d = Drivers.safariDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.firefoxDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.chromeDriver("http://sl-test.herokuapp.com/guinea_pig/file_upload");
        File f = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        d = Drivers.driverInit("chrome", "https://www.xkcd.com/", true);
//        d = Drivers.driverInit("Windows 10", "chrome", "https://xkcd.com/", null, null, true, false);
        d.manage().window().maximize();
        PageFactory.initElements(d, ChromeNoNotification.class);
    }

    @Test
    public void testSauce() throws Exception {
//        Commands.waitForSecs(5000);
//        Commands.waitForURL(d, "newegg");

//        WebElement elMainDrop = d.findElement(By.id("haQuickSearchStore"));
//        Select mainDrop = new Select(elMainDrop);

//        WebElement elTrendingNow = d.findElement(By.className("trend-keys-title"));
//
//        Commands.selectOption(mainDrop, "Gaming", "Click this");
//        Commands.assertTextContains(elTrendingNow, "TRENDING", "Assert this");
        waitForUrl();
        waitForEmail();
        enterEmail(email);
        enterPass("password1!");
        clickLogin();

        waitForUrl();
        waitForHeader();
        assertHeader(role);
        clickProfile();
        clickProfileLink();

//        d!!.navigate().refresh()
//        Commands.waitForSecs(5000)

//        clickDashboard();


    }


    String email = "testing+1@smashingboxes.com";
    String role = "Employee One Smashing Boxes";



    @FindBy(id = "user_email")
    public static WebElement elEmail;

    public static void enterEmail(String textInput){
        String desc = "Enter $textInput in the email text field";
        Commands.enterText(elEmail, textInput, desc);
    }

    @FindBy(id = "user_password")
    public static WebElement elPass;

    public static void enterPass(String textInput){
        String desc = "Enter $textInput in the email text field";
        Commands.enterText(elPass, textInput, desc);
    }

    @FindBy(name = "commit")
    public static WebElement elLogin;

    public static void clickLogin(){
        String desc = "Click the Login button";
        Commands.click(elLogin, desc);
    }

    //Wait Calls
    public static void waitForUrl() {
        Commands.waitForURL(d, "orangecaregroup");
    }

    public static void waitForEmail() {
        Commands.waitForElement(d, elEmail);
    }

    @FindBy(tagName = "h1")
    public static WebElement elHeader;

    public static void assertHeader(String textInput){
        String desc = "Assert that $textInput is found in header";
        Commands.assertTextContains(elHeader, textInput, desc);
    }

    //Wait calls
    public static void waitForHeader() {
        Commands.waitForElement(d, elHeader);
    }

    @FindBy(id = "menu-header-navigation")
    public static WebElement menu;

    public static void clickDashboard(){
        String desc = "Click on Dashboard";
        Commands.assertInList(menu.findElements(By.tagName("li")), "Dashboard", true, desc);
    }

    @FindBy(className = "menu-item-has-children")
    public static List<WebElement> profileIcon = null;

    public static void clickProfile(){
        String desc = "Click on Profile icon";
        Commands.hoverOver(d, profileIcon.get(0), desc);
//        Commands.click(profileIcon.get(0), desc);
    }

    @FindBy(linkText = "PROFILE")
    public static WebElement profile = null;

    public static void clickProfileLink(){
        String desc = "Click on Profile link";
        Commands.click(profile, desc);
    }
}
