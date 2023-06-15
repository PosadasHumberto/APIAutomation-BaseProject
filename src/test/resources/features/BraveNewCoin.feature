@BraveNewCoin
Feature: BraveNewCoin API Scenario: POST GetToken

  Rule: When i send a POST request to the endpoint, I receive a  token to make further authenticated calls.

    Scenario: As a user, I can retrieve token when making a valid POST Request
      Given I have a valid API Key for the https://bravenewcoin.p.rapidapi.com URI
      When I send a POST request with a valid TokenRequestBody payload to the /oauth/token endpoint
      Then I can validate I receive a valid token in the response

    Scenario: As a user, when I use an invalid API Key, I get an HTTP Code Status 403
      Given I have an invalid API Key for the https://bravenewcoin.p.rapidapi.com URI
      When I send a POST request with a valid TokenRequestBody payload to the /oauth/token endpoint
      Then I receive an HTTP Code Status 403

    Scenario: As a user, when i send the POST request without "grant_type", I get an HTTP Code Status 400
      Given I have a valid API Key for the https://bravenewcoin.p.rapidapi.com URI
      When I send a POST request without grant_type in it's body to the /oauth/token endpoint
      Then I receive an HTTP Code Status 400
