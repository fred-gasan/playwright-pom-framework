Phase 1 Implementation & Offline Validation Report: POST Ad-Hoc Payment Warnings

  Phase 1 of FPOAP-182 has been implemented and exhaustively verified through offline compilation, XML structure checks, smoke tests, Groovy unit
  tests, and Python regression test suites.

  ---

  A. Implementation Summary

   1. Active Branch: Verified on feature/fpoap-182/dev at commit 45e8091.
   2. Explicit CSV Data-Driving: Extended readyapi/uat-scenarios.csv with the 6th column expected_warnings:
      - Rows 1–6 (Invoices): NA (Bypasses warnings check cleanly).
      - UAT 7 (Ad-Hoc without address): POPULATED:Please Enter Address Details
      - UAT 8 (Ad-Hoc with special handling address): EMPTY
      - UAT 9 & 10 (Ad-Hoc without address): POPULATED:Please Enter Address Details
      - New UAT 11: POPULATED:Please Enter Address Details
   3. ReadyAPI DataSource Updated: Added expected_warnings property to DataSource in readyapi/ap-e2e-soapui-project.xml, automatically mapping the
      column value into ${DataSource#expected_warnings}.
   4. Dedicated Modular Assertion Script (readyapi/Scripts/VerifyAdHocWarnings.groovy):
      - Implements structural JSON checks: valid JSON object, warnings key exists, warnings instanceof List, all elements are Strings.
      - Strictly enforces EMPTY (warnings.size() == 0) and POPULATED (warnings.size() > 0) expectations.
      - For POPULATED:<substring>, asserts that at least one warning contains the expected substring (Please Enter Address Details).
      - Fails explicitly on missing or invalid expectation rules.
      - Immediately bypasses validation if scenario is an invoice or if rule is NA.
   5. Project Assertion Integration: Added validate adhoc payment warnings assertion step to 05-Submit-AdHocPayment delegating to
      VerifyAdHocWarnings.groovy. Existing assertions (HTTP 200 and payment_id present) remain untouched.
   6. New UAT 11 Fixture (readyapi/fixtures/uat/adhoc-payment-uat-scenario-11.json):
      - Includes unique payee "Apex Advisory Group LLC".
      - Omits address fields to trigger the historically observed Workday validation warning.
      - Includes explicit request field warning_message: "DW Warning: Review required by AP specialist - suspected duplicate review".
      - Reuses existing binary fixture fixtures/uat/adhoc-payment-uat-scenario-7.pdf.
   7. Payload Builder Propagation (BuildAdHocPaymentRequest.groovy):
      - Updated to map fixture.warning_message into requestBody.payment_data.warning_message.
   8. Smoke Checks Updated: Added VerifyAdHocWarnings.groovy and adhoc-payment-uat-scenario-11.json to 00-Check-ProjectFiles.

  ---

  B. Exact Files Created and Modified

  ┌──────────────────────────────────────────────────────────┬──────────┬───────────────────────────────────────────────────────────────────────┐
  │ File                                                     │ Status   │ Description                                                           │
  ├──────────────────────────────────────────────────────────┼──────────┼───────────────────────────────────────────────────────────────────────┤
  │ readyapi/uat-scenarios.csv                               │ Modified │ Added expected_warnings column across all 10 existing rows + appended │
  │                                                          │          │ new UAT 11 row.                                                       │
  │ readyapi/ap-e2e-soapui-project.xml                       │ Modified │ Added expected_warnings property to DataSource, added Groovy          │
  │                                                          │          │ assertion to 05-Submit-AdHocPayment, updated 00-Check-ProjectFiles.   │
  │ readyapi/Scripts/BuildAdHocPaymentRequest.groovy         │ Modified │ Propagated fixture.warning_message to                                 │
  │                                                          │          │ requestBody.payment_data.warning_message and normalized to Unix LF.   │
  │ readyapi/Scripts/VerifyAdHocWarnings.groovy              │ Created  │ Modular warnings assertion script with structural and data-driven     │
  │                                                          │          │ rule validation.                                                      │
  │ readyapi/fixtures/uat/adhoc-payment-uat-scenario-11.json │ Created  │ Dedicated UAT 11 fixture with warning_message and inline payee.       │
  └──────────────────────────────────────────────────────────┴──────────┴───────────────────────────────────────────────────────────────────────┘
  ---

  C. Git Diff Summary

   1  readyapi/Scripts/BuildAdHocPaymentRequest.groovy    |   4 +
   2  readyapi/Scripts/VerifyAdHocWarnings.groovy        |  56 ++++++++++++++++++
   3  readyapi/ap-e2e-soapui-project.xml                 |  16 +++++-
   4  readyapi/fixtures/uat/adhoc-payment-uat-scenario-11.json |  35 +++++++++++
   5  readyapi/uat-scenarios.csv                         |  23 ++++----
   6  5 files changed, 122 insertions(+), 12 deletions(-)

   - Line Endings & Whitespace: git diff --check output is completely empty across all 5 files (pure Unix LF).
   - Working Tree Integrity: All unrelated developer tools (scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat) remain 100%
     untouched.

  ---

  D. Offline Validation Results

  1. ReadyAPI Smoke Test Suite (ProjectLoads / 00-Check-ProjectFiles)
   - Command: testrunner.bat -s "Smoke" -c "ProjectLoads" readyapi/ap-e2e-soapui-project.xml
   - Result: 🟢 PASS in 1,613 ms
   - Confirmed: XML schema is valid, DataSource Loop bindings are intact, and all project files (including new script and fixture) are found.

  2. ReadyAPI Groovy Compiler (FileSystemCompiler)
   - Command: FileSystemCompiler readyapi/Scripts/VerifyAdHocWarnings.groovy
   - Result: 🟢 0 errors, Exit Code 0.

  3. Offline Warnings Assertion Suite (11 Test Cases)
  Executed headlessly via groovy.ui.GroovyMain:

  ┌──────┬──────────────────────────────────────────┬──────────────────────────────────────────────────────────┬───────────────────┐
  │ Case │ Scenario Tested                          │ Expected Behavior                                        │ Actual Result     │
  ├──────┼──────────────────────────────────────────┼──────────────────────────────────────────────────────────┼───────────────────┤
  │ 1    │ Valid populated warnings matching        │ PASS                                                     │ 🟢 PASSED         │
  │      │ substring                                │                                                          │                   │
  │ 2    │ Valid empty warnings array               │ PASS                                                     │ 🟢 PASSED         │
  │ 3    │ Missing warnings property in JSON        │ FAIL (Response missing mandatory 'warnings' property)    │ 🟢 PASSED (Caught │
  │      │                                          │                                                          │ expectedly)       │
  │ 4    │ Null warnings property                   │ FAIL ('warnings' property must not be null)              │ 🟢 PASSED (Caught │
  │      │                                          │                                                          │ expectedly)       │
  │ 5    │ Non-array warnings (String instead of    │ FAIL ('warnings' property must be a JSON array (List))   │ 🟢 PASSED (Caught │
  │      │ List)                                    │                                                          │ expectedly)       │
  │ 6    │ Non-string warning entry (Integer inside │ FAIL (Warning at index 0 must be a String)               │ 🟢 PASSED (Caught │
  │      │ array)                                   │                                                          │ expectedly)       │
  │ 7    │ Missing expected warning substring       │ FAIL (None of the returned warnings ... contain expected │ 🟢 PASSED (Caught │
  │      │                                          │ substring)                                               │ expectedly)       │
  │ 8    │ Unexpected populated warnings when EMPTY │ FAIL (Expected clean (empty) warnings array, but got 1   │ 🟢 PASSED (Caught │
  │      │ expected                                 │ warning(s))                                              │ expectedly)       │
  │ 9    │ Unexpected empty warnings when POPULATED │ FAIL (Expected populated warnings array, but got empty   │ 🟢 PASSED (Caught │
  │      │ expected                                 │ array [])                                                │ expectedly)       │
  │ 10   │ Invalid or missing scenario expectation  │ FAIL (Unsupported expected_warnings rule)                │ 🟢 PASSED (Caught │
  │      │                                          │                                                          │ expectedly)       │
  │ 11   │ Invoice scenario with NA rule            │ PASS (Safely bypassed)                                   │ 🟢 PASSED         │
  └──────┴──────────────────────────────────────────┴──────────────────────────────────────────────────────────┴───────────────────┘

  4. Python Application Layer Regression Suite
   - Command: pytest tests/workday_service/routers/test_adhoc_payments.py
   - Result: 🟢 32 passed in 3.39s (0 failures).

  ---

  E. UAT 11 Fixture & Payload Mapping Verification

   - Offline Builder Test: Simulated BuildAdHocPaymentRequest.groovy with adhoc-payment-uat-scenario-11.json.
   - Payload Verification:

   1   "payment_data": {
   2     "total_payment_amount": 650.0,
   3     "currency": "USD",
   4     "payee_name": "Apex Advisory Group LLC",
   5     "memo": "E2E run_id=test-run-11 UAT 11 Adhoc Payment Warning Review",
   6     "warning_message": "DW Warning: Review required by AP specialist - suspected duplicate review",
   7     ...
   8   }
    Confirmed: warning_message is properly populated into the outgoing POST request payload.
   - Clarification: The fixture is designed with an inline payee without an address to trigger the Workday validation warning; warning_message maps
     into Workday Additional_Field_1 and is not claimed to trigger the response warning itself.

  ---

  F. Remaining Risks & Pending Phase 2 Requirements

   1. Phase 2 Scope (Deferred Awaiting Clarification from Liliana):
      - GET /wd-adhoc-payments search step (08b-Search-AdHoc-Payment).
      - Resolution on unsupported query parameters (payment_reference, supplier_id, limit/offset).
      - Dedicated negative search test step (08c-Validate-Search-Negative-Scenarios).
   2. Sandbox Execution Notice:
      - When live execution is eventually approved, running UAT 7, 8, 9, 10, and 11 will create new persistent Ad-Hoc Payment transactions in the
        Workday Sandbox Company 100.
   3. Current State:
      - Zero live API calls were made.
      - Zero Workday Sandbox transactions were created.
      - Working tree has not been staged or committed.

  ---

  Awaiting your review and instructions before proceeding.
