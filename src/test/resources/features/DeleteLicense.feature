Feature: Delete license

  Background:
    Given the following licenses exist
      | name         | website      | licenseType | description     | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | FANCYLICENSE | www.test.com | TRAINING    | testDescription |          | 500.00 | USD      | 1               | 2023-04-28 | FALSE       | 200   |
    And the licenses persist

  Scenario: Delete license
    When request delete license
    Then return status 200 OK

  Scenario: Delete nonexistent license
    When request delete license on endpoint "/licenses/1338"
    Then return status 404 NOT_FOUND

  Scenario: Delete license with invalid id
    When request delete license on endpoint "/licenses/aa"
    Then return status 400 BAD_REQUEST

