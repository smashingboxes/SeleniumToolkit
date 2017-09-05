package frameworkSandbox;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class POM_FindByAnnotation {

    @FindBy(id = "myfile")
    public static WebElement elFile;

    @FindBy(id = "submit")
    public static WebElement elSubmit;
}
