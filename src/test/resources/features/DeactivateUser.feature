Feature: Disable user

  Scenario: Disable user
    Given There are the next roles in the data base
      | name     | description                                                             |
      | ADMIN    | Admin administrates the website.                                        |
      | REVIEWER | Reviewer approves or rejects requests for access to learning resources. |
      | USER     | User can request access to learning resources.                          |
    And There are the next positions
      | name             |
      | senior tester    |
      | middle developer |
    And There are the next delivery units
      | name |
      | MDD  |
      | MDD  |
    And There are the next disciplines
      | name        |
      | testing     |
      | development |
    And There are the next workplace
      | position         | discipline  | deliveryUnit |
      | senior tester    | testing     | MDD          |
      | middle developer | development | MDD          |
    And There are the next users
      | firstName | lastName | role     | email                  | registrationDate | isActive | lastActive |
      | Test1     | Test1    | USER     | Test1.Test1@endava.com | 2023-06-15       | True     | 2023-06-21 |
      | Test2     | Test2    | REVIEWER | Test2.Test2@endava.com | 2023-05-21       | True     | 2023-06-15 |
      | Test3     | Test3    | ADMIN    | Test3.Test3@endava.com | 2023-04-20       | True     | 2023-06-28 |
    When Admin uses /users endpoint
    And It uses id of existing active users to disable them
    Then It should receive 200 status
    And Users should have DEACTIVATED status
    When It disables users
    But If it uses an id of nonexistent user
    Then It should receive 404 status
    When It disables users
    And Inserted data should be deleted from the data base

