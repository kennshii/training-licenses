Feature: Update License With Unique Constraint

  Background:
    Given the following licenses exist
      | name  | website       | licenseType | description      | logo | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST  | www.test.com  | TRAINING    | testDescription  |      | 500.00 | USD      | 1               | 2023-09-28 | FALSE       | 200   |
      | TEST2 | www.test2.com | TRAINING    | testDescription2 |      | 500.00 | USD      | 1               | 2023-09-28 | FALSE       | 200   |
    And the licenses persist
    And all the licenses have the following credentials
      | username        | password |
      | test@endava.com | testpass |
    And the credentials persist

  Scenario Outline: Attempting to update license with duplicated data
    When get license by id request is sent for TEST
    And the license is returned
    And update the license
      | name   | website   | licenseType   | description   | logo   | cost   | currency   | licenseDuration   | expiresOn   | isRecurring   | seatsTotal   |
      | <name> | <website> | <licenseType> | <description> | <logo> | <cost> | <currency> | <licenseDuration> | <expiresOn> | <isRecurring> | <seatsTotal> |
    And update license request is sent
    Then return status 400 BAD_REQUEST

    Examples:
      | name  | website       | licenseType | description      | logo | cost | currency | licenseDuration | expiresOn | isRecurring | seatsTotal |
#     name should be unique
      | TEST2 |               |             |                  |      |      |          |                 |           |             |            |
#     website should be unique
      |       | www.test2.com |             |                  |      |      |          |                 |           |             |            |
#     description should be unique
      |       |               |             | testDescription2 |      |      |          |                 |           |             |            |
