Feature: User component
  Scenario: list users
    When I GET "/users"
    Then the response status is 200
