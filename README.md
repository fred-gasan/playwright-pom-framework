Here is the complete, read-only Git and repository hygiene audit report. No files or branches have been modified, staged, committed, or deleted.

  ---

  1. Current Branch & Upstream Tracking Configuration

   - Current Branch: dev
   - Current HEAD Commit: 772fd3e ([FPOAP-156] Add data-driven duplicate check coverage for Non-PO invoices (Auto Complete = No) (#226))
   - Upstream Tracking: origin/dev
   - Upstream Sync Status: 0 commits ahead, 0 commits behind origin/dev. Your local dev branch commit history is 100% in sync with the remote repository.

  ---

  2. Working Tree Status & Classification of Files

  Running git status -uall reveals three categories of files:

    1 ┌────────────────────────────────────────────────────────────────────────┐
    2 │                      WORKING TREE FILE INVENTORY                       │
    3 ├────────────────────────────────────────────────────────────────────────┤
    4 │ 1. Intended UAT 7–10 Feature Changes (2 files)                         │
    5 │    • readyapi/Scripts/VerifyAdHocPaymentDetails.groovy  [Untracked]   │
    6 │    • readyapi/ap-e2e-soapui-project.xml                 [Modified]    │
    7 ├────────────────────────────────────────────────────────────────────────┤
    8 │ 2. Unrelated Local Code Enhancements (1 file)                          │
    9 │    • scripts/run_local_server.py                        [Modified]    │
   10 │      (Windows SelectorEventLoop, auto-venv, and dotenv runner fixes)   │
   11 ├────────────────────────────────────────────────────────────────────────┤
   12 │ 3. Build & Compiler Artifacts (Untracked)                              │
   13 │    • VerifyAdHocPaymentDetails*.class (40 .class files in repo root)   │
   14 │      (Produced by the Groovy FileSystemCompiler syntax check)          │
   15 ├────────────────────────────────────────────────────────────────────────┤
   16 │ 4. Local Developer Convenience & Config Files (Untracked)              │
   17 │    • alembic.local.ini (local database connection string)              │
   18 │    • readyapi/view_allure_report.bat (Windows batch launcher)          │
   19 ├────────────────────────────────────────────────────────────────────────┤
   20 │ 5. Line-Ending (CRLF vs LF) Differences (10 files)                     │
   21 │    • entrypoint.sh                                                     │
   22 │    • readyapi/Scripts/AllureListener.groovy                            │
   23 │    • readyapi/Scripts/BuildInvoiceRequest.groovy                       │
   24 │    • readyapi/Scripts/LoadFixture.groovy                               │
   25 │    • readyapi/Scripts/Setup.groovy                                     │
   26 │    • readyapi/Scripts/VerifyInvoiceDetails.groovy                      │
   27 │    • readyapi/env/qa.properties                                        │
   28 │    • readyapi/fixtures/uat/eib-po-invoice-uat-scenario-5.json          │
   29 │    • readyapi/fixtures/uat/eib-po-invoice-uat-scenario-6.json          │
   30 │    • tf/readyapi-job/main.tf                                           │
   31 │    (0 substantive code changes; modified solely by Windows checkout)  │
   32 └────────────────────────────────────────────────────────────────────────┘

  ---

  3. Review of Existing .gitignore Rules

  The root and subdirectory .gitignore files currently handle:
   - Properly Ignored & Safe:
     - .env, .venv/, env/
     - __pycache__/, *.py[cod]
     - .pytest_cache/
     - readyapi/env/local.secrets.properties
     - readyapi/reports/
     - .terraform/, *.tfstate, *.secret.tfvars
   - Gaps in Existing .gitignore:
     1. *.class is missing: The root .gitignore ignores *$py.class (Jython) but does not ignore standard Java/Groovy *.class files.
     2. *.local.ini / alembic.local.ini is missing: Local Alembic connection files are not ignored.
     3. *.bat is not ignored in readyapi/: readyapi/view_allure_report.bat remains untracked.

  ---

  4. Sensitive Files & Credential Tracking Audit

   - No Secrets Exposed in Untracked/Modified Files:
     - readyapi/env/local.secrets.properties is properly ignored and untracked.
     - .env is properly ignored and untracked.
     - alembic.local.ini is untracked and contains local placeholder/Docker credentials (postgres:postgres@localhost:5432).
   - Audit of Tracked Repository Files:
     - No private API keys or tokens exist in tracked files.
     - readyapi/env/local.secrets.properties.example contains non-secret dummy templates.
     - scripts/client_secret.json is a historical tracked file for sandbox OAuth (committed in earlier releases); it has not been modified.

  ---

  5. Review of the Substantive Git Diff

  Filtering out CRLF line endings via git diff --ignore-space-at-eol confirms that only two files contain substantive code changes for this task:

   1. readyapi/Scripts/VerifyAdHocPaymentDetails.groovy (New):
      - Implements fail-fast validations for Currency, Total Amount (BigDecimal scale 2), Payee, Line Items, Financial Coding (Cost Center, Location, Spend
        Category), Line Memos, Header Memo, UAT 7 status APPROVED, UAT 8 Special Handling Address, and UAT 9/10 multi-document attachments.
   2. readyapi/ap-e2e-soapui-project.xml (Modified):
      - Adds 'Scripts/VerifyAdHocPaymentDetails.groovy' to the 00-Check-ProjectFiles Smoke test step.
      - Replaces the inline script in 09b-Verify-AdHocPayment-SOAP with evaluate(scriptFile) delegation.
      - Preserves all existing XPath assertions (including memo carries run_id).

  (Note: scripts/run_local_server.py contains 47 lines of local server enhancements that must be excluded from this PR).

  ---

  6. Recommended Action Plan

  Step 1: Clean Local Build Artifacts (Pre-Branch Hygiene)
   - Delete the 40 generated bytecode files in root: Remove-Item VerifyAdHocPaymentDetails*.class.

  Step 2: Create Feature Branch
   - Create a feature branch directly from the current HEAD:

   1   git checkout -b feature/fpoap-131-adhoc-payment-validations
    (This carries over your entire working tree and unstaged edits without risking any work).

  Step 3: Surgical Staging of Feature Files Only
   - Explicitly stage only the two intended files:

   1   git add readyapi/Scripts/VerifyAdHocPaymentDetails.groovy
   2   git add readyapi/ap-e2e-soapui-project.xml
   - Do not use git add . or git add -A.
   - This guarantees that scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat, and the 10 CRLF line-ending files remain completely unstaged and
     excluded from the commit.

  Step 4: Repository Hygiene (.gitignore update)
   - Add *.class and *.local.ini to .gitignore (either in this PR or as a separate cleanup commit).

  ---

  Please let me know when you are ready to proceed with Step 1 and Step 2.
