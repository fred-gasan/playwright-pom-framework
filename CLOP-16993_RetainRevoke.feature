@RoleMatrix-NRUN @CLOP-16993
Feature: CostCenter-update

  Background:
    Given I am on sat-app-page
    Then we wait
    # Manager login
    When we enter-custom "fg-manager1" in the AdminUserName field
    And we wait
    # Pass the encrypted password
    When we read encrypted password from "SAT_PASSWD" and enter in the "AdminUserPassword" field
    And we wait
    Then we click-custom on AdminLoginButton
    And we wait
    When we switch to url "/rolematrix/managerqueue/"
    And we wait
    Then we take "ManagerQueue" screenshot
    And we wait

  Scenario: Verify Retain and Revoke buttons are disabled before selecting entitlement
    Then "Retain" button should be "disabled"
    And "Revoke" button should be "disabled"

  Scenario: Verify Retain and Revoke buttons become enabled after selecting an entitlement
    When we select the checkbox for entitlement "REG-71.ent1"
    And we wait
    Then "Retain" button should be "enabled"
    And "Revoke" button should be "enabled"

  Scenario: Retain action requires a Business Justification to be entered
    When we select the checkbox for entitlement "REG-71.ent1"
    And we wait
    When we click-custom on "Retain" button for entitlement
    And we wait
    Then we should see validation message "Business Justification is required"

  Scenario: Retain action succeeds when Business Justification is provided
    When we select the checkbox for entitlement "REG-71.ent1"
    And we wait
    When we enter-custom "Access still required for current role" in the Business Justification field for entitlement "REG-71.ent1"
    And we click-custom on "Retain" button for entitlement
    And we wait
    Then the Manager Business Justification field for entitlement "REG-71.ent1" should contain "Access still required for current role"

  Scenario: Revoke action auto-populates Business Justification and sets status to Ready for Signoff
    When we select the checkbox for entitlement "REG-71.ent1"
    And we wait
    When we click-custom on "Revoke" button for entitlement
    And we wait
    Then the Manager Business Justification field for entitlement "REG-71.ent1" should contain "Revoke"
    And the record status for entitlement "REG-71.ent1" should be "Ready for Signoff"

  Scenario: Revoke decision does not override the original Cert Action loaded from OIM upload
    When we select the checkbox for entitlement "REG-71.ent1"
    And we wait
    Then the Cert Action for entitlement "REG-71.ent1" should remain "Inappropriate"
    When we click-custom on "Revoke" button for entitlement
    And we wait
    Then the Cert Action for entitlement "REG-71.ent1" should remain "Inappropriate"
