Feature: Log into shop admin page

  Scenario: Log into admin page
    Given I am on the shop admin login page
    When I enter credentials
    Then I am logged in
