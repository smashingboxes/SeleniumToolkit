package framework;

import static org.testng.Assert.assertEquals;
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
import org.testng.Assert;

public class Commands {

	/**
	 * Returns current stuff's value.
	 *
	 * @return Current stuff's value.
	 */
	public static void assertClick(WebElement element, String func, Boolean check, String desc){
		CommandHelpers.printSteps(func, desc);
		if ((!element.getAttribute("class").contains("checked") && check)
				|| (element.getAttribute("class").contains("checked") && !check)){
			element.click();
		}
	}

	public static void assertInTable(WebElement elementTable, String uniqueVal, Boolean click){
		List<WebElement> elTableRows = elementTable.findElements(By.tagName("tbody"))
				.get(0).findElements(By.tagName("tr"));
		Boolean foundRow = false;

		for (WebElement thisRow : elTableRows){
			if (thisRow.getText().contains(uniqueVal)){
				foundRow = true;
				if (click){ thisRow.click(); }
				break;
			}
		}

		if (!foundRow){ Assert.fail("[" + uniqueVal + "] was not found in the table.");}
	}
	
	public static void assertInList(List<WebElement> elementList, String itemValue, Boolean click, String desc){
		Boolean foundRow = false;

		//Loop through items in list
		for (int i=0; i<elementList.size(); i++){

			if (elementList.get(i).getText().equals(itemValue)){
				foundRow = true;

				if (!click){
					CommandHelpers.printSteps(PropsCommands.assertInList, desc);
					assertEquals(elementList.get(i).isSelected(), true);
				} else {
					CommandHelpers.printSteps(PropsCommands.click, desc);
					elementList.get(i).click();
				}

				break;
			}
		}

		if (!foundRow){ fail("Cannot find " + itemValue + " in the list"); }
	}

	public static void assertPageSource(String source, String expValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertPageSource, desc);
		if (!source.contains(expValue)){
			Assert.fail("[" + expValue + "] is not found in page source.");
		}
	}

	public static void assertNotInList(List<WebElement> elementList, String itemValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertNotInList, desc);
		Boolean flag = false;

		for (WebElement thisItem : elementList){
			if (thisItem.getText().contains(itemValue)){
				flag = true;
				break;
			}
		}

		if (flag){
			Assert.fail("Found [" + itemValue + "] in the list");
		}
	}

	public static void assertRadio(List<WebElement> elementList, String expectedValue, Boolean click, String desc){
		for (WebElement el : elementList){
			if (el.getAttribute("value").equalsIgnoreCase(expectedValue)){
				if (!click){  //if asserting selected value
					CommandHelpers.printSteps(PropsCommands.assertText, desc);
					Assert.assertEquals(el.isSelected(), true);
					break;
				} else {  //else if clicking on an option
					CommandHelpers.printSteps(PropsCommands.click, desc);
					el.click();
					break;
				}
			}
		}
	}
	
	public static void assertTextEquals(WebElement element, String expectedValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertText, desc);
		Assert.assertEquals(element.getText(), expectedValue);
    }

    public static void assertTextContains(WebElement element, String expectedValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertTextContains, desc);
		if (!element.getText().contains(expectedValue)){
			Assert.fail("[" + element.getText() + "] doesn't contain the text [" + expectedValue + "].");
		}
	}
	
	public static void click(WebElement element, String desc){
		CommandHelpers.printSteps(PropsCommands.click, desc);
    	element.click();
	}
	
	public static void clickOffSet(WebDriver driver, WebElement element, Integer xOffset, Integer yOffset, String desc){
		CommandHelpers.printSteps(PropsCommands.clickOffset, desc);
		Actions act = new Actions(driver);
		act.moveToElement(element).moveByOffset(xOffset, yOffset).click().perform();
	}
	
	public static void enterText(WebElement element, String thisString, String desc){
		CommandHelpers.printSteps(PropsCommands.enterText, desc);
		element.clear();
		element.sendKeys(thisString);
	}
	
	public static void fileUpload(WebElement element, String fileDir, String desc){
		element.sendKeys(fileDir);
		CommandHelpers.printSteps(PropsCommands.fileUpload, desc);
	}

	public static void hoverOver(WebDriver driver, WebElement element, String desc){
		CommandHelpers.printSteps(PropsCommands.hoverOver, desc);
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public static void initElements(WebDriver driver, Class[] classList){
		for (Class thisClass : classList){
			PageFactory.initElements(driver, thisClass);
		}
	}

	public static void showHiddenInput(WebDriver driver, WebElement element){
		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('style','style')", element);
	}

	public static void uploadInputHidden(WebDriver driver, WebElement element, String thisString, String desc){
		showHiddenInput(driver, element);
		enterText(element, thisString, desc);
	}

	public static void uploadInputHiddenLoop(WebDriver driver, WebElement element, List<String> upStrings){
		showHiddenInput(driver, element);
		uploadLoop(element, upStrings);
	}

	public static void uploadLoop(WebElement element, List<String> upStrings){
		for (String thisString : upStrings){
			element.sendKeys(thisString);
			waitForSecs(2000);
		}
	}

	public static void scrollByPosition(WebDriver driver, WebElement element, int xPos, int yPos){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(" + element.getLocation().x + xPos + ", " + element.getLocation().y + yPos + ")");
	}

	public static void scrollToEl(WebDriver driver, WebElement element){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
	}

	public static void scrollToTopOfPage(WebDriver driver){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, 0);");
	}

	public static void scrollToBottomOfPage(WebDriver driver){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollToRightOfPage(WebDriver driver){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(document.body.scrollWidth, 0)");
	}

	public static void selectOption(Select elementSelect, String thisString, String desc){
		CommandHelpers.printSteps(PropsCommands.selectOption, desc);
		elementSelect.selectByVisibleText(thisString);
	}

	public static void selectOption(WebElement element, String thisString, String desc){
		CommandHelpers.printSteps(PropsCommands.selectOption, desc);
		new Select(element).selectByVisibleText(thisString);
	}

	public static void switchToIFrame(WebDriver driver, WebElement iFrame, Boolean onIframe){
		if (onIframe){
			driver.switchTo().frame(iFrame);
		} else {
			driver.switchTo().defaultContent();
		}
	}

	public static void waitForAssert(WebElement element, String thisString){
        Integer i = 0;
        do{
            if (!element.getText().contains(thisString)) {
                Commands.waitForSecs(1000);
                i++;
            } else {
                break;
            }
        } while (i < 10);
    }
	
	public static void waitForEl(WebDriver driver, WebElement element){
		String desc = "Waiting for next element to be visible";
		CommandHelpers.printSteps(PropsCommands.waitForEl, desc);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
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

	public static void waitForURL(WebDriver driver, String urlContain){
		Integer i = 0;
		do{
			if (!driver.getCurrentUrl().contains(urlContain)) {
				Commands.waitForSecs(1000);
				i++;
			} else {
				break;
			}
		} while (i < 10);
	}

	public static void waitForNotURL(WebDriver driver, String urlNotContain){
		Integer i = 0;
		do{
			if (driver.getCurrentUrl().contains(urlNotContain)) {
				Commands.waitForSecs(1000);
				i++;
			} else {
				break;
			}
		} while (i < 10);
	}
}
