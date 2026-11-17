Final Pre-Flight Verification Report: ReadyAPI E2E Suite (FPOAP-165)

  All 10 pre-flight checkpoints have been evaluated through read-only inspection. No services were started, no live API calls were made, and no repository files
  were modified.

  ---

  1. Verification of the 10 Checkpoints

  1. Local Services Health (5432 & 8002)
   - AlloyDB Omni (Port 5432): HEALTHY
     - Socket probe: TcpTestSucceeded: True (127.0.0.1:5432).
     - Container alloydb-local is running (Up 35 minutes).
     - Database ap_invoice_processing connected and ready.
   - Local Email Service (Port 8002): HEALTHY
     - Socket probe: TcpTestSucceeded: True (127.0.0.1:8002).
     - Endpoint GET http://localhost:8002/health: Returned {"status": "ok"}.
     - Endpoint GET http://localhost:8002/test-db: Returned {"status": "success", "message": "Database connection test successful."}.

  ---

  2. Effective ReadyAPI Environment Configuration
   - Selected Environment: dev (loaded from readyapi/env/dev.properties and overridden by readyapi/env/local.secrets.properties).
   - Database Target: 127.0.0.1:5432 (ap_invoice_processing, local container).
   - Email Service Target: http://localhost:8002 (local uvicorn process).
   - OIDC Token: local-bypass (bypasses GCP metadata and gcloud identity token requirements).

  ---

  3. Sandbox & Apigee Endpoint Isolation
   - Workday Service Gateway: https://devapis.goaaa.com/v1/s/ap-workday-service (Development Apigee gateway).
   - Workday SOAP Base: https://devapis.goaaa.com/v1/ap-workday (Development Apigee proxy).
   - OAuth Auth URL: https://devapis.goaaa.com/v1/oauth2 (Development OAuth token service).
   - Workday Company: 100 (Workday Sandbox test company).
   - Isolation Status: Confirmed. Zero production domain endpoints or production company entities are configured.

  ---

  4. Network Exposure Assessment (Ports 5432 & 8002)
   - Port 5432 (AlloyDB Omni): Bound strictly to 127.0.0.1:5432 (Loopback). It cannot be accessed by external machines.
   - Port 8002 (Email Service): Bound to 0.0.0.0:8002 (all interfaces) to permit cross-host/container access.
     - Exposure Risk: Local subnet devices could reach port 8002 if Windows Firewall allows inbound connections on non-standard ports. This poses zero production
       risk, but developers on untrusted public Wi-Fi networks should ensure Windows Defender Firewall remains in Public network mode.

  ---

  5. Scenario Execution Scope
   - Suite: E2E-Reference
   - Test Case: InvoiceToWorkday
   - Scope: The DataSource Loop will iterate sequentially across all 10 rows in readyapi/uat-scenarios.csv:
     - Rows 1–6 (Supplier Invoices): Sample invoice, UAT 1 (Non-PO), UAT 2 (Non-PO AutoComplete), UAT 3 (Multi-Supplier Non-PO), UAT 5 (PO Invoice), UAT 6 (PO
       Invoice AutoComplete).
     - Rows 7–10 (Ad-Hoc Payments): UAT 7 (AutoComplete true), UAT 8 (Special Handling address), UAT 9 (Multi-Doc JPG), UAT 10 (Multi-Doc mixed).

  ---

  6. Availability of Fixtures, Attachments, and Scripts
   - Scenarios in CSV: 10 registered scenarios.
   - Fixture JSON Files: 10/10 verified present on disk.
   - Attachment Files: 10/10 verified present on disk (including PDFs, JPGs, and XLSX spreadsheets).
   - Groovy Scripts: All 10 modular scripts in readyapi/Scripts/ are present and verified.

  ---

  7. Recipient & Customer Data Safety
   - Email Recipients: Sourced strictly as seed_to_address = apinvoices-dev@mwg.aaa.com and seed_from_address = e2e-vendor@example.com.
   - Customer Exposure: Zero customer emails, zero real banking accounts, zero production data.

  ---

  8. Inclusion of Commit bf8b25a (FPOAP-165)
   - Branch HEAD: bf8b25a ([FPOAP-165] Add ReadyAPI Ad-Hoc Payment validations for UAT 7-10).
   - File State: readyapi/Scripts/VerifyAdHocPaymentDetails.groovy is compiled and active; readyapi/ap-e2e-soapui-project.xml is configured to evaluate it during
     Step 09b-Verify-AdHocPayment-SOAP.

  ---

  9. Persistent Side Effects & Teardown Analysis
   - Local Database: All rows seeded into processed_emails for each scenario are tagged with subject [E2E ${run_id}]. The test case tearDownScript automatically
     executes:
    DELETE FROM processed_emails WHERE subject LIKE '%${run_id}%'
    Local database records are automatically cleaned up after each run.
   - Workday Sandbox Tenant:
     - Workday SOAP APIs do not support deleting submitted invoices or ad-hoc payments.
     - Transactions created during testing will remain permanently inside the Workday Sandbox Company 100 tenant.
     - Each record is uniquely prefixed with E2E ${run_id}, ensuring zero collision with human testing or subsequent test runs.

  ---

  10. Recommended Execution Command & Artifact Capture

  Execute the full suite headlessly using the local TestRunner binary:

   1 & "$env:LOCALAPPDATA\SmartBear\ReadyAPI-4.2.0\bin\testrunner.bat" `
   2   -s "E2E-Reference" `
   3   -c "InvoiceToWorkday" `
   4   -f "readyapi\reports" `
   5   -j -r -a `
   6   "readyapi\ap-e2e-soapui-project.xml"

  Artifacts Captured:
   1. Console Output: Real-time logging of each scenario, invoice ID, payment ID, polling status, and assertion passes.
   2. JUnit Report: Written to readyapi/reports/TEST-E2E-Reference-InvoiceToWorkday.xml (failure traces, step times).
   3. Allure 2 Results: Emitted to readyapi/reports/allure-results/ (full SOAP XML request/response bodies attached to each step).
   4. Interactive Browser Dashboard: Viewable post-execution via:

   1    .venv\Scripts\python scripts/view_allure_report.py

  ---

  Recommendation: GO

  All environment requirements, local services, credentials, sandbox isolations, and test artifacts are verified and healthy.

  Awaiting your command to begin the live execution of the ReadyAPI E2E test suite.
