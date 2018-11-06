package frameworkSandbox;

import framework.Commands;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import framework.Drivers;

import java.io.File;

public class FileUpload {

	public static WebDriver d;
	
	@BeforeTest
	public static void beforeTest(){
		File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
		d = Drivers.checkSL("Windows 10", "chrome",
				"http://sl-test.herokuapp.com/guinea_pig/file_upload", file, false, true);
//		d = Drivers.checkSauceLabs("Windows 10", "chrome", "http://sl-test.herokuapp.com/guinea_pig/file_upload", file, false);
//		d = Drivers.firefoxDriver(null, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
//		d = Drivers.firefoxDriver("http://sl-test.herokuapp.com/guinea_pig/file_upload");
//		d = Drivers.safariDriver(null, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
		d.manage().window().maximize();
	}
	
	@Test
	public void testSauce() throws Exception {
		Commands.assertPageSource(d.getPageSource(), "test page", "assert this page source");
		System.out.println(d.getPageSource());

//        WebElement upload = d.findElement(By.id("myfile"));
//        upload.sendKeys("/Users/darrinwhitley/Downloads/057_original_print.jpg");
//        d.findElement(By.id("submit")).click();
//        d.findElement(By.tagName("img"));
//        Assert.assertEquals("057_original_print.jpg (image/jpeg)", d.findElement(By.tagName("p")).getText());
//
//
//        int p = d.findElement(By.tagName("p")).getLocation().x;
//
//		JavascriptExecutor js = ((JavascriptExecutor) d);
//		js.executeScript("window.scrollBy(" + p + 200 + ", 200)");

    }
}
