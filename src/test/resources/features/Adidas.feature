@APITest 
Feature: To implement the API automated checks over our DEMO PET STORE

  Scenario: Post a new available pet to the store. Assert new pet added. 
 Given User set valid endpoint to "POST_PUT_PET"
 When User send the "Post" request with valid json having following details
    | description     |   Key               | Value             |
    |key to be updated| nameToBeUpdated     | TestProject       |
    |key to be updated| statusToBeUpdated   | Available         | 
      Then Server should return "200" Status
      And  pet id is generated in response 
      
      
      Scenario Outline: Get "available" pets. Assert expected result 
    Given User set valid endpoint to "GET_PETS"
     When User send the Get request with query param
     | description       |   Key               | Value             |
     | param to be passed| status              | available         |
      Then Server should return "200" Status
      And verify response has <status> pets
      Examples:
      |status|
      |available|
      
       Scenario Outline: Update this pet status to "sold". Assert status updated
 Given User set valid endpoint to "POST_PUT_PET"
 When User send the "Putt" request with valid json having following details
    | description     |   Key               | Value             |
    |key to be updated| nameToBeUpdated     | TestProject       |
    |key to be updated| statusToBeUpdated   | sold              | 
      Then Server should return "200" Status
      And  pet id is generated in response 
      And verify response has <status> pets
      Examples:
      |status|
      |sold|
      
      
      
      
      