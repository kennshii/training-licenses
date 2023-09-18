Feature: Get roles list

  Scenario: Get the list with required roles, except Admin
    Given The following roles exist
      | name     | description                                                             |
      | ADMIN    | Admin administrates the website.                                         |
      | REVIEWER | Reviewer approves or rejects requests for access to learning resources. |
      | USER     | User can request access to learning resources.                          |
    When The user uses the /roles endpoint
    Then It should receive the list of all the roles from the data base, except
      | name  |
      | Admin |
