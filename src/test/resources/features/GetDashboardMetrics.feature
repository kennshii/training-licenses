Feature: Get admin dashboard page metrics

  Scenario: Get user overview stats metric
    Given the following disciplines exist
      | name              |
      | AM_Test           |
      | Development_Test  |
      | Testing_Test      |
      | Architecture_Test |
    And the disciplines persist
    And the following positions exist
      | name             |
      | Senior Tester    |
      | Middle Developer |
    And the positions persist
    And the following delivery units exist
      | name |
      | MDD  |
    And the delivery units persist
    And the following workplaces exist
      | position         | discipline       | deliveryUnit  |
      | Senior Tester    | Testing_Test     |      MDD      |
      | Middle Developer | Development_Test |      MDD      |
    And the workplaces persist
    And the following users exist
      | firstName      | lastName         | position         | discipline       | deliveryUnit | registrationDate | isActive | lastActive |
      | John_Test      | Doe_Test         | Senior Tester    | Testing_Test     | MDD          | 2023-02-12       | TRUE     | 2023-05-02 |
      | John_Test      | Wick_Test        | Middle Developer | Development_Test | MDD          | 2022-05-06       | FALSE    | 2022-05-02 |
      | Jack_Test      | Sparrow_Test     | Senior Tester    | Testing_Test     | MDD          | 2023-04-13       | TRUE     | 2023-06-02 |
      | William_Test   | Shakespeare_Test | Middle Developer | Development_Test | MDD          | 2023-06-15       | TRUE     | 2023-06-16 |
      | Ludwig_Test    | Beethoven_Test   | Senior Tester    | Testing_Test     | MDD          | 2022-09-22       | FALSE    | 2023-05-02 |
      | Trafalgar_Test | Lo_Test          | Middle Developer | Development_Test | MDD          | 2022-09-12       | TRUE     | 2023-01-05 |
      | Mozart_Test    | Winston_Test     | Senior Tester    | Testing_Test     | MDD          | 2022-09-25       | TRUE     | 2023-01-05 |
    And the users persist

    When GET request to "/dashboard/user" is sent
    Then the user overview dto is returned
    And return status 200 OK

  Scenario: Get avg. costs per 1 user stats metric
    Given the following disciplines exist
      | name              |
      | AM_Test           |
      | Development_Test  |
      | Testing_Test      |
      | Architecture_Test |
    And the disciplines persist
    And the following positions exist
      | name             |
      | Senior Tester    |
      | Middle Developer |
    And the positions persist
    And the following delivery units exist
      | name |
      | MDD  |
    And the delivery units persist
    And the following workplaces exist
      | position         | discipline       | deliveryUnit  |
      | Senior Tester    | Testing_Test     |      MDD      |
      | Middle Developer | Development_Test |      MDD      |
    And the workplaces persist
    And the following users exist
      | firstName      | lastName         | position         | discipline       | deliveryUnit | registrationDate | isActive | lastActive |
      | John_Test      | Doe_Test         | Senior Tester    | Testing_Test     | MDD          | 2023-02-12       | TRUE     | 2023-05-02 |
      | John_Test      | Wick_Test        | Middle Developer | Development_Test | MDD          | 2022-05-06       | FALSE    | 2022-05-02 |
      | Jack_Test      | Sparrow_Test     | Senior Tester    | Testing_Test     | MDD          | 2023-04-13       | TRUE     | 2023-06-02 |
      | William_Test   | Shakespeare_Test | Middle Developer | Development_Test | MDD          | 2023-06-15       | TRUE     | 2023-06-16 |
      | Ludwig_Test    | Beethoven_Test   | Senior Tester    | Testing_Test     | MDD          | 2022-09-22       | FALSE    | 2023-05-02 |
      | Trafalgar_Test | Lo_Test          | Middle Developer | Development_Test | MDD          | 2022-09-12       | TRUE     | 2023-01-05 |
      | Mozart_Test    | Winston_Test     | Senior Tester    | Testing_Test     | MDD          | 2022-09-25       | TRUE     | 2023-01-05 |
    And the users persist
    And the following licenses exist
      | name                        | website              | licenseType | description | logo     | cost  | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | Figma_Test                  | www.figma.com        | TRAINING    |             |          | 499.0 | USD      | 1               | 2023-12-13 | TRUE        | 150   |
      | Udemy_Test                  | www.udemy.com        | TRAINING    |             |          | 799.0 | USD      | 6               | 2023-10-11 | TRUE        | 250   |
      | Intellij IDEA               | www.intellijidea.com | SOFTWARE    |             |          | 299.0 | USD      | 11              | 2023-04-30 | TRUE        | 20    |
    And the licenses persist
    And the following requests exists
      | license                     | lastName         | firstName      | status   | requestedOn              | startOfUse                |
      | Figma_Test                  | Doe_Test         | John_Test      | APPROVED | 2023-04-30T00:00:00.000Z | 2023-05-05 |
      | Figma_Test                  | Wick_Test        | John_Test      | PENDING  | 2023-04-30T00:00:00.000Z | 2023-08-05 |
      | Figma_Test                  | Sparrow_Test     | Jack_Test      | PENDING  | 2023-04-10T00:00:00.000Z | 2023-05-08 |
      | Figma_Test                  | Beethoven_Test   | Ludwig_Test    | APPROVED | 2023-05-21T00:00:00.000Z | 2023-06-01 |
      | Udemy_Test                  | Shakespeare_Test | William_Test   | APPROVED | 2023-05-21T00:00:00.000Z | 2023-06-01 |
      | Udemy_Test                  | Beethoven_Test   | Ludwig_Test    | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Winston_Test     | Mozart_Test    | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Lo_Test          | Trafalgar_Test | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Shakespeare_Test | William_Test   | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Doe_Test         | John_Test      | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
    And the requests persist

    When GET request to "/dashboard/user/average" is sent
    Then the average costs dto is returned
    And return status 200 OK

  Scenario: Get list of expiring licenses
    Given the following licenses exist
      | name                        | website              | licenseType | description | logo     | cost   | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | Figma_Test                  | www.figma.com        | TRAINING    |             |          | 499.00 | USD      | 1               | 2022-12-13 | TRUE        | 150   |
      | Udemy_Test                  | www.udemy.com        | TRAINING    |             |          | 799.00 | USD      | 6               | 2023-04-11 | TRUE        | 250   |
      | Intellij IDEA               | www.intellijidea.com | SOFTWARE    |             |          | 299.00 | USD      | 11              | 2023-07-30 | TRUE        | 20    |
      | OReilly_Test                | www.oreilly.com      | TRAINING    |             |          | 199.00 | USD      | 3               | 2023-06-15 | TRUE        | 20    |
    And the licenses persist

    When GET request to "/dashboard/licenses/expiring" is sent
    Then the list of expiring dto licenses is returned
    And return status 200 OK

  Scenario: Get list of unused licenses
    Given the following disciplines exist
      | name              |
      | AM_Test           |
      | Development_Test  |
      | Testing_Test      |
      | Architecture_Test |
    And the disciplines persist
    And the following positions exist
      | name             |
      | Senior Tester    |
      | Middle Developer |
    And the positions persist
    And the following delivery units exist
      | name |
      | MDD  |
    And the delivery units persist
    And the following workplaces exist
      | position         | discipline       | deliveryUnit  |
      | Senior Tester    | Testing_Test     |      MDD      |
      | Middle Developer | Development_Test |      MDD      |
    And the workplaces persist
    And the following users exist
      | firstName      | lastName         | position         | discipline       | deliveryUnit | registrationDate | isActive | lastActive |
      | John_Test      | Doe_Test         | Senior Tester    | Testing_Test     | MDD          | 2023-02-12       | TRUE     | 2023-05-02 |
      | John_Test      | Wick_Test        | Middle Developer | Development_Test | MDD          | 2022-05-06       | FALSE    | 2022-05-02 |
      | Jack_Test      | Sparrow_Test     | Senior Tester    | Testing_Test     | MDD          | 2023-04-13       | TRUE     | 2023-06-02 |
      | William_Test   | Shakespeare_Test | Middle Developer | Development_Test | MDD          | 2023-06-15       | TRUE     | 2023-06-16 |
      | Ludwig_Test    | Beethoven_Test   | Senior Tester    | Testing_Test     | MDD          | 2022-09-22       | FALSE    | 2023-05-02 |
      | Trafalgar_Test | Lo_Test          | Middle Developer | Development_Test | MDD          | 2022-09-12       | TRUE     | 2023-01-05 |
      | Mozart_Test    | Winston_Test     | Senior Tester    | Testing_Test     | MDD          | 2022-09-25       | TRUE     | 2023-01-05 |
    And the users persist
    And the following licenses exist
      | name                        | website              | licenseType | description | logo     | cost  | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | Figma_Test                  | www.figma.com        | TRAINING    |             |          | 499.0 | USD      | 1               | 2023-12-13 | TRUE        | 150   |
      | Udemy_Test                  | www.udemy.com        | TRAINING    |             |          | 799.0 | USD      | 6               | 2023-10-11 | TRUE        | 250   |
      | Intellij IDEA               | www.intellijidea.com | SOFTWARE    |             |          | 299.0 | USD      | 11              | 2023-04-30 | TRUE        | 20    |
      | OReilly_Test                | www.oreilly.com      | TRAINING    |             |          | 199.00 | USD     | 3               | 2023-06-15 | TRUE        | 20    |
    And the licenses persist
    And the following requests exists
      | license                     | lastName         | firstName      | status   | requestedOn              | startOfUse |
      | Figma_Test                  | Doe_Test         | John_Test      | APPROVED | 2023-04-30T00:00:00.000Z | 2023-05-05 |
      | Figma_Test                  | Wick_Test        | John_Test      | PENDING  | 2023-04-30T00:00:00.000Z | 2023-08-05 |
      | Figma_Test                  | Sparrow_Test     | Jack_Test      | PENDING  | 2023-04-10T00:00:00.000Z | 2023-05-08 |
      | Figma_Test                  | Beethoven_Test   | Ludwig_Test    | APPROVED | 2023-05-21T00:00:00.000Z | 2023-06-01 |
      | Udemy_Test                  | Shakespeare_Test | William_Test   | APPROVED | 2023-05-21T00:00:00.000Z | 2023-06-01 |
      | Udemy_Test                  | Beethoven_Test   | Ludwig_Test    | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Winston_Test     | Mozart_Test    | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Lo_Test          | Trafalgar_Test | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Shakespeare_Test | William_Test   | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
      | Intellij IDEA               | Doe_Test         | John_Test      | APPROVED | 2023-05-21T00:00:00.000Z | 2023-08-05 |
    And the requests persist

    When GET request to "/dashboard/licenses/unused" is sent
    Then the list of unused dto licenses is returned
    And return status 200 OK

  Scenario: Get licenses costs overview metric
    Given the following licenses exist
      | name          | website    | licenseType | description | logo     | cost | currency | licenseDuration | expiresOn  | isRecurring | seats |
      | test_license1 | test1.com  | SOFTWARE    |             |          | 1500 | USD      | 3               | 2023-04-21 | TRUE        | 20    |
      | test_license2 | test2.com  | TRAINING    |             |          | 1200 | USD      | 3               | 2022-05-21 | TRUE        | 20    |
      | test_license3 | test2.com  | TRAINING    |             |          | 1300 | USD      | 7               | 2023-08-21 | TRUE        | 20    |
      | test_license4 | test4.com  | SOFTWARE    |             |          | 1800 | USD      | 5               | 2023-09-02 | TRUE        | 20    |
      | test_license5 | test5.com  | SOFTWARE    |             |          | 2000 | USD      | 3               | 2022-05-18 | TRUE        | 20    |
      | test_license6 | test6.com  | SOFTWARE    |             |          | 2300 | USD      | 12              | 2022-12-21 | TRUE        | 20    |
    And the licenses persist
    And the following license_history data are updated
      | startDate  | license       |
      | 2023-01-20 | test_license1 |
      | 2022-02-12 | test_license2 |
      | 2023-01-21 | test_license3 |
      | 2023-04-02 | test_license4 |
      | 2022-02-05 | test_license5 |
      | 2022-12-21 | test_license6 |

    When GET request to "/dashboard/costs/licenseCost" is sent
    Then the costs overview dto is returned
    And return status 200 OK