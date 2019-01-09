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
	 * Ensures that the {@param element} is checked regardless if it is already checked or not. If it is already
	 * checked, then do nothing. If it is not checked, then click on {@param element} to check it.
	 *
	 * Note: This isn't typically used and may need to be either removed or refactored in the future
	 *
	 * @param  element  the web element that is to be asserted
	 * @param  func 	TODO: FIGURE OUT WHAT THIS IS
	 * @param  check 	the intention of the assertion: true if expecting element to be checked, false if expecting
	 *                     element to not be checked
	 * @param  desc 	short description of the action being done
	 * @return      	void
	 */
	public static void assertClick(WebElement element, String func, Boolean check, String desc){
		CommandHelpers.printSteps(func, desc);
		if ((!element.getAttribute("class").contains("checked") && check)
				|| (element.getAttribute("class").contains("checked") && !check)){
			element.click();
		}
	}

	/**
	 * Asserts that a given {@param uniqueVal} is found in a row in the given {@param elementTable}.
	 *
	 * Note: This method only searches by one {@param uniqueVal}. This may need to be refactored in order to be able to
	 * 		handle assertions with multiple values on the same row
	 * Note: May also need to be updated since it doesn't have a description parameter and doesn't print the steps
	 *
	 * @param  elementTable		the table web element that is to be asserted
	 * @param  uniqueVal 		the unique value to be found in the table; this is functionally the unique id or string
	 *                          	in the table itself
	 * @param  click 			the intention of the assertion: true if the row should be clicked on, false if the row
	 *                             should not be clicked on (simply assert that the value is present in the table)
	 * @return      			void
	 */
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

	/**
	 * Asserts that a given {@param listItemValue} is found in the given {@param elementList}
	 *
	 * @param  elementList		the list web element that is to be asserted
	 * @param  listItemValue 	the unique value to be found in the list
	 * @param  click 			the intention of the assertion: true if the intended list item should be selected,
	 *                             false if the intended list item should simply be asserted in the list
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void assertInList(List<WebElement> elementList, String listItemValue, Boolean click, String desc){
		Boolean foundRow = false;

		//Loop through items in list
		for (int i=0; i<elementList.size(); i++){

			if (elementList.get(i).getText().equals(listItemValue)){
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

		if (!foundRow){ fail("Cannot find " + listItemValue + " in the list"); }
	}

	/**
	 * Asserts that the {@param expectecValue} is found anywhere in the given page {@param pageSource}. It is basically a
	 * lazy assert instead of asserting the value at a particular web element. This should be used only when it is
	 * certain that the value will appear on the page one time.
	 *
	 * @param  pageSource		the entire page source
	 * @param  expectedValue 	the unique value to be found in the page source
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void assertPageSource(String pageSource, String expectedValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertPageSource, desc);
		if (!pageSource.contains(expectedValue)){
			Assert.fail("[" + expectedValue + "] is not found in page pageSource.");
		}
	}

	/**
	 * Asserts that a given {@param listItemValue} is NOT found in the given {@param elementList}
	 *
	 * @param  elementList		the list web element that is to be asserted
	 * @param  listItemValue 	the unique value to NOT be found in the list
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void assertNotInList(List<WebElement> elementList, String listItemValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertNotInList, desc);

		for (WebElement thisItem : elementList){
			if (thisItem.getText().contains(listItemValue)){
				Assert.fail("Found [" + listItemValue + "] in the list");
				break;
			}
		}
	}

	/**
	 * Asserts that the given radio {@param elementList} contains an item with the given {@param expectedValue}
	 *
	 * @param  elementList		the list web element that is to be asserted
	 * @param  expectedValue 	the value to be associated to an item in the list
	 * @param  click            the intention of the assertion: true if the intended list item should be selected,
	 *                             false if the intended list item should simply be asserted in the list
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
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

	/**
	 * Asserts that the given {@param element} text equals the text of the given {@param expectedValue}
	 *
	 * @param  element			the web element that is to be asserted
	 * @param  expectedValue 	the value expected to be found in the web element
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void assertTextEquals(WebElement element, String expectedValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertText, desc);
		Assert.assertEquals(element.getText(), expectedValue);
    }

	/**
	 * Asserts that the given {@param element} text contains the text of the given {@param expectedValue}
	 *
	 * @param  element			the web element that is to be asserted
	 * @param  expectedValue 	the value expected to be found in the web element
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
    public static void assertTextContains(WebElement element, String expectedValue, String desc){
		CommandHelpers.printSteps(PropsCommands.assertTextContains, desc);
		if (!element.getText().contains(expectedValue)){
			Assert.fail("[" + element.getText() + "] doesn't contain the text [" + expectedValue + "].");
		}
	}

	/**
	 * Clicks on the given {@param element}
	 *
	 * @param  element			the web element that is to be asserted
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void click(WebElement element, String desc){
		CommandHelpers.printSteps(PropsCommands.click, desc);
    	element.click();
	}

	/**
	 * Clicks on the given {@param element} but at the position determined by the given {@param xOffset} and
	 * {@param yOffset} conditions. When Selenium executes the click action, it clicks in the exact center of the
	 * specified web element. This method allows for clicking the web element at any position.
	 *
	 * @param  driver			the web driver through which the action will take place
	 * @param  element			the web element that is to be clicked
	 * @param  xOffset			the number of pixels away from the center of the element on the x-axis where the click
	 *                          	should take place (positive number is to the right, negative is to the left)
	 * @param  yOffset			the number of pixels away from the center of the element on the y-axis where the click
	 *                          	should take place (positive number is above, negative is below)
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void clickOffSet(WebDriver driver, WebElement element, Integer xOffset, Integer yOffset, String desc){
		CommandHelpers.printSteps(PropsCommands.clickOffset, desc);
		Actions act = new Actions(driver);
		act.moveToElement(element).moveByOffset(xOffset, yOffset).click().perform();
	}

	/**
	 * Enters the given {@param text} into the specified {@param element}
	 *
	 * @param  element			the web element that is to have text entered into
	 * @param  text				the text to be entered into the web element
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void enterText(WebElement element, String text, String desc){
		CommandHelpers.printSteps(PropsCommands.enterText, desc);
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * Uploads an individual file based upon the given {@param filePath}
	 *
	 * @param  elementUpload	the web element that is to have a file uploaded to
	 * @param  filePath			the file path of the file that is to be uploaded
	 * @param  desc 			short description of the action being done
	 * @return      			void
	 */
	public static void fileUpload(WebElement elementUpload, String filePath, String desc){
		CommandHelpers.printSteps(PropsCommands.fileUpload, desc);
		elementUpload.sendKeys(filePath);
	}

	/**
	 * Perform a mouse hover over a particular {@param element}
	 *
	 * @param  driver		the web driver through which the action will take place
	 * @param  element		the element that will be hovered over
	 * @param  desc 		short description of the action being done
	 * @return      		void
	 */
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
		js.executeScript("window.scrollBy(" + element.getLocation().x + xPos + ", "
				+ element.getLocation().y + yPos + ")");
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
