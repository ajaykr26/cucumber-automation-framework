Feature: Login Validation
  Validating login functionality

  @SmokeSuite @Scenario1
  Scenario: Facebook Registration
    Given the user launch the "facebook" application in new window
    Then the user wait for page to load
    Then the user validate that the page title exactly matched with "Facebook – log in or sign up"
    Then the user enter "#(firstname)" into input field having name "firstname"
    Then the user enter "#(lastname)" into input field having xpath "//input[@name='lastname']"
    Then the user enter "#(mobile)" into input field having name "reg_email__"
    Then the user enter "#(password)" into input field having id "u_0_w"
    Then the user select "5" option by text from dropdown having name "birthday_day"
    Then the user select "Mar" option by text from dropdown having id "month"
    Then the user select "2005" option by text from dropdown having name "birthday_year"
    Then the user select "2" option by value from radio button group having xpath "//input[@name='sex']"
    Then the user validate that the value 5338429106 is present in the column Phone in the table having xpath "//div[@class='ui-grid-contents-wrapper']"
    Then the user validate that the value: 5338429106 is present in the column: Phone in the table where table locator: tableLoc header locator: headerLoc rows locator: rowLoc and column locator colLoc at the page LoginPage
    Then the user click on the cell contains <string> in the table having <string> ".*"