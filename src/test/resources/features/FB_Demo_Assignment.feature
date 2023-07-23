@UITest
Feature: To implement the Web automated checks over facebook website

  Scenario: user navigation through groups to do validations
 Given User navigates to "URL" via "chrome" browser
 And User performs login operation
 And User navigates to groups page
 And User opens a suggested group/
 And User validates following
 |Elements|
 |Private    |
 |Visible  |
 |History |
 And user quits the browser
