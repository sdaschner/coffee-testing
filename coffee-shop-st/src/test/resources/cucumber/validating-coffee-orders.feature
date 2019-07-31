Feature: Validating coffee order

  Scenario: Creating valid order
    When I create an order with Espresso from Colombia
    Then The order should be accepted

  Scenario: Creating invalid order, wrong origin
    When I create an order with Espresso from Germany
    Then The order should be rejected

  Scenario: Creating invalid order, wrong type
    When I create an order with Siphon from Colombia
    Then The order should be rejected

  Scenario: Creating invalid order, wrong type and origin
    When I create an order with Siphon from Germany
    Then The order should be rejected
