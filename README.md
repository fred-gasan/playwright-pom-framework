ReadyAPI E2E Test Execution Report: FPOAP-165 (Live Workday Sandbox)

  The live ReadyAPI E2E test suite (E2E-Reference -> InvoiceToWorkday) was executed against the local database (alloydb-local), local Email Service, and the
  remote Workday Sandbox via Apigee Dev gateway.

  ---

  1. Overall Status: FAIL (6 Passed, 1 Failed, 3 Skipped)

   - Total Scenarios Registered: 10
   - Scenarios Executed: 7
   - Passed Scenarios: 6 (Rows 1–6: All Supplier Invoices passed end-to-end)
   - Failed Scenario: 1 (Row 7: UAT 7 Ad-Hoc Payment failed on Payee Descriptor assertion)
   - Skipped Scenarios: 3 (Rows 8, 9, 10 did not execute due to test runner halt on Row 7 failure)

  ---

  2. Individual Scenario Results (All 10 Rows)

  ┌─────┬───────────────┬────────────────────────────────────────────────────────────┬─────────┬─────────────────────────────────────┐
  │ Row │ Scenario Type │ Scenario Name                                              │ Status  │ Notes                               │
  ├─────┼───────────────┼────────────────────────────────────────────────────────────┼─────────┼─────────────────────────────────────┤
  │ 1   │ invoice       │ sample-autocomplete-invoice                                │ 🟢 PASS │ Completed in 14.1s                  │
  │ 2   │ invoice       │ Single EIB - Non PO Invoice (Auto Complete = No) (UAT 1)   │ 🟢 PASS │ Completed in 12.3s                  │
  │ 3   │ invoice       │ Single EIB - Non PO Invoice (Auto Complete = Yes) (UAT 2)  │ 🟢 PASS │ Completed in 11.8s                  │
  │ 4   │ invoice       │ Multi-Supplier EIB Non-PO Invoices (UAT 3)                 │ 🟢 PASS │ Completed in 15.2s                  │
  │ 5   │ invoice       │ Single EIB - PO Invoice (Auto Complete = No) (UAT 5)       │ 🟢 PASS │ Completed in 13.9s                  │
  │ 6   │ invoice       │ Single EIB - PO Invoice (Auto Complete = Yes) (UAT 6)      │ 🟢 PASS │ Completed in 12.4s                  │
  │ 7   │ adhoc         │ Adhoc payment with auto_complete true (UAT 7)              │ 🔴 FAIL │ Payment submitted; assertion failed │
  │ 8   │ adhoc         │ Special handling with Payment Request form address (UAT 8) │ ⚪ SKIP │ Not reached due to failure in Row 7 │
  │ 9   │ adhoc         │ Adhoc reimbursement with multiple attachments (UAT 9)      │ ⚪ SKIP │ Not reached due to failure in Row 7 │
  │ 10  │ adhoc         │ Adhoc reimbursement with multiple attachments (UAT 10)     │ ⚪ SKIP │ Not reached due to failure in Row 7 │
  └─────┴───────────────┴────────────────────────────────────────────────────────────┴─────────┴─────────────────────────────────────┘
  ---

  3. Detailed Validation Results for UAT 7

  In Row 7, the test step flow completed:
   1. 01-Seed-ProcessedEmail: Row seeded in local database.
   2. 04-Callback-ProcessedByQurrent: Local Email Service responded HTTP 200.
   3. 05b-Build-AdHocPayment-Request: Built request payload with attachment adhoc-payment-uat-scenario-7.pdf.
   4. 05-Submit-AdHocPayment: Successfully created Ad-Hoc Payment in Workday Sandbox.
      - Generated Payment ID: AD_HOC_PAYMENT-6-990706
   5. 06c-Capture-adhoc_payment_id: Captured AD_HOC_PAYMENT-6-990706.
   6. 07-Callback-InvoiceProcessed: Local Email Service updated status history to invoice processed in workday.
   7. 08-Poll-Workday: Polled 09b-Verify-AdHocPayment-SOAP.
   8. 09b-Verify-AdHocPayment-SOAP: Received real Workday Cash Management SOAP response:
      - HTTP 200: 🟢 PASS
      - no SOAP fault: 🟢 PASS
      - adhoc payment found: 🟢 PASS
      - memo carries run_id: 🟢 PASS (E2E run_id=8f449266 UAT 7 Adhoc Payment AutoComplete Yes)
      - attachment count matches expected: 🟢 PASS (1 attachment)
      - VerifyAdHocPaymentDetails.groovy validations:
        - Currency: 🟢 PASS (<wd:Currency_ID>USD</wd:ID>)
        - Control Total Amount: 🟢 PASS (<wd:Control_Total_Amount>500</wd:Control_Total_Amount>)
        - Line Extended Amount: 🟢 PASS (<wd:Extended_Amount>500</wd:Extended_Amount>)
        - Line Spend Category: 🟢 PASS (<wd:Spend_Category_ID>PREMIER_MEMBER_BENEFITS</wd:Spend_Category_ID>, WID 0192d8c1390c4d96ac858b19fb5f4571)
        - Line Cost Center: 🟢 PASS (<wd:Cost_Center_Reference_ID>6300</wd:Cost_Center_Reference_ID>)
        - Line Location: 🟢 PASS (<wd:Location_ID>613</wd:Location_ID>)
        - Line Memo: 🟢 PASS (<wd:Item_Description>E2E 8f449266 UAT 7 Consultation fee</wd:Item_Description>)
        - Header Memo: 🟢 PASS (<wd:Memo>E2E run_id=8f449266 UAT 7 Adhoc Payment AutoComplete Yes</wd:Memo>)
        - Attachment Data: 🟢 PASS (wd:Filename="adhoc-payment-uat-scenario-7.pdf")
        - Payee Assertion: 🔴 FAIL

  ---

  4. Failed Step & Root Cause Analysis

   - Failing Step: 09b-Verify-AdHocPayment-SOAP -> assertion validate adhoc payment state
   - Exact Assertion Failure Message:

   1   [validate adhoc payment state] Payee descriptor or name missing in Workday response for expected payee 'Acme Services LLC'.
   2   Expression: actualPayee. Values: actualPayee = 
   - Root Cause Discovered in Real SOAP Response:
    The actual Workday Sandbox read-back response returned the payee reference without a text descriptor:

   1   <wd:Payee_Reference>
   2      <wd:ID wd:type="WID">e13fc11a07d3100120eef533cdae0000</wd:ID>
   3      <wd:ID wd:type="Ad_hoc_Payee_ID">AD_HOC_PAYEE-6-855774</wd:ID>
   4   </wd:Payee_Reference>
    The request step 09b-Verify-AdHocPayment-SOAP queries Workday by reference ID without a Response_Filter or Include_Reference descriptor option. Workday
  returns only the reference IDs (WID and Ad_hoc_Payee_ID), omitting text descriptor attributes (@wd:Descriptor) and child name elements (Ad_Hoc_Payee_Name).

    Because our script contained the strict fail-fast check:

   1   assert actualPayee : "Payee descriptor or name missing in Workday response for expected payee '${expectedPayee}'"
    it failed when Workday returned only IDs.
    (This explains why Lily Allen's VerifyInvoiceDetails.groovy for supplier invoices uses if (expectedSupplier && actualSupplier) rather than failing when
  reference descriptors are omitted by Workday).

  ---

  5. Report Artifact Locations

   - JUnit XML Report: readyapi/reports/TEST-E2EReference.xml
   - Step Execution Logs: readyapi/reports/E2EReference-InvoiceToWorkday-*.txt
     - Failure trace log: readyapi/reports/E2EReference-InvoiceToWorkday-08PollWorkday-13-FAIL.txt
   - Allure 2 Results: readyapi/reports/allure-results/ (6 passed scenario results recorded).
   - Interactive Allure HTML Report: Can be generated via .venv\Scripts\python scripts/view_allure_report.py.

  ---

  6. Local Database Cleanup Confirmation

   - Status: Completed.
   - Verification query against local AlloyDB (public.processed_emails) confirmed that all seeded records matching run_id = 8f449266 were automatically deleted by
     the test case tearDownScript.
   - Rows matching run_id 8f449266: 0.

  ---

  7. Remaining Blockers Before PR #234 Review

   - Blocker: The strict assert actualPayee check in readyapi/Scripts/VerifyAdHocPaymentDetails.groovy must be made conditional or resilient to Workday responses
     that omit the optional text descriptor (matching VerifyInvoiceDetails.groovy conventions):

   1   if (actualPayee) {
   2       assert actualPayee.toLowerCase().contains(expectedPayee.toLowerCase()) || expectedPayee.toLowerCase().contains(actualPayee.toLowerCase()) :
   3           "Payee mismatch: expected '${expectedPayee}', but Workday returned '${actualPayee}'"
   4       log.info "Validated Payee: ${actualPayee}"
   5   } else {
   6       log.info "Payee reference returned by Workday without text descriptor (WID/ID only)"
   7   }
   - Execution Rule Respected: As instructed, no code has been modified, and the test was not rerun.

  Awaiting your decision on whether to apply this surgical correction to VerifyAdHocPaymentDetails.groovy.
