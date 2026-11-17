Read-Only E2E Environment Readiness Audit Report

  This audit verifies local workstation readiness to execute live ReadyAPI E2E test scenarios against the Workday Sandbox. No services were started, no live API
  calls were made, and no files were modified.

  ---

  1. Test Hierarchy for UAT 7–10

   - Project File: readyapi/ap-e2e-soapui-project.xml
   - Test Suite: E2E-Reference
   - Test Case: InvoiceToWorkday
   - Data Source: uat-scenarios.csv
     - Rows 1–6: Supplier Invoices (Sample, UAT 1, UAT 2, UAT 3, UAT 5, UAT 6)
     - Rows 7–10: Ad-Hoc Payments:
       - Row 7: Adhoc payment with auto_complete true (UAT 7)
       - Row 8: Special handling with Payment Request form address (UAT 8)
       - Row 9: Adhoc reimbursement with multiple attachments including jpg (UAT 9)
       - Row 10: Adhoc reimbursement with multiple attachments (UAT 10)

  ---

  2. Local Service & Infrastructure Availability

  ┌─────────────────────┬───────────────────────┬────────────────┬─────────────────────────────────────────────────────────────────────────────────────────┐
  │ Component           │ Target Port / Address │ Current Status │ Finding                                                                                 │
  ├─────────────────────┼───────────────────────┼────────────────┼─────────────────────────────────────────────────────────────────────────────────────────┤
  │ AlloyDB Omni /      │ 127.0.0.1:5432        │ 🔴 Offline     │ Port 5432 is not accepting connections. Container alloydb-local exists in Podman but is │
  │ PostgreSQL          │                       │ (Closed)       │ currently stopped (Exited).                                                             │
  │ Local Email Service │ http://localhost:8002 │ 🔴 Offline     │ Port 8002 is not accepting connections. The local uvicorn server is not running.        │
  │                     │                       │ (Closed)       │                                                                                         │
  │ ReadyAPI TestRunner │ Local SmartBear       │ 🟢 Ready /     │ ReadyAPI-4.2.0\bin\testrunner.bat is available and verified active with an authorized   │
  │                     │ binary                │ Active         │ ReadyAPI Test license.                                                                  │
  │ Workday Sandbox &   │ devapis.goaaa.com     │ 🟢 Configured  │ Configured in readyapi/env/dev.properties to target the Dev Apigee gateway and Workday  │
  │ Apigee              │                       │                │ Sandbox Company 100.                                                                    │
  └─────────────────────┴───────────────────────┴────────────────┴─────────────────────────────────────────────────────────────────────────────────────────┘
  ---

  3. Credential & Secret Audit (local.secrets.properties)

  Inspecting readyapi/env/local.secrets.properties confirms that all 11 required keys are populated with non-empty, non-placeholder values (no secret values
  displayed):

   - db_host: Configured (127.0.0.1)
   - db_port: Configured (5432)
   - db_name: Configured (ap_invoice_processing)
   - db_user: Configured
   - db_password: Configured
   - email_service_url: Configured (http://localhost:8002)
   - apigee_client_id: Configured
   - apigee_client_secret: Configured
   - workday_soap_client_id: Configured
   - workday_soap_client_secret: Configured
   - oidc_token: Configured (local-bypass)

  ---

  4. Scenario Scope & Execution Behavior

   - Would the full suite execute scenarios beyond UAT 7–10?
    Yes. By default, InvoiceToWorkday iterates through readyapi/uat-scenarios.csv sequentially from Row 1 to Row 10.
     - Rows 1–6 (supplier invoices) will execute first, creating test supplier invoices in the Workday Sandbox before reaching Rows 7–10 (Ad-Hoc payments).
   - Execution Options:
     - Option A (Full Regression — Recommended by Lily): Run the complete 10-row suite to verify that invoice processing remains intact and all 4 ad-hoc payment
       scenarios pass.
     - Option B (Ad-Hoc Scenarios Only): Temporarily target only Rows 7–10 in a scoped CSV if you want to test Ad-Hoc payments in isolation first.

  ---

  5. Non-Sandbox Data Safety Evaluation

   - Database Isolation: All database writes (INSERT INTO processed_emails) and tear-down purges (DELETE FROM processed_emails) target only the local container
     (127.0.0.1:5432) on your workstation.
   - Service Isolation: Email processing callbacks target localhost:8002.
   - Workday Tenant Isolation:
     - workday_service_url: https://devapis.goaaa.com/v1/s/ap-workday-service
     - workday_apigee_base_url: https://devapis.goaaa.com/v1/ap-workday
     - company: 100 (Workday Sandbox non-production tenant entity).
   - Production Risk: Zero. Production endpoints (apis.goaaa.com) and production companies (101, 102, 200, etc.) are not configured anywhere in the suite.

  ---

  6. Identified Blockers Before Execution

   1. Blocker 1: alloydb-local container is stopped. Port 5432 is unreachable; step 01-Seed-ProcessedEmail would immediately fail with a JDBC connection refused
      error.
   2. Blocker 2: Local Email Service is not running. Port 8002 is unreachable; step 04-Callback-ProcessedByQurrent would fail with an HTTP connection refused
      error.

  ---

  7. Step-by-Step Execution Plan

  When you are ready to proceed with live testing, follow this sequence:

  Step 1: Start the Local Database Container

   1 ┌────────────────────────────────────────────────────────┐
   2 │ ⚠️  ACTION 1: Start AlloyDB Omni Local Container       │
   3 │ Run in your terminal:                                  │
   4 │                                                        │
   5 │   podman start alloydb-local                           │
   6 └────────────────────────────────────────────────────────┘

  Step 2: Start the Local Email Service Microservice

   1 ┌────────────────────────────────────────────────────────┐
   2 │ ⚠️  ACTION 2: Start Local Email Service                │
   3 │ Open a separate terminal tab and run:                  │
   4 │                                                        │
   5 │   .venv\Scripts\python scripts/run_local_server.py email│
   6 └────────────────────────────────────────────────────────┘

  Step 3: Verify Pre-Flight Connectivity
  Run our non-intrusive probe to confirm both ports are open and responding:

   1 python .gemini/skills/readyapi-local-dev-setup/scripts/setup_local_env.py --env dev

  Step 4: Execute ReadyAPI E2E Tests
  Run the test runner headlessly with Allure and JUnit reporting enabled:

   1 python .gemini/skills/readyapi-local-dev-setup/scripts/setup_local_env.py --e2e
  (Or via direct TestRunner command):

   1 & "$env:LOCALAPPDATA\SmartBear\ReadyAPI-4.2.0\bin\testrunner.bat" -s "E2E-Reference" -c "InvoiceToWorkday" -f "readyapi\reports" -j -r -a
     "readyapi\ap-e2e-soapui-project.xml"

  Step 5: Review Allure Test Report
  Open the Allure report in your browser to inspect test step results, SOAP request/response payloads, and assertion outputs:
   1 .venv\Scripts\python scripts/view_allure_report.py

  ---

  Awaiting your confirmation to begin with Step 1 and Step 2
