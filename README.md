FPOAP-182 Test Architecture & Implementation Plan

  ---

  A. Confirmed API Contract (GET /wd-adhoc-payments & POST /wd-adhoc-payments)

  Revalidated against the latest origin/dev codebase (function/workday_service/routers/adhoc_payments.py at commit 45e8091 / f078c0f):

  1. GET /wd-adhoc-payments (FPOAP-113 & FPOAP-164)
   - Implemented Parameters (Strictly Mandatory):
     - payee_name (str, required, non-empty): The case-sensitive name of the payee.
     - payment_date_on_or_after (str, required, format: YYYY-MM-DD).
     - payment_date_on_or_before (str, required, format: YYYY-MM-DD).
   - Internal Orchestration:
     - Paginates internally through Workday Cash Management Get_Ad_Hoc_Payees across all pages (FPOAP-164 enhancement) to discover all matching
       payee reference IDs (WID and Ad_Hoc_Payee_ID).
     - If no payees match, returns [] (HTTP 200) immediately without calling payments.
     - Calls Get_Ad_Hoc_Payments using the resolved payee references and date range.
     - Recursively strips out unwanted Worktags and enumeration references.
   - Response Format:
     - Standard JSON array: List[Dict] containing Ad_hoc_Payment_Reference and Ad_hoc_Payment_Data objects.
     - Returns empty list [] when no matching payees or payments exist.
   - Error Behavior:
     - Missing any parameter -> 400 Bad Request (error_code: MISSING_REQUIRED_PARAMETER).
     - Invalid date format -> 400 Bad Request (error_code: INVALID_DATE_FORMAT).
     - Dates out of order (after > before) -> 400 Bad Request (error_code: INVALID_DATE_VALUES).
     - Downstream Workday fault -> 400 Bad Request (error_code: WORKDAY_SOAP_FAULT).

  2. POST /wd-adhoc-payments Warnings (FPOAP-169)
   - Request Payload:
     - Accepts optional payment_data.warning_message (str).
     - When populated: Mapped into Workday SOAP Ad_hoc_Payment_Data -> Additional_Fields -> Additional_Field_1.
     - When omitted/empty: <Additional_Fields> element is completely omitted from the SOAP request.
   - Response Format:

   1   {
   2     "payment_id": "AD_HOC_PAYMENT-6-990717",
   3     "workday_wid": "e13fc11a07d390015b192cb994980000",
   4     "warnings": [ ... ],
   5     "ap_id": "...",
   6     "qurrent_workflow_id": null
   7   }
     - warnings is always a JSON array (List[str]).
     - Populated (warnings.size() > 0): Contains Workday alerts and custom validation exceptions (e.g., missing address warnings).
     - Clean / Empty (warnings.size() == 0): Evaluates to empty list [] when no Workday alerts or exceptions are triggered.

  ---

  B. Detailed Test Coverage Matrix

  ┌─────┬───────┬───────────────────────────────────────────────────────────────┬───────┬──────────────┬────────────────────────────┬───────┐
  │ Tes │ Objec │ Endpoint & Parameters                                         │ Scena │ Expected     │ ReadyAPI Step & Script     │ Persi │
  │ t   │ tive  │                                                               │ rio / │ HTTP & Body  │                            │ stent │
  │ ID  │       │                                                               │ Fixtu │              │                            │ Sandb │
  │     │       │                                                               │ re    │              │                            │ ox    │
  │     │       │                                                               │       │              │                            │ Recor │
  │     │       │                                                               │       │              │                            │ d?    │
  ├─────┼───────┼───────────────────────────────────────────────────────────────┼───────┼──────────────┼────────────────────────────┼───────┤
  │ TC- │ Searc │ GET                                                           │ Drive │ 200          │ 08b-Search-AdHoc-Payment<b │ ❌ No │
  │ 182 │ h     │ /wd-adhoc-payments?payee_name=${payee}&payment_date_on_or_aft │ n by  │ OK<br>Array  │ r>(VerifyAdHocSearch.groov │ addit │
  │ -1  │ Newly │ er=${date}&payment_date_on_or_before=${date}                  │ UAT 7 │ length ≥ 1;  │ y)                         │ ional │
  │     │ Creat │                                                               │ (or   │ contains     │                            │ recor │
  │     │ ed    │                                                               │ any   │ item with    │                            │ d     │
  │     │ Payme │                                                               │ Ad-Ho │ matching     │                            │ (sear │
  │     │ nt    │                                                               │ c     │ payment_id,  │                            │ ches  │
  │     │ (Posi │                                                               │ scena │ exact        │                            │ payme │
  │     │ tive  │                                                               │ rio)  │ Control_Tota │                            │ nt    │
  │     │ Match │                                                               │       │ l_Amount,    │                            │ creat │
  │     │ )     │                                                               │       │ Currency_Ref │                            │ ed in │
  │     │       │                                                               │       │ erence, and  │                            │ step  │
  │     │       │                                                               │       │ Payee_Refere │                            │ 05)   │
  │     │       │                                                               │       │ nce.         │                            │       │
  │ TC- │ Searc │ GET                                                           │ Stand │ 200          │ 08b-Search-AdHoc-Payment   │ ❌ No │
  │ 182 │ h     │ /wd-adhoc-payments?payee_name=NonExistentPayee_XYZ_999&paymen │ alone │ OK<br>respon │ (sub-check)                │       │
  │ -2  │ Negat │ t_date_on_or_after=2026-11-01&payment_date_on_or_before=2026- │ verif │ se == []     │                            │       │
  │     │ ive:  │ 11-20                                                         │ icati │              │                            │       │
  │     │ No    │                                                               │ on    │              │                            │       │
  │     │ Payee │                                                               │ step  │              │                            │       │
  │     │ Match │                                                               │ or    │              │                            │       │
  │     │       │                                                               │ negat │              │                            │       │
  │     │       │                                                               │ ive   │              │                            │       │
  │     │       │                                                               │ loop  │              │                            │       │
  │     │       │                                                               │ itera │              │                            │       │
  │     │       │                                                               │ tion  │              │                            │       │
  │ TC- │ Searc │ GET                                                           │ Stand │ 400 Bad      │ 08b-Search-AdHoc-Payment   │ ❌ No │
  │ 182 │ h     │ /wd-adhoc-payments?payment_date_on_or_after=2026-11-01&paymen │ alone │ Request<br>e │ (sub-check)                │       │
  │ -3  │ Valid │ t_date_on_or_before=2026-11-20 (Omit payee_name)              │ valid │ rror_code:   │                            │       │
  │     │ ation │                                                               │ ation │ MISSING_REQU │                            │       │
  │     │ :     │                                                               │ call  │ IRED_PARAMET │                            │       │
  │     │ Missi │                                                               │       │ ER           │                            │       │
  │     │ ng    │                                                               │       │              │                            │       │
  │     │ Param │                                                               │       │              │                            │       │
  │     │ eter  │                                                               │       │              │                            │       │
  │ TC- │ Searc │ GET                                                           │ Stand │ 400 Bad      │ 08b-Search-AdHoc-Payment   │ ❌ No │
  │ 182 │ h     │ /wd-adhoc-payments?payee_name=Acme&payment_date_on_or_after=2 │ alone │ Request<br>e │ (sub-check)                │       │
  │ -4  │ Valid │ 026-13-45&payment_date_on_or_before=2026-11-20                │ valid │ rror_code:   │                            │       │
  │     │ ation │                                                               │ ation │ INVALID_DATE │                            │       │
  │     │ :     │                                                               │ call  │ _FORMAT      │                            │       │
  │     │ Inval │                                                               │       │              │                            │       │
  │     │ id    │                                                               │       │              │                            │       │
  │     │ Date  │                                                               │       │              │                            │       │
  │     │ Forma │                                                               │       │              │                            │       │
  │     │ t     │                                                               │       │              │                            │       │
  │ TC- │ Searc │ GET                                                           │ Stand │ 400 Bad      │ 08b-Search-AdHoc-Payment   │ ❌ No │
  │ 182 │ h     │ /wd-adhoc-payments?payee_name=Acme&payment_date_on_or_after=2 │ alone │ Request<br>e │ (sub-check)                │       │
  │ -5  │ Valid │ 026-11-25&payment_date_on_or_before=2026-11-20                │ valid │ rror_code:   │                            │       │
  │     │ ation │                                                               │ ation │ INVALID_DATE │                            │       │
  │     │ :     │                                                               │ call  │ _VALUES      │                            │       │
  │     │ Dates │                                                               │       │              │                            │       │
  │     │ Out   │                                                               │       │              │                            │       │
  │     │ of    │                                                               │       │              │                            │       │
  │     │ Order │                                                               │       │              │                            │       │
  │ TC- │ POST  │ POST /wd-adhoc-payments<br>(Inline payee created without      │ UAT 7 │ 200          │ 05-Submit-AdHocPayment<br> │ ⚠️    │
  │ 182 │ Ad-Ho │ address)                                                      │ (or   │ OK<br>warnin │ (VerifyAdHocWarnings.groov │ Uses  │
  │ -6  │ c     │                                                               │ UAT 9 │ gs           │ y)                         │ exist │
  │     │ Payme │                                                               │ / 10) │ instanceof   │                            │ ing   │
  │     │ nt:   │                                                               │       │ List &&      │                            │ UAT 7 │
  │     │ Popul │                                                               │       │ !warnings.is │                            │ trans │
  │     │ ated  │                                                               │       │ Empty();     │                            │ actio │
  │     │ Warni │                                                               │       │ contains     │                            │ n     │
  │     │ ngs   │                                                               │       │ Workday      │                            │ (zero │
  │     │ Array │                                                               │       │ alert.       │                            │ addit │
  │     │       │                                                               │       │              │                            │ ional │
  │     │       │                                                               │       │              │                            │ recor │
  │     │       │                                                               │       │              │                            │ ds)   │
  │ TC- │ POST  │ POST /wd-adhoc-payments<br>(Special handling with full        │ UAT 8 │ 200          │ 05-Submit-AdHocPayment<br> │ ⚠️    │
  │ 182 │ Ad-Ho │ address provided)                                             │       │ OK<br>warnin │ (VerifyAdHocWarnings.groov │ Uses  │
  │ -7  │ c     │                                                               │       │ gs           │ y)                         │ exist │
  │     │ Payme │                                                               │       │ instanceof   │                            │ ing   │
  │     │ nt:   │                                                               │       │ List &&      │                            │ UAT 8 │
  │     │ Empty │                                                               │       │ warnings.isE │                            │ trans │
  │     │ Warni │                                                               │       │ mpty() ([]). │                            │ actio │
  │     │ ngs   │                                                               │       │              │                            │ n     │
  │     │ Array │                                                               │       │              │                            │ (zero │
  │     │       │                                                               │       │              │                            │ addit │
  │     │       │                                                               │       │              │                            │ ional │
  │     │       │                                                               │       │              │                            │ recor │
  │     │       │                                                               │       │              │                            │ ds)   │
  └─────┴───────┴───────────────────────────────────────────────────────────────┴───────┴──────────────┴────────────────────────────┴───────┘
  ---

  C. Proposed ReadyAPI Execution Flow (Extending InvoiceToWorkday)

  The Jira description specifically requested extending the existing InvoiceToWorkday testcase rather than creating a separate testcase. This is
  optimal because it leverages the existing lifecycle, authentication, seeded data, and created transactions.

  Step Placement & Guard Logic:

    1   00-Setup
    2   00a-DataSource (CSV: uat-scenarios.csv)
    3   00b-Load-Fixture
    4   01-Seed-ProcessedEmail
    5   02-Read-SeededRow
    6   03-Capture-ap_id
    7   04-Callback-ProcessedByQurrent
    8   05-Dynamic-Submit
    9      ├── 05-Submit-Invoice (Supplier Invoices)
   10      ├── 05b-Build-AdHocPayment-Request
   11      └── 05-Submit-AdHocPayment (REST POST)
   12          └── Assertion: [validate adhoc payment warnings] --> evaluate(VerifyAdHocWarnings.groovy)
   13   06c-Capture-adhoc_payment_id
   14   07-Callback-InvoiceProcessed
   15   08-Poll-Workday (SOAP read-back)
   16   08b-Search-AdHoc-Payment (NEW REST GET Step: /wd-adhoc-payments)
   17      └── Guard: If scenario_type != 'adhoc', skip immediately (0 impact on invoices)
   18      └── Assertion: [verify adhoc payment search] --> evaluate(VerifyAdHocSearch.groovy)
   19   09-Verify-Workday-SOAP (Invoices)
   20   09b-Verify-AdHocPayment-SOAP (Ad-Hoc Payments)
   21   10-Audit-StatusHistory
   22   11-DataSource Loop

  How 08b-Search-AdHoc-Payment Operates Safely:
   1. Guard Condition: In 08b-Search-AdHoc-Payment:

   1    def scenarioType = context.expand('${DataSource#scenario_type}')
   2    if (scenarioType != 'adhoc') {
   3        log.info "Skipping 08b-Search-AdHoc-Payment for scenario_type=${scenarioType}"
   4        testRunner.gotoStepByName("09-Verify-Workday-SOAP") // or return early
   5        return
   6    }
     Ensures 100% zero interference with existing Supplier Invoice scenarios (Rows 1–6).
   2. Dynamic Search Query:
      - payee_name: Extracted from fixture.payee_name.
      - payment_date_on_or_after and payment_date_on_or_before: Set to fixture.payment_date (or YYYY-MM-DD bounds covering the transaction).
      - In 08b-Search-AdHoc-Payment, ReadyAPI issues:
       GET
  /wd-adhoc-payments?payee_name=${#TestCase#payee_name}&payment_date_on_or_after=${#TestCase#payment_date}&payment_date_on_or_before=${#TestCase#pa
  yment_date}
   3. Locating the Created Payment:
      - In VerifyAdHocSearch.groovy, parses the returned JSON array.
      - Finds the payment matching context.expand('${#TestCase#payment_id}') (e.g. AD_HOC_PAYMENT-6-990717).
      - Asserts exact amount (BigDecimal matching fixture.total_payment_amount), currency (USD), and payee reference.

  ---

  D. Deterministic Warnings Test Strategy

  Our live Sandbox findings from the previous run proved how Workday behaves:
   1. Populated Warnings (warnings.size() > 0):
      - In UAT 7, 9, and 10, an inline payee name is submitted without address fields (Address_Data).
      - Workday Sandbox deterministically returns:
       warnings: ["[Warning] - Please Enter Address Details For New Payee", "[Warning] Warning Custom Validation Exceptions Exists. Click \"View
  Exceptions\" button for details."]
   2. Clean / Empty Warnings (warnings.size() == 0):
      - In UAT 8, a complete special handling address is supplied (456 Market St Suite 100, San Francisco, 94105).
      - Workday Sandbox deterministically returns:
       warnings: [] (empty array).
   3. Assertion Logic (VerifyAdHocWarnings.groovy):

    1    // Differentiate expectation based on scenario metadata or presence of address
    2    def expectWarnings = fixture.address == null // If no address, Workday emits missing address alert
    3    if (expectWarnings) {
    4        assert json.warnings instanceof List && !json.warnings.isEmpty() :
    5            "Expected populated warnings array for payment without address, but got: ${json.warnings}"
    6        log.info "Validated populated warnings array: ${json.warnings}"
    7    } else {
    8        assert json.warnings instanceof List && json.warnings.isEmpty() :
    9            "Expected clean (empty) warnings array for payment with valid address, but got: ${json.warnings}"
   10        log.info "Validated empty warnings array for clean payment"
   11    }
     Result: We achieve 100% deterministic testing of both populated and empty warning states without fabricating artificial error payloads or
  creating extra sandbox transactions.

  ---

  E. Exact File-Level Implementation Plan

   1. readyapi/ap-e2e-soapui-project.xml:
      - Add REST TestRequest step 08b-Search-AdHoc-Payment (bound to workday_service_url / /wd-adhoc-payments).
      - Add Groovy assertion on 05-Submit-AdHocPayment delegating to readyapi/Scripts/VerifyAdHocWarnings.groovy.
      - Add Groovy assertion on 08b-Search-AdHoc-Payment delegating to readyapi/Scripts/VerifyAdHocSearch.groovy.
      - Add 08b-Search-AdHoc-Payment script references to 00-Check-ProjectFiles smoke checks.
   2. readyapi/Scripts/VerifyAdHocSearch.groovy (New Modular Script):
      - Parses the JSON response from 08b-Search-AdHoc-Payment.
      - Locates the created payment_id within the payment list.
      - Asserts HTTP 200, JSON array schema, exact Control_Total_Amount (BigDecimal), Currency_Reference, and Payee_Reference.
      - Executes non-mutating validation probes (TC-182-2 to TC-182-5) for empty matches, missing parameters, and date ordering.
   3. readyapi/Scripts/VerifyAdHocWarnings.groovy (New Modular Script):
      - Validates response.warnings on 05-Submit-AdHocPayment.
      - Asserts populated vs. empty warning arrays deterministically based on fixture.address.
   4. readyapi/uat-scenarios.csv:
      - Can remain unchanged, or optionally receive an expected_warnings column (POPULATED vs EMPTY) if explicit CSV data-driving is preferred over
        fixture inference.
   5. Allure Integration (AllureListener.groovy):
      - AllureListener.groovy automatically logs 08b-Search-AdHoc-Payment into the scenario timeline, capturing query parameters, request URLs, and
        response JSON payloads without any custom listener modifications.

  ---

  F. Pending Clarifications & Blockers (With Liliana)

  ┌───────────────────┬──────────────────┬─────────────────────────────┬────────────────────────────────────────────────────────────┐
  │ Item              │ Ticket Text      │ Actual Implemented Contract │ Recommended Path for FPOAP-182                             │
  │                   │ (FPOAP-182)      │                             │                                                            │
  ├───────────────────┼──────────────────┼─────────────────────────────┼────────────────────────────────────────────────────────────┤
  │ payment_reference │ Listed as search │ ❌ Does not exist on GET    │ Filter payments in memory by payment_id from the payee     │
  │ query param       │ filter           │ /wd-adhoc-payments          │ search results. Clarify with Liliana that query param does │
  │                   │                  │                             │ not exist on endpoint.                                     │
  │ supplier_id query │ Listed as search │ ❌ Does not exist (Ad-Hoc   │ Clarify that supplier_id is an invoice concept; payee_name │
  │ param             │ filter           │ payments use Payees, not    │ is the supported parameter.                                │
  │                   │                  │ Suppliers)                  │                                                            │
  │ limit / offset    │ Listed as        │ ❌ Not implemented on GET   │ Clarify that GET /wd-adhoc-payments currently returns a    │
  │ pagination        │ acceptance       │ /wd-adhoc-payments          │ full list; pagination is a future enhancement.             │
  │                   │ criteria         │                             │                                                            │
  └───────────────────┴──────────────────┴─────────────────────────────┴────────────────────────────────────────────────────────────┘
  ---

  G. Validation Strategy

   1. Offline Validation:
      - Groovy compiler (FileSystemCompiler) check on all new scripts.
      - Headless unit tests against mock JSON responses covering matching search results, empty arrays, 400 validation error structures, and
        populated/empty warning payloads.
   2. Live E2E Verification:
      - Execute scoped live test run targeting UAT 7 and UAT 8 in Workday Sandbox Company 100 via Apigee Dev.
      - Confirm UAT 7 validates populated warnings + successful payment search.
      - Confirm UAT 8 validates empty warnings + successful payment search.
      - Run full regression run (Rows 1–6) to guarantee zero regression on supplier invoices.

  ---

  Awaiting your confirmation and approval before creating any files or editing the project XML.
