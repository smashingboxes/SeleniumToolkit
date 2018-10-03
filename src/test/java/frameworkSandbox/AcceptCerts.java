package frameworkSandbox;

import framework.Drivers;
import framework.PropsSystem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.Driver;

public class AcceptCerts {

    public static WebDriver d;
    public static File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");

    @BeforeTest
    public static void beforeTest(){
        String app = "https://ec2-18-191-60-177.us-east-2.compute.amazonaws.com";
//        d = Drivers.newSafariDriver("https://ec2-18-191-60-177.us-east-2.compute.amazonaws.com");
//        d = Drivers.safariDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.firefoxDriver(d, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.chromeDriver(null, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
        d = Drivers.checkSauceLabs("Windows 10", PropsSystem.safari, app, file, false);
        d.manage().window().maximize();
    }

    @Test
    public void testSauce() throws Exception {

//        WebElement upload = d.findElement(By.id("myfile"));
//        upload.sendKeys("/Users/darrinwhitley/Downloads/057_original_print.jpg");
//        d.findElement(By.id("submit")).click();
//        d.findElement(By.tagName("img"));
//        Assert.assertEquals("057_original_print.jpg (image/jpeg)", d.findElement(By.tagName("p")).getText());
    }
}
