# automation-framework-bdd
This is a sample project to demonstrate how to work with Selenium and cucumber for Java.
It includes:
1. Web automation Selenium
2. mobile automation
3. PDF automation
4. API automation
5. HeadLess Browser Support
6. Selenium Grid Execution Support

## technology 
1. java 8 and above.
2. maven
3. testng
4. selenium
5. Appium
6. Karate
7. Allure reporting

## Features:
1. cross-browser testing like Chrome-MicrosoftEdge, Chrome-Firefox
1. cross-platform testing like API-Web, API-Mobile, Web-Mobile
2. save allure result locally for each run
3. attach screenshot in allure for every step
4. allure result dashboard
5. save screenshot and add to word doc
6. credential encryption using bat file
7. can be run from bat file without IDE
8. separate log for each run
9. anyone can create feature file no technical skill required.

## Data Setup
1. JSON file name should be same as the Feature Name.
2. data set name should be same as Scenario name in the JSON file.

## Initial setups
1. browser name should be mention in the *ENV*.properties file kept in the resources/config/environment folder with key as **browser**.
2. application url should be mentioned in *Env*.properties file kept in the resources/config/environment folder.
3. user details should be mentioned in *Env*-SecureText.properties file kept in the resources/config/environment folder.

## vm arguments
-ea
-Dcukes.environment=UAT
-Dcukes.techstack=LOCAL_CHROME
-Dorg.apache.logging.log4j.level=DEBUG
-Dcukes.selenium.defaultFindRetries=2

(e.g: LOCAL_IE, LOCAL_MSEDGE, LOCAL_FIREFOX, REMOTE_CHROME, REMOTE_IE, REMOTE_MSEDGE, REMOTE_FIREFOX, APPIUM_ANDROID)

## Writing feature file
1. use "@(testdata)" format to read data from config.properties file.
2. use "#(testdata)" to read data from JSON.
3. if you are passing "testdata" then it will be treated as same string.



