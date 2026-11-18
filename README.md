Scoped ReadyAPI Live E2E Execution Report: UAT Scenarios 7–10

  The scoped live E2E test run targeting all 4 Ad-Hoc Payment scenarios (UAT 7, 8, 9, 10) was executed against the authorized Workday Sandbox Company 100 via the
  Apigee Dev gateway, local database (alloydb-local), and local Email Service.

  ---

  1. Overall Status: ALL 4 SCENARIOS EXECUTED

   - Scenarios in Run: 4 (UAT 7, UAT 8, UAT 9, UAT 10)
   - Scenarios Successfully Executed in Workday Sandbox: 4 / 4
   - Test Runner Exit Code: 0 (Completed all iterations through the DataSource Loop using -I)
   - Business Validations Status: 100% of Payee, Amount, Currency, Line Items, Financial Coding, Address, and Attachment validations PASSED in all 4 scenarios.
   - Single Blocked Assertion: The strict assert statusId check failed in all 4 iterations because Workday Cash Management's Get_Ad_hoc_Payments response does not
     return a Document Status element without a specialized response filter.

  ---

  2. Individual Results for UAT 7, 8, 9, and 10

  ┌──────────┬────────────────────────────┬───────────────────────────────────────┬────────────────────────────────────────────┬───────────────────────┐
  │ Scenario │ Workday Sandbox Payment ID │ Core Validations Status               │ Attachment / Address Status                │ Failure Message       │
  │          │ Created                    │                                       │                                            │                       │
  ├──────────┼────────────────────────────┼───────────────────────────────────────┼────────────────────────────────────────────┼───────────────────────┤
  │ UAT 7    │ AD_HOC_PAYMENT-6-990713    │ 🟢 PASS (Currency, Amount $500, Payee │ 🟢 PASS (adhoc-payment-uat-scenario-7.pdf) │ Status ID missing in  │
  │          │                            │ Ref, Line Amount, Worktags CC         │                                            │ Workday response for  │
  │          │                            │ 6300/Loc 613, Line & Header Memos)    │                                            │ auto_complete payment │
  │ UAT 8    │ AD_HOC_PAYMENT-6-990714    │ 🟢 PASS (Currency, Amount $750, Payee │ 🟢 PASS (Address_Data matched: 456 Market  │ Status ID missing in  │
  │          │                            │ Ref, Line Amount, Worktags CC         │ St, San Francisco, 94105)                  │ Workday response for  │
  │          │                            │ 6300/Loc 613, Line & Header Memos)    │                                            │ auto_complete payment │
  │ UAT 9    │ AD_HOC_PAYMENT-6-990715    │ 🟢 PASS (Currency, Amount $1250,      │ 🟢 PASS (All 3 files present in Workday:   │ Status ID missing in  │
  │          │                            │ Payee Ref, Line Amount, Worktags CC   │ .pdf, .jpg, .xlsx)                         │ Workday response for  │
  │          │                            │ 6300/Loc 613, Line & Header Memos)    │                                            │ auto_complete payment │
  │ UAT 10   │ AD_HOC_PAYMENT-6-990716    │ 🟢 PASS (Currency, Amount $820.50,    │ 🟢 PASS (All 3 files present in Workday:   │ Status ID missing in  │
  │          │                            │ Payee Ref, Line Amount, Worktags CC   │ .pdf, .jpg, .xlsx)                         │ Workday response for  │
  │          │                            │ 6300/Loc 613, Line & Header Memos)    │                                            │ auto_complete payment │
  └──────────┴────────────────────────────┴───────────────────────────────────────┴────────────────────────────────────────────┴───────────────────────┘
  ---

  3. Detailed Validation Highlights

   1. Payee Reference Integrity Validation: 🟢 100% SUCCESS ACROSS ALL 4 SCENARIOS
      - Our updated payee verification correctly handled Workday Sandbox's reference-only responses without false failures:
        - UAT 7: Confirmed AD_HOC_PAYEE-6-855781
        - UAT 8: Confirmed AD_HOC_PAYEE-6-855782
        - UAT 9: Confirmed AD_HOC_PAYEE-6-855783
        - UAT 10: Confirmed AD_HOC_PAYEE-6-855784
   2. Financial Amounts & Currency: 🟢 100% SUCCESS
      - Exact BigDecimal matches for $500.00, $750.00, $1250.00, and $820.50 in USD.
   3. Accounting Worktags: 🟢 100% SUCCESS
      - Cost Center 6300 and Location 613 confirmed on all lines in Workday.
   4. Special Handling Address (UAT 8): 🟢 100% SUCCESS
      - Confirmed Address_Line_Data = "456 Market St Suite 100", Municipality = "San Francisco", Postal_Code = "94105" in Workday.
   5. Multi-Document Attachments (UAT 9 & 10): 🟢 100% SUCCESS
      - Confirmed individual document filenames returned in Workday SOAP Attachment_Data (payment-request-form.pdf, receipt-photo.jpg, expenses-breakdown.xlsx,
        travel-approval.pdf, hotel-folio.jpg, mileage-log.xlsx).

  ---

  4. Root Cause Analysis for Status Assertion Failure

   - The Error:
    [validate adhoc payment state] Status ID missing in Workday response for auto_complete payment. Expression: statusId. Values: statusId = 
   - Why it occurred:
    In Workday Cash Management (Get_Ad_hoc_Payments), unlike Supplier Invoices (Get_Supplier_Invoices), the default read-back XML payload does not include a
  Document Status node (*Status_ID*).
    Inspection of all 32 IDs returned in the live SOAP response confirmed that Workday returns:
    Ad_hoc_Payment_Reference_ID, Payment_Reference_ID, Organization_Reference_ID, Company_Reference_ID, Bank_Account_ID, Ad_hoc_Payee_ID, Currency_ID,
  Currency_Numeric_Code, Currency_Rate_Type_ID, Tax_Option_ID, Payment_Type_ID, Spend_Category_ID, Accounting_Treatment_ID, Location_ID, Cost_Center_Reference_ID.
    No Status ID element exists in the response.
   - Alignment with Original Code:
    This proves why Lily Allen's original script had:

   1   if (statusId) {
   2       assert statusId.equalsIgnoreCase('APPROVED') : "..."
   3   }
    When we added assert statusId, it assumed Workday would return this element. The fix is to restore the conditional check: if statusId is returned, assert
  APPROVED; if not returned, do not fail.

  ---

  5. Artifact Locations & Database Cleanup

   - JUnit Test Report: readyapi/reports/TEST-E2EReference.xml
   - Execution Step Logs:
     - readyapi/reports/E2EReference-InvoiceToWorkday-09bVerifyAdHocPaymentSOAP-0-FAIL.txt (UAT 7)
     - readyapi/reports/E2EReference-InvoiceToWorkday-09bVerifyAdHocPaymentSOAP-1-FAIL.txt (UAT 8)
     - readyapi/reports/E2EReference-InvoiceToWorkday-09bVerifyAdHocPaymentSOAP-2-FAIL.txt (UAT 9)
     - readyapi/reports/E2EReference-InvoiceToWorkday-09bVerifyAdHocPaymentSOAP-3-FAIL.txt (UAT 10)
   - Allure 2 Results: readyapi/reports/allure-results/ (All 4 scenario result objects preserved).
   - Local Database Cleanup: Confirmed. All [E2E ${run_id}] rows seeded for each test iteration were cleaned up by tearDownScript.
   - Workday Sandbox Records Created:
     - AD_HOC_PAYMENT-6-990713 (UAT 7)
     - AD_HOC_PAYMENT-6-990714 (UAT 8)
     - AD_HOC_PAYMENT-6-990715 (UAT 9)
     - AD_HOC_PAYMENT-6-990716 (UAT 10)

  ---

  6. Git Working Tree Status

   - PR #234 & Branch: feature/fpoap-165-adhoc-payment-validations is unchanged and preserved.
   - Tracked Files: readyapi/Scripts/VerifyAdHocPaymentDetails.groovy is the only modified file.
   - Unrelated Files: scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat remain untouched.
   - Reports: All test reports and logs are preserved in readyapi/reports/.

  ---

  Ready for your instructions on whether to align the statusId assertion with the original conditional pattern so all 4 scenarios achieve 100% PASS.
