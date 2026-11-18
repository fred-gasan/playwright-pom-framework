 Scoped Live ReadyAPI E2E Test Execution Report: UAT 7–10 (100% PASS)

  The scoped live E2E test suite (InvoiceToWorkday across UAT scenarios 7, 8, 9, and 10) was executed against the authorized Workday Sandbox Company 100 via the
  Apigee Dev gateway, local database (alloydb-local), and local Email Service (localhost:8002), utilizing the isolated DataSource in readyapi/reports/uat-adhoc/.

  ---

  1. Overall Status: 100% PASS (4 Passed, 0 Failed, 0 Skipped)

   - Total Scenarios Executed: 4
   - Passed Scenarios: 4 / 4 (100%)
   - Failed Scenarios: 0
   - Total Test Steps: 148
   - Total Request Assertions: 203 (203 Passed, 0 Failed)
   - Total Execution Time: 73.8 seconds
   - TestRunner Exit Code: 0

  ---

  2. Individual Scenario Results

  ┌──────────┬─────────────────────────────────────────────────────────────────────┬─────────┬───────────┬────────────────────────────┐
  │ Scenario │ Scenario Name                                                       │ Status  │ Attempt   │ Workday Sandbox Payment ID │
  ├──────────┼─────────────────────────────────────────────────────────────────────┼─────────┼───────────┼────────────────────────────┤
  │ UAT 7    │ Adhoc payment with auto_complete true (UAT 7)                       │ 🟢 PASS │ Attempt 1 │ AD_HOC_PAYMENT-6-990717    │
  │ UAT 8    │ Special handling with Payment Request form address (UAT 8)          │ 🟢 PASS │ Attempt 1 │ AD_HOC_PAYMENT-6-990718    │
  │ UAT 9    │ Adhoc reimbursement with multiple attachments including jpg (UAT 9) │ 🟢 PASS │ Attempt 1 │ AD_HOC_PAYMENT-6-990719    │
  │ UAT 10   │ Adhoc reimbursement with multiple attachments (UAT 10)              │ 🟢 PASS │ Attempt 1 │ AD_HOC_PAYMENT-6-990720    │
  └──────────┴─────────────────────────────────────────────────────────────────────┴─────────┴───────────┴────────────────────────────┘
  ---

  3. Detailed Validation Highlights & Warnings

   1. Payee Reference Integrity Validation: 🟢 PASSED (Attempt 1 on all 4)
      - All 4 scenarios successfully confirmed valid Workday Ad-Hoc Payee reference IDs returned in the SOAP response without false descriptor failures:
        - UAT 7: Confirmed AD_HOC_PAYEE-6-855785
        - UAT 8: Confirmed AD_HOC_PAYEE-6-855786
        - UAT 9: Confirmed AD_HOC_PAYEE-6-855787
        - UAT 10: Confirmed AD_HOC_PAYEE-6-855788
   2. Financial Precision & Accounting Worktags: 🟢 PASSED
      - Exact BigDecimal amounts confirmed in Workday for $500.00, $750.00, $1250.00, and $820.50.
      - Cost Center Worktag 6300 and Location Worktag 613 confirmed on all lines.
   3. Special Handling Address (UAT 8): 🟢 PASSED
      - Workday Address_Data verified: 456 Market St Suite 100, San Francisco, 94105.
   4. Multi-Document Attachments (UAT 9 & 10): 🟢 PASSED
      - Confirmed all attachments present in Workday by count (3) and individual filenames (payment-request-form.pdf, receipt-photo.jpg, expenses-breakdown.xlsx,
        travel-approval.pdf, hotel-folio.jpg, mileage-log.xlsx).
   5. Workday Approval Status Warning (Logged As Required):
      - Workday Cash Management's Get_Ad_hoc_Payments response omits a Document Status ID in its default payload.
      - Explicit Warning Logged in All 4 Scenarios:

   1      [log] Workday Cash Management response does not expose Status ID; Workday approval status was NOT verified
      - Confirmation: Successful payment creation, HTTP 200, and database audit transitions were not conflated with Workday approval. The UAT 7 Workday Approved
        business status remains documented as unverified at the SOAP layer.

  ---

  4. Report Artifact Locations

  All previous reports were preserved, and the new run results were isolated into a dedicated folder:
   - Dedicated JUnit XML Report: readyapi/reports/run-adhoc-rerun/TEST-E2EReference.xml
   - Dedicated HTML Summary: readyapi/reports/run-adhoc-rerun/index.html
   - Execution Step Logs: readyapi/reports/run-adhoc-rerun/E2EReference-InvoiceToWorkday-*.txt
   - Allure 2 Results: readyapi/reports/allure-results/ (All 4 latest scenario result objects recorded as passed).
   - Interactive Allure HTML Dashboard: Can be viewed anytime via .venv\Scripts\python scripts/view_allure_report.py.

  ---

  5. Persistent Artifacts & Database Cleanup

   - Local Database Cleanup: Confirmed. The test case tearDownScript automatically purged all seeded records matching run_id = 8743d5e7 from
     public.processed_emails (Rows for run_id 8743d5e7: 0).
   - Workday Sandbox Records Created:
     - AD_HOC_PAYMENT-6-990717 (UAT 7)
     - AD_HOC_PAYMENT-6-990718 (UAT 8)
     - AD_HOC_PAYMENT-6-990719 (UAT 9)
     - AD_HOC_PAYMENT-6-990720 (UAT 10)

  ---

  6. Git Working Tree Status

   - PR #234 Branch: feature/fpoap-165-adhoc-payment-validations remains active.
   - Tracked Files Modified: Only readyapi/Scripts/VerifyAdHocPaymentDetails.groovy (has the payee descriptor fallback, quoted @'Descriptor', and conditional
     status assertion).
   - Unrelated Local Changes: scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat, and line-ending differences remain completely untouched and
     unstaged.
   - Remote State: No commits, pushes, or PR updates have been performed.

  ---

  Ready for your instructions on staging the updated VerifyAdHocPaymentDetails.groovy and updating PR #234.
