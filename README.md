 Git Ref Namespace Conflict Report (Pre-Branch Creation Stop)

  In accordance with your instruction ("If any Git operation fails or produces an unexpected result, stop and report it rather than attempting a destructive
  recovery"), I have stopped and am reporting the exact Git constraint encountered.

  ---

  1. The Issue: Git Hierarchical Ref Namespace Conflict

  When executing:

   1 git checkout -b feature/fpoap-165-adhoc-payment-validations/dev
  Git returned the following error:

   1 fatal: cannot lock ref 'refs/heads/feature/fpoap-165-adhoc-payment-validations/dev':
   2 'refs/heads/feature/fpoap-165-adhoc-payment-validations' exists;
   3 cannot create 'refs/heads/feature/fpoap-165-adhoc-payment-validations/dev'

  Why Git Disallowed This:
  In Git's internal object store (.git/refs/heads/), branch names map directly to filesystem paths:
   - Because the branch feature/fpoap-165-adhoc-payment-validations already exists as a file, Git cannot create a directory with that exact same name to hold the
     child leaf /dev.
   - Furthermore, because origin/feature/fpoap-165-adhoc-payment-validations already exists on GitHub (backing PR #234), GitHub's remote server will enforce the
     exact same rule and reject any push to feature/fpoap-165-adhoc-payment-validations/dev with a ref lock error.

  ---

  2. Available Options to Satisfy the /dev Branch Suffix

  Option A: Use feature/fpoap-165/dev (Recommended — Matches Repo Convention)
   - Why this works: Inspection of the commit history shows the team standardizes on this exact pattern:
     - feature/fpoap-169/dev (merged in PR #224)
     - feature/fpoap-177/dev (merged in PR #228)
   - Safety: Neither local Git nor GitHub has an existing ref named feature/fpoap-165, so creating and pushing feature/fpoap-165/dev has zero ref conflicts.
   - Preservation: Completely preserves the existing branch feature/fpoap-165-adhoc-payment-validations and PR #234 without deleting anything.
   - Cloud Build: Ends in /dev, which perfectly triggers the GCP Cloud Build CI pipeline.

  Option B: Use feature/fpoap-165-adhoc/dev or feature/fpoap-165-adhoc-validations/dev
   - Slightly more descriptive, also avoids the exact prefix conflict.

  ---

  3. Current Working Tree & Commit Status

   - Commit Created: dfd0218 on local branch feature/fpoap-165-adhoc-payment-validations:

   1   commit dfd0218: [FPOAP-165] Fix payee reference validation and handle missing status in Ad-Hoc SOAP response
   2   1 file changed, 39 insertions(+), 14 deletions(-)
   - Staging Status: Clean (all changes committed locally).
   - Remote State: No branches deleted; PR #234 remains intact.
   - Unrelated Files: scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat remain untouched.

  ---

  4. Proposed PR Title & Description (For Your Review)

  Target Base: dev  
  Head Branch: feature/fpoap-165/dev  
  Title: [FPOAP-165] Add ReadyAPI Ad-Hoc Payment validations for UAT 7–10

  Proposed PR Body:

    1 ## Summary
    2
    3 This PR adds fixture-driven ReadyAPI SOAP validations for Ad-Hoc Payment UAT Scenarios 7–10 and extracts the existing inline assertion into a reusable
      Groovy script.
    4
    5 > **Note:** This PR supersedes #234 to align with the repository's `/dev` branch naming convention required for GCP Cloud Build CI triggers.
    6
    7 ## Changes
    8
    9 * **Modularized Assertion:** Extracted inline assertion logic into `readyapi/Scripts/VerifyAdHocPaymentDetails.groovy`.
   10 * **Project XML Update:** Configured `09b-Verify-AdHocPayment-SOAP` in `readyapi/ap-e2e-soapui-project.xml` to evaluate the external script and added it to
      `00-Check-ProjectFiles` smoke checks.
   11 * **Financial Validations:** Added fail-fast, exact `BigDecimal` checks for currency (`USD`), total payment amount, line extended amounts, and accounting
      worktags (Cost Center `6300`, Location `613`, Spend Category).
   12 * **Payee Reference Integrity:** Implemented two-tier payee validation: checks descriptor name when present in SOAP response, and strictly asserts non-empty
      Workday `Ad_hoc_Payee_ID` / `WID` reference integrity when descriptors are omitted by Workday Cash Management.
   13 * **Memos:** Verified line item memos and header memos against fixture data.
   14 * **UAT 8 Special Handling:** Verified `Address_Data` (`Address_Line_Data`, `Municipality`, `Postal_Code`).
   15 * **UAT 9 & 10 Attachments:** Verified all multi-document attachments by count and individual filenames in Workday SOAP.
   16 * **Status Handling:** Configured conditional status verification with explicit warning logging. (Workday Cash Management `Get_Ad_hoc_Payments` response
      does not expose a Document Status field; UAT 7 Workday *Approved* business status remains documented as unverified at the SOAP layer).
   17 * **Preserved Assertions:** All existing XPath assertions (including `memo carries run_id`) remain intact.
   18
   19 ## Live E2E Test Results (Workday Sandbox)
   20
   21 Executed headlessly against local database (`alloydb-local`), local Email Service (`localhost:8002`), and the authorized Workday Sandbox Company 100 via
      Apigee Dev:
   22
   23 * **Overall Status:** **100% PASS** (4 / 4 scenarios passed, 0 failures, 203 / 203 request assertions passed)
   24 * **UAT 7:** `AD_HOC_PAYMENT-6-990717` -> **PASS**
   25 * **UAT 8:** `AD_HOC_PAYMENT-6-990718` -> **PASS**
   26 * **UAT 9:** `AD_HOC_PAYMENT-6-990719` -> **PASS**
   27 * **UAT 10:** `AD_HOC_PAYMENT-6-990720` -> **PASS**
   28 * **Local Suite Checks:**
   29   * ReadyAPI Smoke suite: **PASS** (1,730 ms)
   30   * Groovy AST compilation: **PASS** (0 errors)
   31   * Python unit tests: **44 passed** in 2.56s
   32
   33 ## Jira
   34
   35 [FPOAP-165](https://goaaa.atlassian.net/browse/FPOAP-165)

  ---

  Please let me know if you approve using feature/fpoap-165/dev (Option A) to push the branch and open the replacement PR.
