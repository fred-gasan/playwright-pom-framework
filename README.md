Controlled Live ReadyAPI E2E Execution Report: FPOAP-182 Phase 1 (Warnings Coverage)

  The controlled live E2E test execution targeting Ad-Hoc Payment warnings coverage (UAT 8 and UAT 11) was executed against the authorized Workday
  Sandbox Company 100 via Apigee Dev, local AlloyDB (alloydb-local), and local Email Service (localhost:8002).

  ---

  A. Environment Readiness

   - Target Tenant: Workday Sandbox Company 100 via Apigee Dev (https://devapis.goaaa.com/v1/s/ap-workday-service)
   - Active Git Branch: feature/fpoap-182/dev (at commit 45e8091)
   - Local AlloyDB Container (alloydb-local): Port 5432 🟢 Online
   - Local Email Service: Port 8002 (GET /health -> {"status": "ok"}) 🟢 Online
   - ReadyAPI License & Core: ReadyAPI 4.2.0 Test License initialized cleanly.

  ---

  B. Exact Scoped Execution Command

   1 & "$env:LOCALAPPDATA\SmartBear\ReadyAPI-4.2.0\bin\testrunner.bat" `
   2   -s "E2E-Reference" `
   3   -c "InvoiceToWorkday" `
   4   -P "E2E_ENV=dev" `
   5   -P "fixture_root=C:/Users/611537/accounts-payable-invoice-processing/readyapi/reports/uat-scoped-phase1" `
   6   -f "C:\Users\611537\accounts-payable-invoice-processing\readyapi\reports\run-fpoap-182-phase1" `
   7   -j -r -a `
   8   "C:\Users\611537\accounts-payable-invoice-processing\readyapi\ap-e2e-soapui-project.xml"

  ---

  C. Per-Scenario PASS/FAIL Results

  ┌──────────┬─────────────────────────────────────┬────────┬─────────┬─────────────────────────────────────────────────────────┬─────────┐
  │ Scenario │ Scenario Name                       │ Status │ Attempt │ Warnings Assertion Result                               │ Overall │
  │          │                                     │        │         │                                                         │ Result  │
  ├──────────┼─────────────────────────────────────┼────────┼─────────┼─────────────────────────────────────────────────────────┼─────────┤
  │ UAT 8    │ Special handling with Payment       │ 🟢     │ Attempt │ [validate adhoc payment warnings] PASS (Strictly empty  │ 🟢 PASS │
  │          │ Request form address (UAT 8)        │ PASS   │ 1       │ array [] validated)                                     │         │
  │ UAT 11   │ Adhoc payment with explicit warning │ 🟢     │ Attempt │ [validate adhoc payment warnings] PASS (Populated array │ 🟢 PASS │
  │          │ message (UAT 11)                    │ PASS   │ 1       │ matching substring Please Enter Address Details         │         │
  │          │                                     │        │         │ validated)                                              │         │
  │ (UAT 7)  │ Adhoc payment with auto_complete    │ 🟢     │ Attempt │ [validate adhoc payment warnings] PASS (Populated array │ 🟢 PASS │
  │          │ true (UAT 7)                        │ PASS   │ 1       │ validated)                                              │         │
  │ (UAT 9)  │ Adhoc reimbursement with multiple   │ 🟢     │ Attempt │ [validate adhoc payment warnings] PASS (Populated array │ 🟢 PASS │
  │          │ attachments including jpg (UAT 9)   │ PASS   │ 1       │ validated)                                              │         │
  │ (UAT 10) │ Adhoc reimbursement with multiple   │ 🟢     │ Attempt │ [validate adhoc payment warnings] PASS (Populated array │ 🟢 PASS │
  │          │ attachments (UAT 10)                │ PASS   │ 1       │ validated)                                              │         │
  └──────────┴─────────────────────────────────────┴────────┴─────────┴─────────────────────────────────────────────────────────┴─────────┘

  ---

  D. Actual Warnings Response Evidence (Recorded from Live POST Responses)

  1. UAT 8 (Clean Scenario with Full Address):
   - Live Response File: readyapi/reports/run-fpoap-182-phase1/E2EReference-InvoiceToWorkday-05SubmitAdHocPayment-1-PASS.txt
   - Recorded Response Payload:

   1   {
   2     "payment_id": "AD_HOC_PAYMENT-6-990891",
   3     "workday_wid": "acb3493d747f9001092cf8ee93ac0000",
   4     "warnings": []
   5   }
   - Assertion Log:
    Validated empty warnings array for clean Ad-Hoc payment scenario

  2. UAT 11 (Inline Payee Without Address + Explicit warning_message):
   - Live Response File: readyapi/reports/run-fpoap-182-phase1/E2EReference-InvoiceToWorkday-05SubmitAdHocPayment-4-PASS.txt
   - Recorded Response Payload:

   1   {
   2     "payment_id": "AD_HOC_PAYMENT-6-990894",
   3     "workday_wid": "acb3493d747f9001092f49dd1b4d0000",
   4     "warnings": [
   5       "[Warning] - Please Enter Address Details For New Payee",
   6       "[Warning] Warning Custom Validation Exceptions Exists. Click \"View Exceptions\" button for details."
   7     ]
   8   }
   - Assertion Log:
    Validated populated warnings array contains expected substring 'Please Enter Address Details': [[Warning] - Please Enter Address Details For
  New Payee, [Warning] Warning Custom Validation Exceptions Exists. Click "View Exceptions" button for details.]
   - Outgoing Payload Verification: Outgoing request included "warning_message": "DW Warning: Review required by AP specialist - suspected
     duplicate review".

  ---

  E. Assertion Counts & Execution Duration

   - Total Execution Time: 67.4 seconds
   - Total TestSteps Executed: 162
   - Total Request Assertions: 226 (226 Passed, 0 Failed, 0 Warnings)
   - TestRunner Exit Code: 0

  ---

  F. Created Sandbox Payment IDs

  Persistent Workday Sandbox Company 100 payments created during this live run:
   - UAT 8: AD_HOC_PAYMENT-6-990891 (Workday WID acb3493d747f9001092cf8ee93ac0000)
   - UAT 11: AD_HOC_PAYMENT-6-990894 (Workday WID acb3493d747f9001092f49dd1b4d0000)
   - (UAT 7: AD_HOC_PAYMENT-6-990890, UAT 9: AD_HOC_PAYMENT-6-990892, UAT 10: AD_HOC_PAYMENT-6-990893)

  ---

  G. Database Cleanup & Report Locations

   - Local Database Cleanup: Confirmed. The test case tearDownScript automatically purged seeded rows matching run_id = 4a7b1c40 from
     public.processed_emails (Remaining rows for run_id 4a7b1c40: 0).
   - Isolated JUnit Report: readyapi/reports/run-fpoap-182-phase1/TEST-E2EReference.xml
   - Isolated HTML Summary: readyapi/reports/run-fpoap-182-phase1/index.html
   - Individual Step Logs: readyapi/reports/run-fpoap-182-phase1/E2EReference-InvoiceToWorkday-*.txt
   - Allure 2 Results: readyapi/reports/allure-results/ (All scenario result objects recorded as passed).

  ---

  H. Verification & Working Tree Status

   - Failures / Anomalies: None. All warnings assertions, JSON schema checks, string type validations, and existing SOAP read-backs passed on
     attempt 1.
   - Git Working Tree Status: Strictly the 5 intended Phase 1 files modified/created. No files staged, no commits created, and all unrelated
     developer tools remain intact.

  ---

  Stopping here as instructed. We are ready to proceed with committing Phase 1 or awaiting Liliana's clarification on Phase 2 GET search criteria.
