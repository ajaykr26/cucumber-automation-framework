Feature: Login Validation
  Validating login functionality

  @SmokeSuite @ScenarioOne
  Scenario: Facebook Registration One
    Then the user launch the "@(facebook)" application in new window
    Then the user wait for page to load
    Then the user validate that the page title exactly matched with "Facebook – log in or sign up"
    Then the user clicks on the element "register" at the page "Facebook_Registration"
    Then the user enter "#(firstname)" into input field having name "firstname"
    Then the user enter "#(lastname)" into input field having xpath "//input[@name='lastname']"
    Then the user enter "#(mobile)" into input field having name "reg_email__"
    Then the user enter "#(password)" into input field having id "password_step_input"
    Then the user select "5" option by text from dropdown having name "birthday_day"
    Then the user select "Apr" option by text from dropdown having id "month"
    Then the user select "2005" option by text from dropdown having name "birthday_year"
    Then the user select "2" option by value from radio button group having xpath "//input[@name='sex']"
    Then the user validates image at "#(logoUrl)" url matches with "logo.png" image file
#
#  @SmokeSuite @ScenarioOne
#  Scenario: Facebook Registration One
#    Given the user opens the "Resume_Ajay Kumar" pdf kept in testdata folder
#    And the user validates the orientation is in portrait mode "orientation" "HardStopOnFailure"
#    And the user takes a screenshot of the active pdf
#    Then the active pdf is closed

#  @SmokeSuite @ScenarioTwo
#  Scenario: Cowin Availability check
#    Then the user launch the "@(cowin)" application in new window
#    Then the user wait for page to load
#    Then the user enter "845401" into input field having xpath "//input[@type='search']"
#    Then the user click on element having xpath "//button[normalize-space()='Search']"
#    Then the user click on element having xpath "//label[normalize-space()='Age 18+']"
#    Then the user validate that text at the element having xpath "//p[text()=' Banjariya, East Champaran, Bihar, 845401 ']/../..//following-sibling::div//span[@class='age-limit' and text()='Age 18+']/../../a" is "not matched" with the value "Booked"
