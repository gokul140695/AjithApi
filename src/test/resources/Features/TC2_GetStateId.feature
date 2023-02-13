@StateList
Feature: State Module API Automation

  Scenario: Verify user get state list through api
  Given User add headers for to state list
  When User send "GET" request for state list endpoint
  Then User should verify the status code is 200
  Then User should verify the state list response message matches "Tamil Nadu" and saved state id
    
