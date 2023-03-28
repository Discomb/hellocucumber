Feature: Shop admin page

  Scenario: 01 - Log into admin page
    Given I am on the shop admin login page
    When I enter credentials
    Then I am logged in

  Scenario: 02 - Go through menu
    Given I am logged into shop admin page
    When I go through menu
    Then Every title has a header

  Scenario: 03 - Check product stickers on the main page
    Given I am on the main shop page
    When I look on product images
    Then I see that every product has only one sticker

  Scenario: 04 - Check sorting of the Countries page
    Given I am on the Countries tab of admin page
    When I check sorting of the countries by name
    Then Countries sorted by name from A to Z

  Scenario: 05 - Check sorting of Zones in Countries with multiple zones
    Given I am on the Countries tab of admin page
    When I check if country has multiple zones
    Then Zones in those countries are sorted by name from A to Z
