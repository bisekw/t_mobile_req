Feature: Calling Agify.io API

Background:
  When Użytkownik przygotowuje konfigurację pod testy API
  @RUN
  Scenario: Calling API with name "Jon"
    Given The client makes a GET request with parameter "name" set to "Jon"
    Then I receive a response with status code 200
    And The response contains the expected name "Jon"

