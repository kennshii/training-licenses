Feature: Update License With Invalid Data

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

  Scenario Outline: Attempting to update license with invalid data
    When get license by id request is sent for TEST
    And the license is returned
    And update the license
      | name   | website   | licenseType   | description   | logo   | cost   | currency   | licenseDuration   | expiresOn   | isRecurring   | seatsTotal   |
      | <name> | <website> | <licenseType> | <description> | <logo> | <cost> | <currency> | <licenseDuration> | <expiresOn> | <isRecurring> | <seatsTotal> |
    And update license request is sent
    Then return status 400 BAD_REQUEST

    Examples:
      | name                  | website                         | licenseType | description | logo | cost       | currency | licenseDuration | expiresOn  | isRecurring | seatsTotal |
#     name should have min 3 chars and max 20 chars
      | TE                    |                                 |             |             |      |            |          |                 |            |             |            |
      | TEEEEEEEEEEEEEEEEEEST |                                 |             |             |      |            |          |                 |            |             |            |

#      website should have min 5 chars and max 30 chars
      |                       | test                            |             |             |      |            |          |                 |            |             |            |
      |                       | teeeeeeeeeeeeeeeeeeeeeeeest.com |             |             |      |            |          |                 |            |             |            |

#      description should have min 5 chars
      |                       |                                 |             | test        |      |            |          |                 |            |             |            |

#      cost should have min 1 numeric char and max 5 numeric chars
      |                       |                                 |             |             |      | -0.1       |          |                 |            |             |            |
      |                       |                                 |             |             |      | 9999999.00 |          |                 |            |             |            |

#      seats should be min 20 and max 250
      |                       |                                 |             |             |      |            |          |                 |            |             | 19         |
      |                       |                                 |             |             |      |            |          |                 |            |             | 251        |

#      duration should be min 1 month and max 12 months
      |                       |                                 |             |             |      |            |          | 0               |            |             |            |
      |                       |                                 |             |             |      |            |          | 13              |            |             |            |

#      date should be from future
      |                       |                                 |             |             |      |            |          |                 | 20-03-2022 |             |            |

#      logo should be at least 2MB
      |                       |                                 |             |             | logo |            |          |                 |            |             |            |
