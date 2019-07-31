Feature: Validating coffee order

  Scenario Outline: Creating <type> from <origin>, will be <result>
    When I create an order with <type> from <origin>
    Then The order should be <result>

    Examples:
      | type      | origin   | result   |
      | Espresso  | Colombia | accepted |
      | Pour_over | Colombia | accepted |
      | Espresso  | Ethiopia | accepted |
      | Latte     | Ethiopia | accepted |
      | Pour_over | Ethiopia | accepted |
      | Espresso  | Germany  | rejected |
      | Siphon    | Colombia | rejected |
      | Siphon    | Germany  | rejected |
