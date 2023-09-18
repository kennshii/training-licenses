Feature: Add license

  Background:
    Given the following licenses exist
      | name | website      | licenseType | description     | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST | www.test.com | TRAINING    | testDescription |          | 500.00 | USD      | 1               | 2023-04-28 | FALSE       | 200   |
    And the licenses persist
    And all the licenses have the following credentials
      | username        | password |
      | test@endava.com | testpass |
    And the credentials persist

  Scenario Outline: Add license with valid data
    When create license request
      | name   | website   | licenseType   | description   | logo   | cost   | currency   | licenseDuration   | expiresOn   | isRecurring   | seatsTotal   |
      | <name> | <website> | <licenseType> | <description> | <logo> | <cost> | <currency> | <licenseDuration> | <expiresOn> | <isRecurring> | <seatsTotal> |
    And add new credentials for request
      | username         | password  |
      | test1@endava.com | test1pass |
    And send an added license request
    Then return the following license response
      | name   | website   | licenseType   | description   | logo   | cost   | currency   | licenseDuration   | expiresOn   | isRecurring   | seatsTotal   | isActive   | seatsAvailable   |
      | <name> | <website> | <licenseType> | <description> | <logo> | <cost> | <currency> | <licenseDuration> | <expiresOn> | <isRecurring> | <seatsTotal> | <isActive> | <seatsAvailable> |
    And return status 201 CREATED

    Examples:
      | name                | website                        | licenseType | description | logo | cost    | currency | licenseDuration | expiresOn  | isRecurring | seatsTotal | isActive | seatsAvailable |
      | TEST1               | test1.com                      | TRAINING    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST_1              | test1.com                      | TRAINING    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TES                 | test1.com                      | TRAINING    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEeeeeeeeeeeeeeeST1 | test1.com                      | TRAINING    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | t.com                          | TRAINING    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | www.teeeeeeeeeeeeeeeeeeeee.com | TRAINING    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | test1.com                      | SOFTWARE    | TEST1       |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | test1.com                      | SOFTWARE    | TEST1_.,    |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | test1.com                      | SOFTWARE    |             |      | 1.00    | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | test1.com                      | SOFTWARE    |             |      | 9999.00 | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | test1.com                      | SOFTWARE    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 20         | true     | 20             |
      | TEST1               | test1.com                      | SOFTWARE    |             |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 250        | true     | 250            |
      | TEST1               | test1.com                      | SOFTWARE    | TEST1       |      | 499.00  | USD      | 1               | 20-08-2023 | false       | 150        | true     | 150            |
      | TEST1               | test1.com                      | SOFTWARE    | TEST1       |      | 499.00  | USD      | 11              | 20-08-2023 | false       | 150        | true     | 150            |
