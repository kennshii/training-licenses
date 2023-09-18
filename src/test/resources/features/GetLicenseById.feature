Feature: Get license by Id

  Background:
    Given the following licenses exist
      | name | website      | licenseType | description     | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST | www.test.com | TRAINING    | testDescription |          | 500.00 | USD      | 1               | 2023-04-28 | FALSE       | 200   |
    And the licenses persist
    And all the licenses have the following credentials
      | username        | password |
      | test@endava.com | testpass |
    And the credentials persist

  Scenario: Get the details of an existing license
    When get license by id request is sent for TEST
    And the license is returned
    Then the license is equal to the existing license
    And return status 200 OK

  Scenario: Attempting to get the details of an unknown license
    When get license by id request is sent for Unknown
    Then return status 404 NOT_FOUND