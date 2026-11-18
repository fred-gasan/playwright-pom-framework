Replacement Pull Request #237 has been created on GitHub.

  ---

  1. Pull Request Details

   - PR Number: #237 (https://github.com/aaa-ncnu/accounts-payable-invoice-processing/pull/237)
   - PR URL: https://github.com/aaa-ncnu/accounts-payable-invoice-processing/pull/237 (https://github.com/aaa-ncnu/accounts-payable-invoice-processing/pull/237)
   - Title: [FPOAP-165] Add ReadyAPI Ad-Hoc Payment validations for UAT 7–10
   - Base Branch: dev
   - Head Branch: feature/fpoap-165/dev
   - State: open (Not merged)

  ---

  2. Commits & File Changes

  Commits in PR (2 commits):
   1. bf8b25a: [FPOAP-165] Add ReadyAPI Ad-Hoc Payment validations for UAT 7-10
   2. dfd0218: [FPOAP-165] Fix payee reference validation and handle missing status in Ad-Hoc SOAP response

  Files Changed (2 files):
   - readyapi/Scripts/VerifyAdHocPaymentDetails.groovy (+204, -0)
   - readyapi/ap-e2e-soapui-project.xml (+4, -52)

  ---

  3. CI & Check Status

   - Cloud Build Check Run: accounts-payable-invoice-processing-dev (aaa-web-services-dev)
   - Check Run ID: 134960771938 (https://github.com/aaa-ncnu/accounts-payable-invoice-processing/runs/134960771938)
   - Status: completed
   - Conclusion: success 🟢 (All unit tests, packaging, and linting checks passed).

  ---

  4. Verification & Scope Disclosures Included in PR #237

   - Cross-Reference: Explicitly states that PR #237 supersedes PR #234 to align with the /dev branch suffix required for GCP Cloud Build CI triggers.
   - Live E2E Results: Documents the 100% PASS across all 4 Ad-Hoc scenarios (203/203 assertions passed) against the Workday Sandbox Company 100.
   - Payee Disclosure: Explicitly notes that when Workday omits the descriptor attribute on Payee_Reference, the test validates Workday reference ID integrity
     (Ad_hoc_Payee_ID / WID) rather than the expected text string at the SOAP layer.
   - Status Disclosure: Explicitly notes that Workday Cash Management Get_Ad_hoc_Payments response does not expose a Document Status field, and that UAT 7 Workday
     Approved business status remains documented as unverified at the SOAP layer.

  ---

  5. Repository & Local Safeguard Confirmations

   - PR #234: Preserved and remains open (not closed or modified).
   - Branch Retention: No local or remote branches were deleted or renamed.
   - Local Working Tree: scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat, and line-ending differences remain completely untouched and
     unstaged.

  ---

  Ready for your instructions regarding closing PR #234 or requesting reviewers.
