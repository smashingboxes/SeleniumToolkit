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

Alright, here is the meat of the SeleniumToolkit:

The Commands.java class contains the bulk of the commands that can be used in the Toolkit. Typically, each command requires a WebElement object and a short Description string. The Selenium action would be done on the WebElement object and the short Description would be used for the console printout to identify which steps have been executed.
