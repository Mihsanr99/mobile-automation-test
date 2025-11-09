Feature: Product Purchase
  As a user
  I want to purchase products
  So that I can complete my shopping

  Scenario: Purchase Sauce Labs Backpack and Bike Light
    Given I open the Sauce Labs app
    When I login with valid credentials
    And I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    And I open the cart
    And I proceed to checkout
    Then I should see order confirmation