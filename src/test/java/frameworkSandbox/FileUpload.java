package frameworkSandbox;

import framework.Commands;
import org.openqa.selenium.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import framework.Drivers;

import java.io.File;

public class FileUpload {

	public static WebDriver d;
	
	@BeforeTest
	public static void beforeTest(){
		DefaultConfig.appAddress = "http://sl-test.herokuapp.com/guinea_pig/file_upload";
		DefaultConfig.runSauceLabs = false;
		d = DefaultConfig.defaultSauceLabsConfig();
//		d = DefaultConfig.defaultConfig();
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
