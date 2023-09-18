Feature: Add license with unique field constraint

  Background:
    Given the following licenses exist
      | name | website      | licenseType | description     | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST | www.test.com | TRAINING    | testDescription |          | 500.00 | USD      | 1               | 2023-04-28 | FALSE       | 200   |
    And the licenses persist
    And all the licenses have the following credentials
      | username        | password |
      | test@endava.com | testpass |
    And the credentials persist

  Scenario Outline: Attempting to add license with duplicate data
    When create license request
      | name   | website   | licenseType   | description   | logo   | cost   | currency   | licenseDuration   | expiresOn   | isRecurring   | seatsTotal   |
      | <name> | <website> | <licenseType> | <description> | <logo> | <cost> | <currency> | <licenseDuration> | <expiresOn> | <isRecurring> | <seatsTotal> |
    And add new credentials for request
      | username         | password  |
      | test1@endava.com | test1pass |
    And send an added license request
    Then return status 400 BAD_REQUEST

    Examples:
      | name                  | website                         | licenseType | description     | logo     | cost       | currency | licenseDuration | expiresOn  | isRecurring | seatsTotal |
#      unique name
      | TEST                  | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |

#      unique website
      | TEST1                 | www.test.com                    | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |

#      unique description
      | TEST1                 | test1.com                       | TRAINING    | testDescription |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |

#      logo should be unique
      | TEST1                 | test1.com                       | TRAINING    |                 | testLogo | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |
