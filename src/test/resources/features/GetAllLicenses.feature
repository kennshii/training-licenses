Feature: Get license detailed list

  Scenario Outline: Get the detailed list of licenses
    Given the following licenses exist
      | name   | website   | licenseType   | description   | logo   | cost   | currency   | licenseDuration   | expiresOn   | isRecurring   | seats   |
      | <name> | <website> | <licenseType> | <description> | <logo> | <cost> | <currency> | <licenseDuration> | <expiresOn> | <isRecurring> | <seats> |
    And the licenses persist
    And all the licenses have the following credentials
      | username      | password |
      | test@endava.com | test123  |
    And the credentials persist
    When GET request to "/licenses" is sent
    Then the license detailed list is returned

    Examples:
      | name | website      | licenseType | description | logo | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST | www.TEST.com | TRAINING    |             |      | 499.00 | USD      | 1               | 2023-04-20 | false       | 150   |
      | TEST | www.TEST.com | SOFTWARE    | TEST1       |      | 499.00 | USD      | 1               | 2023-04-20 | false       | 150   |
      | TEST | www.TEST.com | SOFTWARE    |             |      | 499.00 | USD      | 1               | 2023-04-20 | false       | 150   |
      | TEST | www.TEST.com | SOFTWARE    | TEST2       |      | 499.00 | USD      | 1               | 2023-04-20 | false       | 150   |


