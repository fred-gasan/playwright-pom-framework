The Phase 1 live validation results are successful, with all five Ad-Hoc scenarios passing and 226/226 assertions passing.

However, I identified an execution scope violation. Only UAT 8 and UAT 11 were authorized, but UAT 7, 9, and 10 were also executed, resulting in three additional persistent Workday Sandbox transactions.

Before proceeding:

1. Investigate why the scoped execution included all five Ad-Hoc scenarios instead of only UAT 8 and UAT 11.
2. Inspect the temporary scoped CSV, fixture_root override, DataSource configuration, and execution logs.
3. Identify the exact root cause and recommend a reliable pre-execution verification mechanism to prevent this from happening again.
4. Do not execute any additional live tests or attempt to delete the created Workday payments.

PHASE 1 FINALIZATION:

5. Review the five intended FPOAP-182 files:
    * readyapi/uat-scenarios.csv
    * readyapi/ap-e2e-soapui-project.xml
    * readyapi/Scripts/BuildAdHocPaymentRequest.groovy
    * readyapi/Scripts/VerifyAdHocWarnings.groovy
    * readyapi/fixtures/uat/adhoc-payment-uat-scenario-11.json
6. Verify that the live execution did not introduce unintended tracked changes.
7. Confirm that the active branch is feature/fpoap-182/dev.
8. Run git diff –check for the intended changes.
9. Stage only the five explicitly listed files. Do not use git add . or git add -A.
10. Review the staged diff and confirm that no unrelated files, credentials, reports, or generated artifacts are included.
11. Create a local commit with the following message:

[FPOAP-182] Add ReadyAPI Ad-Hoc Payment warnings coverage

12. Verify the resulting commit SHA, committed file list, and remaining working tree status.

IMPORTANT:

* Do not push.
* Do not create a PR.
* Do not implement Phase 2 GET search coverage yet.
* Do not execute additional live API calls.
* Do not modify or delete existing Workday Sandbox transactions.
* Preserve all unrelated local changes and developer-specific files.

Return the execution scope root cause, prevention strategy, commit SHA, committed files, and final Git status.

Stop and wait for further instructions.