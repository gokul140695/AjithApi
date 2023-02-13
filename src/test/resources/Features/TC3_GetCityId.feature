@CityList
Feature: City Module API Automation

  Scenario: Verify user get city list through api
    Given User add headers for city list
    When User add request body for state id 
    And User send "POST" request for city list endpoint
    Then User should verify the status code is 200
    Then User should verify city list response message matches "Kodambakkam" and saved city id
    
