package framework;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Commands {

	public static void assertInTable(WebDriver d, String attrType, String attrValue, String uniqueCol, HashMap<String, String> hm, Boolean click, String desc){
		WebElement el = CommandHelpers.getElementBy(d, attrType, attrValue).findElement(By.xpath("/tbody/"));
		List<WebElement> elHeaders = CommandHelpers.getElementsBy(null, el, PropsCommands.xpath, PropsCommands.trth);  //tr[0]/th level (WebElement list of headers)
		List<String> listHeaders = new ArrayList<String>();
		Integer iUniqueCol = -1;
		
		//Get unique column index and create a String list of headers
		for (int i=0; i<elHeaders.size(); i++){
			listHeaders.add(elHeaders.get(i).getText());
			if (iUniqueCol.equals(-1) && elHeaders.get(i).getText() == uniqueCol){
				iUniqueCol = i;
			}
		}
		
		//If there are headers and if there is a unique column index
		if (listHeaders != null && iUniqueCol != -1){
			List<WebElement> elRows = CommandHelpers.getElementsBy(null, el, PropsCommands.xpath, PropsCommands.tr);  //tr level (List of rows)
			Boolean foundRow = false;
			//Loop through all table rows except header row
			for (int i=1; i<elRows.size(); i++){
				List<WebElement> elCells = CommandHelpers.getElementsBy(null, elRows.get(i), PropsCommands.xpath, PropsCommands.td);
				//If cell text equals the expected value for that specific column
				if (elCells.get(iUniqueCol).getText() == hm.get(uniqueCol)){
					foundRow = false;
					//Loop through cells to assert row values against expected values
					for (int ii=0; ii<listHeaders.size(); ii++){
						if (elCells.get(ii).getText() == hm.get(listHeaders.get(ii))){
							foundRow = true;
						} else {
							foundRow = false;
							break;
						}
					}
					//If all values in row match all expected values and if clicking on the row
					if (foundRow && click){
						elRows.get(i).click();
						break;
					}
				}
				
				//If at the last row and expected row is not found, then fail
				//Will need to be revisited for pagination with no search feature
				if (i == elRows.size() - 1 && !foundRow){
					fail("Cannot find expected row");
				}
			}
		}
	}
	
	public static void assertInList(WebDriver d, String attrType, String attrValue, String itemValue, String parentXPATH, Boolean isDatePicker, Boolean click, String desc){
		WebElement el = CommandHelpers.getElementBy(d, attrType, attrValue).findElement(By.xpath(parentXPATH));
		List<WebElement> elItems = CommandHelpers.getElementsBy(d, el, PropsCommands.xpath, PropsCommands.div);
		Boolean foundRow = false;
		
		//Loop through items in list
		for (int i=0; i<elItems.size(); i++){
			if (elItems.get(i).getText().equals(itemValue)){
				foundRow = true;
				
				if (!click){
					assertEquals(elItems.get(i).isSelected(), true);
					CommandHelpers.printSteps(PropsCommands.assertText, desc);
				} else {
					elItems.get(i).click();
					CommandHelpers.printSteps(PropsCommands.click, desc);
				}
				
				break;
			}
		}
		
		if (!foundRow){
			fail("Cannot find " + itemValue + " in the list");
		}
	}
	
	public static void assertRadio(WebDriver d, String attrType, String attrValue, String expValue, Boolean click, String desc){
		List<WebElement> el = CommandHelpers.getElementsBy(d, null, attrType, attrValue);
		for (int i=0; i<el.size(); i++){
			if (el.get(i).getAttribute("value").equalsIgnoreCase(expValue)){
				if (!click){  //if asserting selected value
					assertEquals(el.get(i).isSelected(), true);
					CommandHelpers.printSteps(PropsCommands.assertText, desc);
					break;
				} else {  //else if clicking on an option
					el.get(i).click();
					CommandHelpers.printSteps(PropsCommands.click, desc);
					break;
				}
			}
		}
		
	}
	
	public static void assertText(WebDriver d, String attrType, String attrValue, String expValue, String desc){
		WebElement el = CommandHelpers.getElementBy(d, attrType, attrValue);
		assertEquals(el.getText(), expValue);
		CommandHelpers.printSteps(PropsCommands.assertText, desc);
	}
	
	public static void click(WebDriver d, String attrType, String attrValue, String desc){
		WebElement el = CommandHelpers.getElementBy(d, attrType, attrValue);
		el.click();
		CommandHelpers.printSteps(PropsCommands.click, desc);
	}
	
	public static void enterText(WebDriver d, String attrType, String attrValue, String thisString, String desc){
		WebElement el = CommandHelpers.getElementBy(d, attrType, attrValue);
		el.sendKeys(thisString);
		CommandHelpers.printSteps(PropsCommands.enterText, desc);
	}
	
	public static void selectOption(WebDriver d, String attrType, String attrValue, String getBy, String thisString, Integer thisIndex, String desc){
		WebElement el = CommandHelpers.getElementBy(d, attrType, attrValue);
		CommandHelpers.getSelectOptionBy(d, el, getBy, attrValue, thisIndex);
		CommandHelpers.printSteps(PropsCommands.selectOption, desc);
	}
	
	public static void waitForEl(WebDriver d, String attrType, String attrValue){
		String desc = "Waiting for next element to be visible";
		WebDriverWait wait = new WebDriverWait(d, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommandHelpers.getVisibleElementBy(d, attrType, attrValue)));
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
}
