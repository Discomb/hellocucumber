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
