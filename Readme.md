# automation-framework-bdd
This is a sample project to demonstrate how to work with Selenium and cucumber for Java.

## Data Setup
1. JSON file name should be same as the Feature Name.
2. data set name should be same as Scenario name in the JSON file.

## Initial setups
1. browser name should be mention in the config.properties file kept in the resources/config folder with key as **browser**.
2. application url should be mentioned in config.properties file kept in the resources/config folder.
3. user details should be mentioned in config.properties file kept in the resources/config folder.

## vm arguments
-ea
-Dcukes.environment=UAT
-Dcukes.techStack=LOCAL_CHROME
-Dorg.apache.logging.log4j.level=DEBUG
-Dcukes.selenium.defaultFindRetries=2

## requirement
1. java 8 and above.
2. maven
3. testng
4. selenium

## Writing feature file
1. use "@(testdata)" format to read data from config.properties file.
2. use "#(testdata)" to read data from JSON.
3. if you are passing "testdata" then it will be treated as same string.

## To implement
1. cross browser testing
2. save allure result os each run
3. save screenshot and add to word doc
4. screenshot on every step
5. allure result dashboard

