package framework;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Commands {

	public static void assertClick(WebElement el, String func, Boolean check, String desc){
		if ((!el.getAttribute("class").contains("checked") && check) || (el.getAttribute("class").contains("checked") && !check)){
			el.click();
		}

		CommandHelpers.printSteps(func, desc);
	}

	public static void assertInTable(WebElement elTable, String uniqueVal, Boolean click){
		List<WebElement> elTableRows = elTable.findElements(By.tagName("tbody")).get(0).findElements(By.tagName("tr"));
		Boolean foundRow = false;

		for (WebElement thisRow : elTableRows){
			if (thisRow.getText().contains(uniqueVal)){
				foundRow = true;
				if (click){ thisRow.click(); }
				break;
			}
		}

		if (!foundRow){ fail(uniqueVal + " was not found in the table."); }
	}
	
	public static void assertInList(List<WebElement> elItems, String itemValue, Boolean click, String desc){
		Boolean foundRow = false;

		//Loop through items in list
		for (int i=0; i<elItems.size(); i++){

			if (elItems.get(i).getText().equals(itemValue)){
				foundRow = true;

				if (!click){
					assertEquals(elItems.get(i).isSelected(), true);
					CommandHelpers.printSteps(PropsCommands.assertInList, desc);
				} else {
					elItems.get(i).click();
					CommandHelpers.printSteps(PropsCommands.click, desc);
				}

				break;
			}
		}

		if (!foundRow){ fail("Cannot find " + itemValue + " in the list"); }
	}

	public static void assertPageSource(WebDriver d, String expValue, String desc){
		if (!d.getPageSource().contains(expValue)){
			assertFalse(true, "[" + expValue + "] is not found in page source.");
		}
		CommandHelpers.printSteps(PropsCommands.assertPageSource, desc);
	}

	public static void assertNotInList(List<WebElement> elItems, String itemValue, String desc){
		Boolean flag = false;

		for (WebElement thisItem : elItems){
			if (thisItem.getText().contains(itemValue)){
				flag = true;
				break;
			}
		}

		if (flag){
			fail("Found " + itemValue + " in the list");
		} else {
			CommandHelpers.printSteps(PropsCommands.assertNotInList, desc);
		}
	}

	public static void assertRadio(List<WebElement> elItems, String expValue, Boolean click, String desc){
		for (WebElement el : elItems){
			if (el.getAttribute("value").equalsIgnoreCase(expValue)){
				if (!click){  //if asserting selected value
					assertEquals(el.isSelected(), true);
					CommandHelpers.printSteps(PropsCommands.assertText, desc);
					break;
				} else {  //else if clicking on an option
					el.click();
					CommandHelpers.printSteps(PropsCommands.click, desc);
					break;
				}
			}
		}
	}
	
	public static void assertTextEquals(WebElement el, String expValue, String desc){
        assertEquals(el.getText(), expValue);
        CommandHelpers.printSteps(PropsCommands.assertText, desc);
    }

    public static void assertTextContains(WebElement el, String expValue, String desc){
		if (!el.getText().contains(expValue)){
			assertFalse(true, "[" + el.getText() + "] doesn't contain the text [" + expValue + "].");
		}
		CommandHelpers.printSteps(PropsCommands.assertTextContains, desc);
	}
	
	public static void click(WebElement el, String desc){
		el.click();
		CommandHelpers.printSteps(PropsCommands.click, desc);
	}
	
	public static void clickOffSet(WebDriver d, WebElement el, Integer xOffset, Integer yOffset, String desc){
		Actions act = new Actions(d);
		act.moveToElement(el).moveByOffset(xOffset, yOffset).click().perform();
		CommandHelpers.printSteps(PropsCommands.clickOffset, desc);
	}
	
	public static void enterText(WebElement el, String thisString, String desc){
		el.clear();
		el.sendKeys(thisString);
		CommandHelpers.printSteps(PropsCommands.enterText, desc);
	}
	
	public static void fileUpload(WebElement el, String fileDir, String desc){
		el.sendKeys(fileDir);
		CommandHelpers.printSteps(PropsCommands.fileUpload, desc);
	}

	public static void hoverOver(WebDriver d, WebElement el, String desc){
		Actions action = new Actions(d);
		action.moveToElement(el).build().perform();
		CommandHelpers.printSteps(PropsCommands.hoverOver, desc);
	}

	public static void initElements(WebDriver d, Class[] classList){
		for (Class thisC : classList){
			PageFactory.initElements(d, thisC);
		}
	}

	public static void showHiddenInput(WebDriver d, WebElement el){
		((JavascriptExecutor) d).executeScript("arguments[0].removeAttribute('style','style')", el);
	}

	public static void uploadInputHidden(WebDriver d, WebElement el, String thisString, String desc){
		showHiddenInput(d, el);
		enterText(el, thisString, desc);
	}

	public static void uploadInputHiddenLoop(WebDriver d, WebElement el, List<String> upStrings){
		showHiddenInput(d, el);
		uploadLoop(el, upStrings);
	}

	public static void uploadLoop(WebElement el, List<String> upStrings){
		for (String thisString : upStrings){
			el.sendKeys(thisString);
			waitForSecs(2000);
		}
	}

	public static void scrollByPosition(WebDriver d, WebElement el, int xPos, int yPos){
		JavascriptExecutor js = ((JavascriptExecutor) d);
		js.executeScript("window.scrollBy(" + el.getLocation().x + xPos + ", " + el.getLocation().y + yPos + ")");
	}

	public static void scrollToEl(WebDriver d, WebElement el){
		JavascriptExecutor jse = (JavascriptExecutor)d;
		jse.executeScript("arguments[0].scrollIntoView()", el);
	}

	public static void scrollToTopOfPage(WebDriver d){
		JavascriptExecutor jse = (JavascriptExecutor)d;
		jse.executeScript("scroll(0, 0);");
	}

	public static void scrollToBottomOfPage(WebDriver d){
		JavascriptExecutor js = ((JavascriptExecutor) d);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollToRightOfPage(WebDriver d){
		JavascriptExecutor js = ((JavascriptExecutor) d);
		js.executeScript("window.scrollTo(document.body.scrollWidth, 0)");
	}

	public static void selectOption(Select el, String thisString, String desc){
		el.selectByVisibleText(thisString);
		CommandHelpers.printSteps(PropsCommands.selectOption, desc);
	}

	public static void selectOption(WebElement el, String thisString, String desc){
		new Select(el).selectByVisibleText(thisString);
		CommandHelpers.printSteps(PropsCommands.selectOption, desc);
	}

	public static void switchToIFrame(WebDriver d, WebElement iFrame, Boolean onIframe){
		if (onIframe){
			d.switchTo().frame(iFrame);
		} else {
			d.switchTo().defaultContent();
		}
	}

	public static void waitForAssert(WebElement el, String thisString){
        Integer i = 0;
        do{
            if (!el.getText().contains(thisString)) {
                Commands.waitForSecs(1000);
                i++;
            } else {
                break;
            }
        } while (i < 10);
    }
	
	public static void waitForEl(WebDriver d, WebElement el){
		String desc = "Waiting for next element to be visible";
		WebDriverWait wait = new WebDriverWait(d, 10);
		wait.until(ExpectedConditions.visibilityOf(el));
		CommandHelpers.printSteps(PropsCommands.waitForEl, desc);
	}
	
	public static void waitForSecs(Integer secs){
		try {
			String desc = "Waiting for " + secs + " milliseconds";
			CommandHelpers.printSteps(PropsCommands.waitForSecs, desc);
			Thread.sleep(secs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void waitForURL(WebDriver d, String urlContain){
		Integer i = 0;
		do{
			if (!d.getCurrentUrl().contains(urlContain)) {
				Commands.waitForSecs(1000);
				i++;
			} else {
				break;
			}
		} while (i < 10);
	}

	public static void waitForNotURL(WebDriver d, String urlNotContain){
		Integer i = 0;
		do{
			if (d.getCurrentUrl().contains(urlNotContain)) {
				Commands.waitForSecs(1000);
				i++;
			} else {
				break;
			}
		} while (i < 10);
	}
}
