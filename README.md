# SeleniumToolkit

SeleniumToolkit is a Java library that allows for faster development of functional automation testing scripts with Selenium.

The toolkit handles the following:

* Executing Selenium commands transparently
* Running test scripts in Chrome, Firefox, and Safari with headless option
* Running test scripts in SauceLabs for remote/virtual testing (SauceLabs account required)
* Sending test run results to PractiTest for documentation (PractiTest account required)
* WebDriver version management and configuration according to given parameters
* Printing executed steps in the console in a readable format

The toolkit currently has the following integrations:

* SauceLabs
* PractiTest

Dependencies
* Selenium-RC 1.0-20081010.060147
* Selenium-Java 3.8.1
* JUnit 4.12
* TestNG 6.11
* WebDriverManager 2.2.1
* Selenium Jupiter 2.2.0
* Sauce TestNG 2.1.21

#Toolkit Commands

The Commands.java class contains the bulk of the commands that can be used in the Toolkit. Typically, each command requires a WebElement object and a short Description string. The Selenium action would be done on the WebElement object and the short Description would be used for the console printout to identify which steps have been executed.

Basic example:
Your testing code would look like this:
```
//Method 1 for getting the WebElement object (The Selenium Way)
WebElement paragraph = driver.findElement(By.id("text"));

//OR

//Method 2 for getting the WebElement object (The TestNG Way)
@FindBy(id = "text")
public static WebElement paragraph = null;

//THEN

//Assert that the text found in the given WebElement contains the expected value
Commands.assertTextContains(paragraph, "Foobar", "Asserting that [Foobar] is found in the paragraph WebElement");
```

The SeleniumToolkit method for handling the "assertTextContains" function:
```
//The SeleniumToolkit command:
public static void assertTextContains(WebElement element, String expectedValue, String desc){
  printStep("assertTextContains", desc);
  if (!element.getText().contains(expectedValue)){
    Assert.fail("[" + element.getText() + "] doesn't contain the text [" + expectedValue + "].");
  }
}
```

Here is a list of all of the current SeleniumToolkit commands for quick reference. (Please note that any commands not listed here may be private or not utilized anymore):
* assertClick
* assertInTable
* assertInList
* assertPageSource
* assertNotInList
* assertRadio
* assertTextEquals
* assertTextContains
* click
* clickOffSet
* enterText
* fileUpload
* hoverOver
* printStep
* uploadInputHidden
* uploadInputHiddenLoop
* scrollByPosition
* scrollToElement
* scrollToTopOfPage
* scrollToBottomOfPage
* scrollToRightOfPage
* selectOption
* switchToIFrame
* waitForAssert
* waitForElement
* waitForSeconds
* waitForURL
* waitForNotURL


#SauceLabs Integration (Requires active account)

To utilize the integration, use the following method:
```
WebDriver driver = SauceLabsUtils.sauceLabsConfig(slUser, slPass, browser, platform, appAddress)
```

This method will return the appropriate type of WebDriver based on the given browser ("chrome", "firefox", "safari").


#PractiTest Integration (Requires active account)

To utilize the integration, it would be best to use the following method in a TestNG listener class:
```
PractiTestRequests.executeTestRun(devEmail, apiToken, projectId, testSetId, testId, result)
```

Note: the "result" parameter is the ITestResult object that is provided by TestNG in the listener class. For more info about this class, visit https://www.guru99.com/listeners-selenium-webdriver.html