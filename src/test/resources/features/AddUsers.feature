Feature: Get all users

  Background:
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

  Scenario: Get all users from the data base
    When Admin uses /users endpoint
    Then It should receive the list of all the users from the data base
    And These users should be the users defined above
    And Inserted data should be deleted from the data base

  Scenario: Change users' role for existing users and roles
    When Admin uses /users endpoint
    Then It should receive the list of all the users from the data base
    When Admin decides to change returned users' role to REVIEWER
    And It changes users' roles
    And Admin uses /users/roles endpoint
    Then It should receive 200 status
    And Users' roles should be changed
    And Inserted data should be deleted from the data base

  Scenario: Change users' role for existing users to ADMIN
    When Admin uses /users endpoint
    Then It should receive the list of all the users from the data base
    When Admin decides to change returned users' role to ADMIN
    And It changes users' roles
    And Admin uses /users/roles endpoint
    Then It should receive 400 status
    And Inserted data should be deleted from the data base

  Scenario: Change users' role for nonexistent users
    When Admin uses /users endpoint
    Then It should receive the list of all the users from the data base
    When Admin decides to change returned users' role to REVIEWER
    And It changes users' roles
    And Admin uses an invalid user id
    And Admin uses /users/roles endpoint
    Then It should receive 400 status
    And Inserted data should be deleted from the data base

