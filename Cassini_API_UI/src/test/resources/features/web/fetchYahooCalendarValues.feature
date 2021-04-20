@regression
Feature: Login
  Capture the login from the yahoo calendar
  As a registered user,
  I want to login to the yahoo website and access the calendar
  so that I can check the values for the selected date

  Background: User is on the home page
    Given I navigate to the Project "Home" page

  @positive
  Scenario: Access the calendar values from the finance section
    And user navigates to the calendar page
    And capture the calendar values
    Then the user should see the calendar values as per the below data table
      | 22 Earnings        |
      | 10 Stock splits    |
      | 5 IPO pricing      |
      | 61 Economic events |

  Scenario: Login with the invalid username and password and check the error message
    When user enters username and clicks the Next button
    Then user should see the error message "Sorry, we don't recognise this email address." on the screen

