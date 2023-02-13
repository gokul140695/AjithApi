@SearchProduct
Feature: 	Search Product Module API Automation

  Scenario: Verify user search product to the application through api
    Given User add Headers for search product endpoint
    When User add request body for search product "fruits"
    And User send  "POST" request for search product endpoint
    Then User should verify the status code is 200
    Then User should verify the search product response message matches "OK"

  