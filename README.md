Comprehensive FPOAP-182 Test Architecture & Implementation Plan

  This plan addresses all six review findings with direct empirical evidence, strict branching safeguards, and an architecture that integrates into
  InvoiceToWorkday without regression risk to Supplier Invoices.

  ---

  1. Recorded Warnings Evidence (Live Workday Sandbox Data)

  Inspection of the actual response files recorded during our live Workday Sandbox E2E execution (readyapi/reports/run-adhoc-rerun/) provides
  direct evidence for the warnings array returned by POST /wd-adhoc-payments:

  ┌──────────┬──────────────────────────────────────────────────┬─────────────────────────┬──────────┬──────────────────────────────────────┐
  │ Scenario │ Live Response File                               │ Workday Sandbox Payment │ warnings │ Actual Recorded warnings Content     │
  │          │                                                  │ ID                      │ Count    │                                      │
  ├──────────┼──────────────────────────────────────────────────┼─────────────────────────┼──────────┼──────────────────────────────────────┤
  │ UAT 7    │ E2EReference-...-05SubmitAdHocPayment-0-PASS.txt │ AD_HOC_PAYMENT-6-990717 │ 2        │ ["[Warning] - Please Enter Address   │
  │          │                                                  │                         │          │ Details For New Payee", "[Warning]   │
  │          │                                                  │                         │          │ Warning Custom Validation Exceptions │
  │          │                                                  │                         │          │ Exists. Click \"View Exceptions\"    │
  │          │                                                  │                         │          │ button for details."]                │
  │ UAT 8    │ E2EReference-...-05SubmitAdHocPayment-1-PASS.txt │ AD_HOC_PAYMENT-6-990718 │ 0        │ [] (Empty JSON array)                │
  │ UAT 9    │ E2EReference-...-05SubmitAdHocPayment-2-PASS.txt │ AD_HOC_PAYMENT-6-990719 │ 2        │ ["[Warning] - Please Enter Address   │
  │          │                                                  │                         │          │ Details For New Payee", "[Warning]   │
  │          │                                                  │                         │          │ Warning Custom Validation Exceptions │
  │          │                                                  │                         │          │ Exists. Click \"View Exceptions\"    │
  │          │                                                  │                         │          │ button for details."]                │
  │ UAT 10   │ E2EReference-...-05SubmitAdHocPayment-3-PASS.txt │ AD_HOC_PAYMENT-6-990720 │ 2        │ ["[Warning] - Please Enter Address   │
  │          │                                                  │                         │          │ Details For New Payee", "[Warning]   │
  │          │                                                  │                         │          │ Warning Custom Validation Exceptions │
  │          │                                                  │                         │          │ Exists. Click \"View Exceptions\"    │
  │          │                                                  │                         │          │ button for details."]                │
  └──────────┴──────────────────────────────────────────────────┴─────────────────────────┴──────────┴──────────────────────────────────────┘

  Key Technical Distinction: Special Handling Address vs. Payee Address
  In function/workday_service/workday_client.py (lines 2550–2630):
   - When creating an inline new payee WITHOUT an address (UAT 7, 9, 10):
    The microservice sends Create_new_Ad_hoc_Payee_Name and omits <Address_Data>. Workday creates the payee, but automatically raises a validation
  warning:
    [Warning] - Please Enter Address Details For New Payee.
   - When creating an inline new payee WITH an address (UAT 8):
    The microservice populates <Address_Data> on the ad-hoc payment request with primary usage COMMUNICATION_ADDRESS. Workday receives the payee
  address, accepts the creation cleanly, and emits zero warnings (warnings: []).
   - FPOAP-169 Request warning_message vs. Response warnings:
     - warning_message in the request maps into Workday's custom text field Additional_Fields -> Additional_Field_1.
     - warnings in the response reflects Workday's validation alerts parsed from Alert_Messages and Exceptions_Response_Data.
     - Sending warning_message does not create a response warning; response warnings are triggered by Workday validation exceptions (e.g. missing
       address details).

  ---

  2. Data-Driven Warnings Validation Strategy

  Instead of inferring warning expectations from fixture structure, we will drive expectations explicitly via uat-scenarios.csv:

  Proposed uat-scenarios.csv Column Extension
  Add a 6th column: expected_warnings with strict enum values:
   - EMPTY: Asserts warnings != null, warnings instanceof List, warnings.size() == 0.
   - POPULATED:Please Enter Address Details: Asserts warnings != null, warnings instanceof List, warnings.size() > 0, and every element is a
     String. Verifies that at least one warning contains the expected substring Please Enter Address Details.
   - NA: For Supplier Invoices (Rows 1–6), warnings validation is bypassed.

  Accounting for the Jira Requirement: Add 1 New Warnings Scenario
  FPOAP-182 explicitly requires adding one new warnings scenario. We will add UAT 11:
   - Scenario in CSV:
    adhoc,Adhoc payment with explicit warning message (UAT 11 /
  FPOAP-182),fixtures/uat/adhoc-payment-uat-scenario-11.json,Approved,1,POPULATED:Please Enter Address Details
   - Fixture (fixtures/uat/adhoc-payment-uat-scenario-11.json):
    Includes warning_message: "DW Warning: Review required by AP specialist" and inline payee without address, exercising both request mapping into
  Additional_Field_1 and response validation of the warnings array.

  Modular Script: readyapi/Scripts/VerifyAdHocWarnings.groovy

    1 import groovy.json.JsonSlurper
    2
    3 def rawResponse = messageExchange.responseContent
    4 assert rawResponse : "05-Submit-AdHocPayment response is empty"
    5 def json = new JsonSlurper().parseText(rawResponse)
    6
    7 // 1. Structural schema verification
    8 assert json.containsKey('warnings') : "Response missing mandatory 'warnings' property"
    9 assert json.warnings instanceof List : "'warnings' property must be a JSON array"
   10 json.warnings.each { w ->
   11     assert w instanceof String : "Each warning entry must be a string, found: ${w?.getClass()?.name}"
   12 }
   13
   14 // 2. Data-driven assertion from CSV
   15 def expectedWarningsRule = context.expand('${DataSource#expected_warnings}') ?: 'NA'
   16 if (expectedWarningsRule == 'NA') {
   17     return
   18 }
   19
   20 if (expectedWarningsRule == 'EMPTY') {
   21     assert json.warnings.isEmpty() : "Expected clean (empty) warnings array, but got: ${json.warnings}"
   22     log.info "Validated empty warnings array for clean scenario"
   23 } else if (expectedWarningsRule.startsWith('POPULATED')) {
   24     assert !json.warnings.isEmpty() : "Expected populated warnings array, but got empty list"
   25     def expectedSubstr = expectedWarningsRule.contains(':') ? expectedWarningsRule.split(':', 2)[1] : null
   26     if (expectedSubstr) {
   27         assert json.warnings.any { it.contains(expectedSubstr) } :
   28             "Expected warnings to contain '${expectedSubstr}', but got: ${json.warnings}"
   29     }
   30     log.info "Validated populated warnings array (${json.warnings.size()} warnings): ${json.warnings}"
   31 }

  ---

  3. Conditional Search Execution (Pre-Execution Branching)

  To strictly guarantee that 08b-Search-AdHoc-Payment is never executed for Supplier Invoices (Rows 1–6):

  Step Sequence & Branching Control
  In readyapi/ap-e2e-soapui-project.xml, branching is controlled by testRunner.gotoStepByName(...) before any HTTP call can be dispatched:

    1   08-Poll-Workday (Groovy Step)
    2      ├── If scenario_type == 'invoice':
    3      │     testRunner.gotoStepByName('10-Audit-StatusHistory')  <-- Bypasses 08b completely!
    4      └── If scenario_type == 'adhoc':
    5            testRunner.gotoStepByName('08b-Search-AdHoc-Payment') <-- Executes GET
    6   08b-Search-AdHoc-Payment (REST Step: GET /wd-adhoc-payments)
    7      └── Assertion: evaluate(VerifyAdHocSearch.groovy)
    8   09-Verify-Workday-SOAP (Invoices - invoked programmatically during step 08 poll)
    9   09b-Verify-AdHocPayment-SOAP (Ad-Hoc - invoked programmatically during step 08 poll)
   10   10-Audit-StatusHistory (JDBC Step)
   11   11-DataSource Loop

  Property Refresh & Exact Payment Matching
   - Property Refresh: In 00b-Load-Fixture, all scenario-specific properties (payment_id, payee_name, payment_date) are cleared from context and
     testCase before loading the current row's fixture.
   - Search Query Construction:
    GET
  /wd-adhoc-payments?payee_name=${#TestCase#payee_name}&payment_date_on_or_after=${#TestCase#payment_date}&payment_date_on_or_before=${#TestCase#pa
  yment_date}
    Uses the exact payee name and payment date from the fixture.
   - Exact ID Matching:
    VerifyAdHocSearch.groovy will never assume the first result is our payment. It parses the array and finds the exact element matching
  context.expand('${#TestCase#payment_id}'):

   1   def targetPayment = payments.find { p ->
   2       def refIds = p.Ad_hoc_Payment_Reference?.ID?.collect { it._value_1 ?: it.text() ?: it } ?: []
   3       def dataId = p.Ad_hoc_Payment_Data?.Ad_hoc_Payment_ID
   4       (targetPaymentId in refIds) || (targetPaymentId == dataId)
   5   }
   6   assert targetPayment != null : "Created payment '${targetPaymentId}' not found in search results for payee '${expectedPayee}'"
   - Bounded Read-Only Retries:
    Because Workday's indexer may experience a 1–2 second propagation delay between creation and search visibility, 08b-Search-AdHoc-Payment will
  incorporate a bounded read-only retry (up to 3 attempts with 2s delay) using context.poll(...) or a Groovy retry wrapper before asserting.

  ---

  4. Negative Search Coverage Architecture

  To prevent cluttering the main data-driven loop with repeated negative HTTP calls:

   1. Separation from Main Loop:
     Negative search tests (400 MISSING_REQUIRED_PARAMETER, 400 INVALID_DATE_FORMAT, 400 INVALID_DATE_VALUES, 200 [] no match) do not belong inside
  08b-Search-AdHoc-Payment's response assertion.
   2. Dedicated Step Execution:
     We will add a dedicated Groovy test step: 08c-Validate-Search-Negative-Scenarios:
      - Execution Guard: Executes only once (e.g. during UAT 7 iteration, using context.negativeSearchDone flag) so it does not run across all 11
        iterations.
      - Individual Allure Visibility: For each negative case, the step creates a sub-step or issues distinct requests captured by
        AllureListener.groovy:
        - GET /wd-adhoc-payments?payee_name=NonExistentPayee_XYZ_999&... -> Asserts 200 OK and [].
        - GET /wd-adhoc-payments?payment_date_on_or_after=2026-11-01&payment_date_on_or_before=2026-11-20 -> Asserts 400 Bad Request with
          MISSING_REQUIRED_PARAMETER.
        - GET /wd-adhoc-payments?payee_name=Acme&payment_date_on_or_after=2026-13-45&... -> Asserts 400 Bad Request with INVALID_DATE_FORMAT.
        - GET /wd-adhoc-payments?payee_name=Acme&payment_date_on_or_after=2026-11-25&payment_date_on_or_before=2026-11-20 -> Asserts 400 Bad
          Request with INVALID_DATE_VALUES.
      - Allure 2 Result: Because testRunner.runTestStep(...) is used, each probe logs its own HTTP request, URL, status code, and response JSON in
        the Allure report timeline.

  ---

  5. API Contract Status & Pending Clarification (With Liliana)

  ┌───────────────────┬────────────────┬───────────────────────────────────┬────────────────────────────────────────────────────────────────┐
  │ Parameter /       │ Ticket         │ Implemented Code                  │ Status / Architectural Distinction                             │
  │ Feature           │ FPOAP-182 Text │ (routers/adhoc_payments.py)       │                                                                │
  ├───────────────────┼────────────────┼───────────────────────────────────┼────────────────────────────────────────────────────────────────┤
  │ payment_reference │ Listed as      │ ❌ Not Supported on GET endpoint  │ Client-side matching is NOT query filtering. Searching by      │
  │                   │ query          │                                   │ payee name + date and finding the ID in memory validates       │
  │                   │ parameter      │                                   │ duplicate detection, but does not test a query parameter.      │
  │                   │                │                                   │ Requires clarification from Liliana whether server-side query  │
  │                   │                │                                   │ parameter is planned.                                          │
  │ supplier_id       │ Listed as      │ ❌ Not Supported                  │ Ad-Hoc payments use payee_name, not supplier_id. Clarification │
  │                   │ query          │                                   │ pending.                                                       │
  │                   │ parameter      │                                   │                                                                │
  │ from_date /       │ Listed as      │ Supported as                      │ Aliasing difference. The test suite must use the implemented   │
  │ to_date           │ query          │ payment_date_on_or_after and      │ parameter names.                                               │
  │                   │ parameter      │ payment_date_on_or_before         │                                                                │
  │                   │ names          │                                   │                                                                │
  │ limit / offset    │ Listed as      │ ❌ Not Implemented on public REST │ FPOAP-164 implemented internal pagination across Workday SOAP  │
  │ Pagination        │ acceptance     │ API                               │ pages; the public REST endpoint does not accept limit or       │
  │                   │ criteria       │                                   │ offset. Clarification pending.                                 │
  └───────────────────┴────────────────┴───────────────────────────────────┴────────────────────────────────────────────────────────────────┘
  ---

  6. Minimal File Changes Plan

   1 readyapi/
   2 ├── ap-e2e-soapui-project.xml                 # Add 08b-Search-AdHoc-Payment step, update 08-Poll branching, add warnings assertion on 05
   3 ├── uat-scenarios.csv                         # Add expected_warnings column and append UAT 11 row
   4 ├── fixtures/uat/
   5 │   └── adhoc-payment-uat-scenario-11.json    # Dedicated new warnings scenario fixture (FPOAP-182 requirement)
   6 └── Scripts/
   7     ├── VerifyAdHocWarnings.groovy            # Dedicated script for warnings array validation (empty vs populated)
   8     └── VerifyAdHocSearch.groovy              # Dedicated script for GET search validation and exact payment matching

  Allure Integration Verification
   - In readyapi/Scripts/AllureListener.groovy (lines 124–145):
    Every httprequest step that executes is automatically recorded with duration, status, request parameters, URL, and response body.
    08b-Search-AdHoc-Payment is a native httprequest step and will automatically appear in the Allure timeline with zero listener changes.

  ---

  7. Offline & Live Validation Strategy

   1. Offline AST & Script Compilation:
      - Compile VerifyAdHocWarnings.groovy and VerifyAdHocSearch.groovy with ReadyAPI's FileSystemCompiler (0 errors).
      - Run headless Groovy unit tests against mock payloads covering:
        - Populated warnings matching substring
        - Empty warnings array ([])
        - Unexpected warnings failure
        - Search JSON match by ID, amount, and payee
        - Missing ID failure in search results
   2. Live Workday Sandbox Run (Controlled & Scoped):
      - Run scoped test on UAT 7 (positive search + populated warnings) and UAT 8 (positive search + empty warnings).
      - Total new Workday Sandbox transactions: 0 (piggybacks on existing UAT 7 & 8 creations).
      - Only UAT 11 creates 1 new transaction to satisfy the new warnings acceptance criterion.
   3. Regression Validation:
      - Execute Supplier Invoices (Rows 1–6) to empirically verify that 08b-Search-AdHoc-Payment is skipped and zero regressions occur.

  ---

  No files have been modified. Please confirm if you approve this refined architecture so we can proceed when Liliana's clarifications arrive.
