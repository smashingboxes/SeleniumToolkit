package frameworkSandbox;

import framework.Drivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FindByAnnotation {

    public static WebDriver d;

    @BeforeTest
    public void beforeTest(){
        d = Drivers.chromeDriver("http://sl-test.herokuapp.com/guinea_pig/file_upload");
//        d = Drivers.chromeDriver(null, "http://sl-test.herokuapp.com/guinea_pig/file_upload");
        PageFactory.initElements(d, this);
        PageFactory.initElements(d, POM_FindByAnnotation.class);
    }

    @Test
    public void testFindByAnnotation() throws Exception {
        POM_FindByAnnotation.elFile.sendKeys("/Users/darrinwhitley/Downloads/057_original_print.jpg");
        POM_FindByAnnotation.elSubmit.click();
        d.findElement(By.tagName("img"));
        Assert.assertEquals("057_original_print.jpg (image/jpeg)", d.findElement(By.tagName("p")).getText());
    }
}
