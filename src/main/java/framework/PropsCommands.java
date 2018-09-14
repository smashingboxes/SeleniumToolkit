package framework;

public interface PropsCommands {

	//Attribute types
	String id = "id";
	String name = "name";
	String cssSelector = "cssSelector";
	String xpath = "xpath";
	String linkText = "linkText";
	String className = "className";
	
	//Select getBy types
	String visibleText = "visibleText";
	String value = "value";
	String index = "index";
	
	//Command types
	String click = "click";
	String enterText = "enterText";
	String assertText = "assertText";
	String assertTextContains = "assertTextContains";
	String assertInList = "assertInList";
	String clickOffset = "clickOffset";
	String fileUpload = "fileUpload";
	String selectOption = "selectOption";
	String waitForSecs = "waitForSecs";
	String waitForEl = "waitForEl";
	
	//Get Elements types
	String li = "/li";
	String th = "/th";
	String td = "/td";
	String tr = "/tr";
	String trth = "/tr[0]/th";
	String trtd = "/tr/td";
	String div = "div";
}
