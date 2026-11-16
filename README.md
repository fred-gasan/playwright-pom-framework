Line endings for the two target files have been converted to Unix LF (\n), verified, and selectively staged.

  ---

  1. Pre-Staging Verification Results

   - Line Ending Format: Verified Unix LF (\n) on both files.
   - git diff --check: 0 whitespace errors / 0 trailing \r warnings.
   - XML Diff Scope: Exactly 56 lines changed (4 insertions, 52 deletions) with zero extraneous line-ending churn.
   - Groovy Compilation: Succeeded with 0 errors.
   - ReadyAPI Smoke Test (testrunner.bat -s "Smoke"): PASS (1,730 ms).

  ---

  2. Staged Diff Verification

  A. Staged Files (git diff --cached --name-status)

   1 A       readyapi/Scripts/VerifyAdHocPaymentDetails.groovy
   2 M       readyapi/ap-e2e-soapui-project.xml

  B. Staged Statistics (git diff --cached --stat)

   1  readyapi/Scripts/VerifyAdHocPaymentDetails.groovy | 179 ++++++++++++++++++++++
   2  readyapi/ap-e2e-soapui-project.xml                |  56 +------
   3  2 files changed, 183 insertions(+), 52 deletions(-)

  C. Staged Whitespace & Hygiene Check (git diff --cached --check)
   - Output: (empty) — Clean, 0 warnings or whitespace errors.

  D. Repository Status (git status --short)

    1  M entrypoint.sh
    2  M readyapi/Scripts/AllureListener.groovy
    3  M readyapi/Scripts/BuildInvoiceRequest.groovy
    4  M readyapi/Scripts/LoadFixture.groovy
    5  M readyapi/Scripts/Setup.groovy
    6 A  readyapi/Scripts/VerifyAdHocPaymentDetails.groovy
    7  M readyapi/Scripts/VerifyInvoiceDetails.groovy
    8 M  readyapi/ap-e2e-soapui-project.xml
    9  M readyapi/env/qa.properties
   10  M readyapi/fixtures/uat/eib-po-invoice-uat-scenario-5.json
   11  M readyapi/fixtures/uat/eib-po-invoice-uat-scenario-6.json
   12  M scripts/run_local_server.py
   13  M tf/readyapi-job/main.tf
   14 ?? alembic.local.ini
   15 ?? readyapi/view_allure_report.bat

  ---

  3. Checkpoint Confirmations

   - Exactly Two Files Staged: Only VerifyAdHocPaymentDetails.groovy and ap-e2e-soapui-project.xml are in the index (staged).
   - Feature Scope Respected:
     - VerifyAdHocPaymentDetails.groovy contains the complete, fail-fast UAT 7–10 validations (Currency, Total Amount via BigDecimal, Payee, Lines, Financial
       Coding, Line & Header Memos, Status APPROVED, Address, and Multi-Document Attachments).
     - ap-e2e-soapui-project.xml is modified only to register the script in Smoke and delegate assertion step 09b-Verify-AdHocPayment-SOAP.
     - Existing XPath assertions (including memo carries run_id) remain unchanged.
   - Unrelated Changes Untouched: scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat, and all other files remain completely untouched and
     unstaged.
   - No Sensitive Data: No tokens, credentials, or secrets are staged.
   - No Git Mutations: No commits, pushes, or PRs have been executed.

  Ready for your next instruction.
