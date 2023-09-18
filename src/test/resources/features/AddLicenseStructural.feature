Feature: Add license with invalid data

  Background:
    Given the following licenses exist
      | name | website      | licenseType | description     | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST | www.test.com | TRAINING    | testDescription |          | 500.00 | USD      | 1               | 2023-04-28 | FALSE       | 200   |
    And the licenses persist
    And all the licenses have the following credentials
      | username        | password |
      | test@endava.com | testpass |
    And the credentials persist

  Scenario Outline: Attempting to add license with invalid data
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
#     name should have min 3 chars and max 20 chars
      | TE                    | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |
      | TEEEEEEEEEEEEEEEEEEST | test1.com                       | SOFTWARE    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 100        |
      |                       | test1.com                       | SOFTWARE    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 100        |

#      website should have min 5 chars and max 30 chars
      | TEST1                 | test                            | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |
      | TEST1                 | teeeeeeeeeeeeeeeeeeeeeeeest.com | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |
      | TEST1                 |                                 | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |

#      description should have min 5 chars
      | TEST1                 | test1.com                       | TRAINING    | test            |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |

#      cost should have min 1 numeric char and max 5 numeric chars
      | TEST1                 | test1.com                       | TRAINING    |                 |          | -0.1       | USD      | 1               | 20-08-2023 | false       | 150        |
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 9999999.00 | USD      | 1               | 20-08-2023 | false       | 150        |
      | TEST1                 | test1.com                       | TRAINING    |                 |          |            | USD      | 1               | 20-08-2023 | false       | 150        |

#      seats should be min 20 and max 250
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 19         |
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       | 251        |
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-08-2023 | false       |            |

#      duration should be min 1 month and max 11 months
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 0               | 20-08-2023 | false       | 150        |
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 13              | 20-08-2023 | false       | 150        |
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      |                 | 20-08-2023 | false       | 150        |

#      date should be from future
      | TEST1                 | test1.com                       | TRAINING    |                 |          | 499.00     | USD      | 1               | 20-03-2023 | false       | 150        |

#      logo should be at least 2MB
      | TEST1                 | test1.com                       | TRAINING    |                 | logo     | 499.00     | USD      | 1               | 20-08-2023 | false       | 150        |
