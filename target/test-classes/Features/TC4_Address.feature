@Address
Feature: Address Module API Automation

@first
  Scenario Outline: Verify add user address to the application through api
    Given User add Header and bearer authorization for accessing address endpoint
    When User add request body for add new address "<first_name>" , "<last_name>" , "<mobile>" , "<apartment>" , "<country>" , "<zipcode>" , "<address>" and "<address_type>"
    And User send  "POST" request for add user address endpoint
    Then User should verify the status code is 200
    Then User should verify the add user address response message matches "Address added successfully"

    Examples: 
      | first_name | last_name | mobile     | apartment       | country | zipcode | address | address_type |
      | Gokul      | name      | 8907654327 | Gokul Apartment |      30 |  610004 | Chennai | Home         |

  Scenario Outline: Verify update user address to the application through api
    Given User add Header and bearer authorization for accessing update address endpoint
    When User add request body for update user address addressId and "<first_name>" , "<last_name>" , "<mobile>" , "<apartment>" , "<country>" , "<zipcode>" , "<address>" and "<address_type>"
    And User send  "PUT" request for update user address endpoint
    Then User should verify the status code is 200
    Then User verify the update user address response message matches "Address updated successfully"

    Examples: 
      | first_name | last_name | mobile     | apartment       | country | zipcode | address | address_type |
      | Gokul      | name      | 8907654327 | Gokul Apartment |      30 |  610004 | Chennai | Home         |

  Scenario: Verify get user address to the application through api
    Given User add Header and bearer authorization for accessing address endpoint
    When User send  "GET" request for get user address endpoint
    Then User should verify the status code is 200
    Then User verify get user address response message matches "OK"

  Scenario: Verify delete user address to the application through api
    Given User add Header and bearer authorization for accessing address endpoint
    When User add request body for delete user address address_id
    And User send  "DELETE" request for delete user address endpoint
    Then User should verify the status code is 200
    Then User should verify delete user address response message matches "Address deleted successfully"
