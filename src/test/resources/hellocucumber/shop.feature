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

  Scenario: 06 - Check product names and prices and styles
    Given I am on the main shop page
#    When I check product attributes on the main page
    Then The price and name are the same on the product page


# Сценарий лишний, стили проверил в 6 сценарии
#  Scenario: 07 - Check prices styles
#    Given I am on the main shop page
##    When I check product attributes on the main page
#    Then They have the same style on product page


# TODO    Проверка открытия правильной страницы товара
#  1. на главной  и странице товара совпадают текст названия
#    2. на главной и странице товара совпадают цены (обычная и акционная)
#    3. Обычная — серая и зач. а акц. красная и жирная
#    4. акционная крупнее
#
#    + проверить во всех браузерах
