Feature: Edit request

  Background:
    Given There are the next roles in the data base
      | name     | description                                                             |
      | ADMIN    | Admin administrates the website.                                        |
      | REVIEWER | Reviewer approves or rejects requests for access to learning resources. |
      | USER     | User can request access to learning resources.                          |
    And the following disciplines exist
      | name              |
      | AM_Test           |
    And the disciplines persist
    And the following positions exist
      | name             |
      | Senior Tester    |
    And the positions persist
    And the following delivery units exist
      | name |
      | MDD  |
    And the delivery units persist
    And the following workplaces exist
      | position         | discipline       | deliveryUnit  |
      | Senior Tester    | AM_Test         |      MDD      |
    And the workplaces persist
    And the following users exist
      | firstName | lastName | registrationDate | isActive | lastActive | position      | discipline | deliveryUnit |
      | Test1     | Test1    | 2023-06-15       | True     | 2023-06-21 | Senior Tester | AM_Test    | MDD          |
    And the users persist
    And the following licenses exist
      | name | website      | licenseType | description     | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | TEST | www.test.com | TRAINING    | testDescription |          | 500.00 | USD      | 1               | 2023-04-28 | FALSE       | 200   |
    And the licenses persist
    And the following requests exist
      | status   | requestedOn              | startOfUse               |
      | PENDING  | 2018-12-10T00:00:00.000Z | 2018-12-10T00:00:00.000Z |
      | PENDING  | 2018-12-10T00:00:00.000Z | 2018-12-10T00:00:00.000Z |
      | REJECTED | 2018-12-10T00:00:00.000Z | 2018-12-10T00:00:00.000Z |
    And the requests have random user and license
    And the requests persist

  Scenario: Update status of requests
    When request to update VALID requests is sent with the new status APPROVED
    Then updated requests are returned with status APPROVED

  Scenario: Update invalid requests status
    When request to update INVALID requests is sent with the new status APPROVED
    Then return status 400 BAD_REQUEST

  Scenario: Get all the requests
    When get request for requests is sent
    Then return status 200 OK
    And the added requests are present

  Scenario: Delete all the requests
    When delete request for requests is sent
    Then return status 200 OK
    And the added requests are not present