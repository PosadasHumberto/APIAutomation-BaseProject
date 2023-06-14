Feature: Example of a Request to practice

@API
Scenario: Test GET to the endpoint
  Given I send a GET request to the https://api.github.com URI
  Then I get a 200 status code

@Size
Scenario: Validate entries quantity on response
  Given I send a GET request to the https://jsonplaceholder.typicode.com URI
  Then I validate there are 10 items on the /users endpoint

@ContainsInName
Scenario: Validate response contains an element by name
  Given I send a GET request to the https://jsonplaceholder.typicode.com URI
  Then I validate there is a name Chelsey in the response at /users endpoint

@ContainsInUsername
Scenario: Validate response contains an element by username
  Given I send a GET request to the https://jsonplaceholder.typicode.com URI
  Then I validate there is a username Maxime_Nienow in the response at /users endpoint

@ContainsIsNameOrUsername
Scenario Outline: Validate response contains an element by name or username
  Given I send a GET request to the https://jsonplaceholder.typicode.com URI
  Then I validate there is a value <value> as a <type> in the response at /users endpoint

#Table of Examples
  Examples:
  |value    |type |
  |Clementina  |name |
  |Leopoldo_Corkery | username  |

@ContainsNestedValue
Scenario:
  Given I send a GET request to the https://jsonplaceholder.typicode.com URI
  Then I can validate the nested value Skiles Walks on the response at /users endpoint