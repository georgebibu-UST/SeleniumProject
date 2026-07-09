Feature: Cart removal

  As a shopper
  I want to remove items from my cart
  So that I can manage my cart contents

  Background:
    Given the catalog is open

  @remove
  Scenario: Add a product and remove it from the cart
    When I search for "shoes"
    And I add the first result to the cart
    Then the cart badge shows 1

    When I open the cart
    Then the cart has 1 line item

    When I remove the item from the cart
    Then the cart has 0 line items
    And the cart badge shows 0