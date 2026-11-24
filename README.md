Proceed with Phase 1 finalization.

1. Investigate why UAT 7, 9, and 10 were executed when only UAT 8 and UAT 11 were authorized.
2. Inspect the scoped CSV, fixture_root override, DataSource configuration, and execution logs.
3. Identify the exact root cause and propose a reliable prevention mechanism for future scoped runs.
4. Confirm the active branch is feature/fpoap-182/dev.
5. Review and stage only these five intended files:

* readyapi/uat-scenarios.csv
* readyapi/ap-e2e-soapui-project.xml
* readyapi/Scripts/BuildAdHocPaymentRequest.groovy
* readyapi/Scripts/VerifyAdHocWarnings.groovy
* readyapi/fixtures/uat/adhoc-payment-uat-scenario-11.json

6. Run git diff –check before staging.
7. Run git diff –cached –check after staging.
8. Review the staged diff and verify that no unrelated changes, credentials, reports, or generated artifacts are included.
9. Create a local commit with the following message:

[FPOAP-182] Add ReadyAPI Ad-Hoc Payment warnings coverage

10. Verify the commit SHA, committed files, and remaining working tree status.

IMPORTANT:

* Do not use git add . or git add -A.
* Do not push or create a PR.
* Do not execute additional live tests.
* Do not modify or delete existing Workday Sandbox transactions.
* Do not start Phase 2 GET implementation.
* Preserve all unrelated local changes.

Return the root cause of the execution scope violation, prevention strategy, commit SHA, committed file list, and final Git status.

If any unexpected changes or blockers are identified, stop before committing and report them.

Wait for further instructions.