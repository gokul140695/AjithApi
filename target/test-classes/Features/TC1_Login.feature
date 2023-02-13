@Login
Feature: Login Module API Automation

  Scenario: Get user logtoken from login endpoint
    Given User add header
    When User add basic authendication for login
    And User send "POST" request for login endpoint
    Then User should verify the status code is 200
    Then User shoud verify login response body firstName present as "GOKULNATH" and get the logtoken saved
