The payee assertion correction and all 7 offline tests look good.

Next, let’s prepare a focused live E2E rerun for FPOAP-165, covering UAT scenarios 7–10.

Before execution:

1. Inspect the existing ReadyAPI DataSource and DataSource Loop configuration.
2. Determine the safest way to execute only UAT 7–10 without modifying the original uat-scenarios.csv or unrelated repository files.
3. Verify that the local database and Email Service are still healthy.
4. Confirm the effective environment is dev and all Workday/Apigee targets point to the authorized Sandbox.
5. Confirm that the updated VerifyAdHocPaymentDetails.groovy script is the one ReadyAPI will execute.
6. Explain whether the test runner will stop after a failed scenario and how to capture results for all four scenarios.
7. Confirm the exact command and any temporary files required.
8. Preserve the existing feature branch, PR #234, and all unrelated local changes.

Do not execute live tests, create Workday transactions, modify files, commit, push, or rename branches yet.

Provide a safe execution plan for UAT 7–10 and identify any risks or blockers.