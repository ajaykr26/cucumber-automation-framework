Feature: Login Validation

  Scenario: Web Automation
    Then the user launches the "facebook" application in a "NewWindow"
    Then the user wait for page to load
    Then the user clicks on the element "register" at the page "FacebookRegistration"
    And the user enters "#(firstname)" into the input field "firstname" at the page "FacebookRegistration"
    And the user enters "#(lastname)" into the input field "lastname" at the page "FacebookRegistration"
    And the user enters "#(mobile)" into the input field "mobile" at the page "FacebookRegistration"
    And the user enters "#(password)" into the input field "password" at the page "FacebookRegistration"

  Scenario: PDF Automation
    Given the user opens the "Resume_Ajay Kumar" pdf kept at "#(pdfFilepath)" path
    And the user validates the orientation is in portrait mode "orientation" "HardStopOnFailure"
    And the user takes a screenshot of the active pdf
    Then the active pdf is closed

  @SmokeTest
  Scenario: Mobile Automation
    Given the user launch the mobile application "facebook"
