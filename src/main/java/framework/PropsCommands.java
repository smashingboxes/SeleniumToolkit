package framework;

public interface PropsCommands {

	//Command types
	String click = "click";
	String enterText = "enterText";
	String assertText = "assertText";
	String assertTextContains = "assertTextContains";
	String assertInList = "assertInList";
	String assertPageSource = "assertPageSource";
	String assertNotInList = "assertNotInList";
	String clickOffset = "clickOffset";
	String fileUpload = "fileUpload";
	String hoverOver = "hoverOver";
	String selectOption = "selectOption";
	String waitForSecs = "waitForSecs";
	String waitForEl = "waitForEl";
}
