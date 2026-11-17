Please proceed with creating the GitHub Pull Request for FPOAP-165.

Repository: aaa-ncnu/accounts-payable-invoice-processing

Base branch: dev

Feature branch: feature/fpoap-165-adhoc-payment-validations

PR Title:
[FPOAP-165] Add ReadyAPI Ad-Hoc Payment validations for UAT 7–10

PR Description:

Summary

This PR adds fixture-driven ReadyAPI SOAP validations for Ad-Hoc Payment UAT Scenarios 7–10 and extracts the existing inline assertion into a reusable Groovy script.

Changes

* Added VerifyAdHocPaymentDetails.groovy.
* Updated the ReadyAPI project XML to invoke the external script.
* Added validation for currency, payment amount, payee, line items, financial coding, and memos.
* UAT 7: Verify APPROVED payment status.
* UAT 8: Verify special handling address.
* UAT 9 and 10: Verify attachment counts and filenames.
* Preserved existing XPath assertions.

Test Results

* Groovy compilation: PASS
* ReadyAPI Smoke suite: PASS
* Python unit tests: 44 passed
* Offline SOAP validation: UAT 7–10 passed

Note: Live Workday Sandbox E2E tests have not been executed.

Jira

FPOAP-165

Before creating the PR:

1. Verify that the base branch is dev.
2. Confirm exactly one feature commit (bf8b25a) and two changed files.
3. Confirm no credentials, local configurations, or unrelated changes are included.

Then:

1. Create the Pull Request.
2. Do NOT merge it.
3. Do NOT modify local files or create additional commits.
4. Do NOT automatically assign reviewers unless required by repository rules.

After creation, provide:

* PR number and URL
* PR title
* Base and feature branches
* CI/check status
* Reviewer assignment status

If PR creation fails because of permissions or authentication, stop and report the error.