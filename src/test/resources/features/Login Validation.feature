Feature: Login Validation
  This is description of feature

  @Web
  Scenario: Web Automation
  This is description of web scenario
    Then the user launches the "@(facebook)" application in a "NewWindow"
    Then the user wait for page to load
    Then the user clicks on the element "register" at the page "FacebookRegistration"
    And the user enters "#(firstname)" into the input field "firstname" at the page "FacebookRegistration"
    And the user enters "#(lastname)" into the input field "lastname" at the page "FacebookRegistration"
    And the user enters "#(mobile)" into the input field "mobile" at the page "FacebookRegistration"

  @PDF
  Scenario: PDF Automation
    Given the user opens the "Resume_Ajay Kumar" pdf kept at "#(pdfFilepath)" path
    And the user validates the orientation is in portrait mode "orientation" "HardStopOnFailure"
    And the user takes a screenshot of the active pdf
    Then the active pdf is closed

  @Mobile
  Scenario: Mobile Automation
    Given the user launches "@(google)" in mobile browser

  @Database
  Scenario: Database Automation
    Given the user launches "@(google)" in mobile browser
