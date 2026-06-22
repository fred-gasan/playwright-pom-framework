@RoleMatrix-NRUN @SAT_20260630
Feature: Datatable Configuration - Current Appropriateness Column

  Background:
    Given I am on sat-app-page
    Then we wait
    When we enter-custom "fg-manager1" in the AdminUserName field
    And we wait
    When we read encrypted password from "SAT_PASSWD" and enter in the "AdminUserPassword" field
    And we wait
    Then we click-custom on AdminLoginButton
    And we wait
    When we switch to url "/rolematrix/mnepreviewqueue/"
    And we wait
    Then we take "ManagerQueue_DatatableConfig" screenshot
    And we wait

  Scenario: Search on Current Appropriateness column returns matching rows
    When we enter "Inappropriate" in the Current Appropriateness search field
    And we wait
    Then all visible rows should have "Inappropriate" in the Current Appropriateness column
    And the table should not be empty

  Scenario: Search on Current Appropriateness column with no match shows empty table
    When we enter "xyz123" in the Current Appropriateness search field
    And we wait
    Then the table should show no results message

  Scenario: Search on Current Appropriateness column is case-insensitive
    When we enter "inappropriate" in the Current Appropriateness search field
    And we wait
    Then all visible rows should have "Inappropriate" in the Current Appropriateness column

  Scenario: Sorting is disabled on Current Appropriateness column
    When we click on the Current Appropriateness column header
    And we wait
    Then the Current Appropriateness column header should not have a sort class applied
    And the table row order should remain unchanged

  Scenario: Sorting still works on other columns
    When we click on the "Mnemonic" column header
    And we wait
    Then the "Mnemonic" column header should have a sort class applied

  Scenario: Search on Current Appropriateness does not cause a timeout
    When we enter "Inappropriate" in the Current Appropriateness search field
    Then the page should remain responsive within 5 seconds
    And the table should not be empty
