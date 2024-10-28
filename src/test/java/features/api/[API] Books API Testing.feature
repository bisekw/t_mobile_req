Feature: Books API Testing

Background:
  When User is setting up the configuration for API testing.



  @API
  Scenario: Verify that the Books API consistently returns the correct list of books over multiple requests
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 10 times
    Then [API] Each response should have a status code of 200
    And [API] Each response should contain the expected JSON structure

  @API
  Scenario: Verification of data consistency for the book retrieved by ID
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I request the book by its ID
    Then [API] The book data should match

  @API
  Scenario: Creating a new book with generated data
    Given [API] I have valid authentication credentials and baseurl
    When [API] I create a new book with generated details
    Then [API] The book should be created successfully
    And [API] The response should contain the correct book details

  @API
  Scenario: Updating a single parameter of the book -price
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I update the book's price to a new value
    Then [API] The book should be updated successfully
    And [API] I request the book by its ID
    Then [API] The book data should match

  @API
  Scenario: Updating a single parameter of the book -name
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I update the book's name to a new value
    Then [API] The book should be updated successfully
    And [API] I request the book by its ID
    Then [API] The book data should match

  @API
  Scenario: Updating a single parameter of the book -author
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I update the book's author to a new value
    Then [API] The book should be updated successfully
    And [API] I request the book by its ID
    Then [API] The book data should match

  @API
  Scenario: Updating a single parameter of the book -category
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I update the book's category to a new value
    Then [API] The book should be updated successfully
    And [API] I request the book by its ID
    Then [API] The book data should match

  @API
  Scenario: Updating all parameter of the book
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I update the book with new generated details
    Then [API] The book should be updated successfully
    And [API] The book's details should reflect the new values

  @API
  Scenario: Deleting a book and verifying it has been removed
    Given [API] I have valid authentication credentials and baseurl
    When [API] I request all books from the API 1 times
    And [API] I select a random book from the list
    And [API] I delete the selected book
    Then [API] The book should be deleted successfully
    And [API] The book should no longer be retrievable by ID