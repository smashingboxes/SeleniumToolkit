package framework;

public interface PropsCommands {

	//Attribute types
	public String id = "id";
	public String name = "name";
	public String cssSelector = "cssSelector";
	public String xpath = "xpath";
	
	//Select getBy types
	public String visibleText = "visibleText";
	public String value = "value";
	public String index = "index";
	
	//Command types
	public String click = "click";
	public String enterText = "enterText";
	public String assertText = "assertText";
	public String assertInList = "assertInList";
	public String selectOption = "selectOption";
	public String waitForSecs = "waitForSecs";
	public String waitForEl = "waitForEl";
	
	//Get Elements types
	public String li = "/li";
	public String th = "/th";
	public String td = "/td";
	public String tr = "/tr";
	public String trth = "/tr[0]/th";
	public String trtd = "/tr/td";
	public String div = "/div/";
}
