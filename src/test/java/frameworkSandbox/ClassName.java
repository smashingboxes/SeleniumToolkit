package frameworkSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import framework.Commands;
import framework.Drivers;

@Listeners(CheckResultsListener.class)
public class ClassName {

	public static String thisURL = "https://developer.mozilla.org/en-US/docs/Web/HTML/Element/select";
	public static String thisURL1 = "https://amazon.com";
	public static String thisURL2 = "https://woot.com";
	public static String thisURL3 = "http://www.cnn.com";
	public static String thisURL4 = "https://xkcd.com/";
	public static String cssDropdown = "#wikiArticle > p:nth-child(10) > select";
	public static String idComic = "comic";
	public static String cssLeftNavList = "#quick-links";
	public static String cssTopNavDivList = "#nav-xshop";
	public static String cssTopNavNavList = "#global-header > div > nav.categories.with-promo";
	public static String cssNewsList = "#homepage1-zone-1 > div.l-container > div > div.column.zn__column--idx-1.vidfix > ul";
	public static String linkTextString = "Today's Deals";
	public static String classNamePrice = "price-holder";
	
	public static WebDriver d;
	
	@BeforeTest
	public static void beforeTest(){
//		d = Drivers.chromeDriver(null, thisURL2);
//		d = Drivers.chromeDriver(thisURL4, true);
		d = Drivers.firefoxDriver(thisURL4, true);
	}
	
	@Test
	public static void test(){
//		Commands.waitForEl(d, PropsCommands.cssSelector, cssNewsList);
//		Commands.click(d, PropsCommands.className, classNamePrice, "This Works");
		Commands.waitForURL(d, "xkcd");
		Commands.waitForSecs(3000);
		Commands.printStep("actionOne", "Step One");
		Commands.printStep("actionTwo", "Step Two");
		Commands.printStep("actionThree", "Step Three");
//		d.findElement(By.cssSelector(cssDropdown));
		d.findElement(By.id(idComic));
//		Commands.selectOption(d, PropsCommands.cssSelector, cssDropdown, PropsCommands.visibleText, "Value 3", null, null);
//		String[] tagNames = {"article"};
//		Commands.assertInList(d, PropsCommands.cssSelector, cssNewsList, "Thousands leave NC island after power loss", tagNames, true, null);
	}
}
