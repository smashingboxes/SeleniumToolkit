package framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommandHelpers {
	public static WebElement getElementBy(WebDriver d, String attrType, String attrValue){
		switch(attrType){
			case "id": return d.findElement(By.id(attrValue));
			case "name": return d.findElement(By.name(attrValue));
			case "cssSelector": return d.findElement(By.cssSelector(attrValue));
			default: return null;
		}
	}
	
	public static List<WebElement> getElementsBy(WebDriver d, WebElement el, String listTag){
		if (d.equals(null)){
			return el.findElements(By.xpath(listTag));
		} else {
			return d.findElements(By.xpath(listTag));
		}
	}
	
	public static void getSelectOptionBy(WebDriver d, WebElement el, String getBy, String attrValue, Integer attrIndex){
		Select select = new Select(el);
		switch(getBy){
			case "visibleText": select.selectByVisibleText(attrValue);
			case "value": select.selectByValue(attrValue);
			case "index": select.selectByIndex(attrIndex);
		}
	}
	
	public static void printSteps(String action, String desc){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date).toString() + " -- [" + action + "] -- " + desc);
	}
}
