@api
Feature: Test the request and reponses for the https://reqres.in/ site

  Scenario: User Registration
    When user enters valid userID and Password
    Then the user should be registered successfully

  Scenario: User Login
    When user enters valid userID and Password to login
    Then the user should be logged-in successfully

  Scenario Outline: Validate the resource
    When user sends the request to fetch the resources for the id "3"
    Then the user should see the id "<id>",name "<name>", year"<year>",color "<color>" and pantone_value "<pantone_value>" in response

    Examples:
      | id | name     | year | color   | pantone_value |
      | 3  | true red | 2002 | #BF1932 | 19-1664       |

  @negative
  Scenario Outline: Validate the response when the resource does not exists
    When user sends the request to fetch the resources for the id "<id>"
    Then the user should see the error code "<errorCode>" in the response
    Examples:
      | id  | errorCode |
      | 89  | 404       |
      | abd | 404       |

