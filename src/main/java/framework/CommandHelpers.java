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
			case "xpath": return d.findElement(By.xpath(attrValue));
			default: return null;
		}
	}
	
	public static List<WebElement> getElementsBy(WebDriver d, WebElement el, String attrType, String attrValue){
		if (d.equals(null)){
			switch(attrType){
				case "id": return el.findElements(By.id(attrValue));
				case "name": return el.findElements(By.name(attrValue));
				case "cssSelector": return el.findElements(By.cssSelector(attrValue));
				case "xpath": return el.findElements(By.xpath(attrValue));
				default: return null;
			}
		} else {
			switch(attrType){
				case "id": return d.findElements(By.id(attrValue));
				case "name": return d.findElements(By.name(attrValue));
				case "cssSelector": return d.findElements(By.cssSelector(attrValue));
				case "xpath": return d.findElements(By.xpath(attrValue));
				default: return null;
			}
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
