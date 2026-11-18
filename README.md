Scoped Execution Plan: UAT Scenarios 7–10 (Ad-Hoc Payments)

  This plan provides an isolated, safe execution workflow specifically targeting UAT scenarios 7, 8, 9, and 10 without executing the preceding supplier invoices
  (Rows 1–6) and without modifying readyapi/uat-scenarios.csv.

  ---

  1. DataSource & Loop Inspection Findings

  A. How ReadyAPI Resolves the Scenario Table
  In readyapi/ap-e2e-soapui-project.xml (lines 76–80), the DataSource step is configured as:

   1 <con:dataSource type="File">
   2   <con:name>uat-scenarios</con:name>
   3   <con:type>File</con:type>
   4   <con:file>${#Project#fixture_root}/uat-scenarios.csv</con:file>
   - When ${#Project#fixture_root} is not specified, ReadyAPI defaults to readyapi/uat-scenarios.csv.
   - When ${#Project#fixture_root} is overridden at runtime via -P "fixture_root=<path>", ReadyAPI reads <path>/uat-scenarios.csv instead.
   - In readyapi/Scripts/LoadFixture.groovy, fixture JSON files are resolved relative to ${projectDir}:
   1   def fixtureJsonFile = new File(projectDir, fixtureJsonPath)
    Because the CSV rows specify paths relative to readyapi/ (fixtures/uat/adhoc-payment-uat-scenario-*.json), the fixtures, attachments, and scripts resolve
  regardless of where the CSV is stored.

  ---

  2. Isolation Strategy (Zero Git / File Churn)

  To execute only UAT 7–10 without modifying tracked files:
   1. Isolated Scenario Table:
     Place a temporary uat-scenarios.csv inside readyapi/reports/uat-adhoc/:

   1    scenario_type,scenario_name,fixture_path,expected_status,expected_attachment_count
   2    adhoc,Adhoc payment with auto_complete true (UAT 7),fixtures/uat/adhoc-payment-uat-scenario-7.json,Approved,1
   3    adhoc,Special handling with Payment Request form address (UAT 8),fixtures/uat/adhoc-payment-uat-scenario-8.json,Approved,1
   4    adhoc,Adhoc reimbursement with multiple attachments including jpg (UAT 9),fixtures/uat/adhoc-payment-uat-scenario-9.json,Approved,3
   5    adhoc,Adhoc reimbursement with multiple attachments (UAT 10),fixtures/uat/adhoc-payment-uat-scenario-10.json,Approved,3
     (Because readyapi/reports/ is explicitly listed in readyapi/.gitignore, this temporary file creates zero git noise, requires zero git reverts, and leaves the
  original readyapi/uat-scenarios.csv 100% untouched).

   2. Capturing All Results Even If One Fails (-I Flag):
     ReadyAPI TestRunner supports the -I flag ("Do not stop if error occurs, ignore them").
      - In our earlier run without -I, the runner aborted immediately when Row 7 encountered an assertion failure, skipping Rows 8, 9, and 10.
      - Adding -I ensures the runner continues through all 4 iterations of the loop, recording independent JUnit and Allure results for UAT 7, UAT 8, UAT 9, and
        UAT 10.

   3. Dynamic Script Loading:
     Test step 09b-Verify-AdHocPayment-SOAP executes:

   1    def scriptFile = new File(context.expand('${projectDir}'), 'Scripts/VerifyAdHocPaymentDetails.groovy')
   2    evaluate(scriptFile)
     Our updated payee logic (which validates actualPayeeName when descriptors exist, and validates Ad_hoc_Payee_ID / WID reference integrity when descriptors are
  omitted) is read directly from disk on each polling step.

  ---

  3. Readiness & Safety Checkpoints

  ┌────────────────────────────────────────┬──────────────────────────────────────────────────────────────┬─────────────────────┐
  │ Component                              │ Target / Value                                               │ Status              │
  ├────────────────────────────────────────┼──────────────────────────────────────────────────────────────┼─────────────────────┤
  │ AlloyDB Omni (Container alloydb-local) │ Port 5432 (bound strictly to 127.0.0.1)                      │ 🟢 Healthy / Online │
  │ Local Email Service                    │ Port 8002 (GET /health -> {"status": "ok"})                  │ 🟢 Healthy / Online │
  │ Workday Sandbox Endpoint               │ https://devapis.goaaa.com/v1/ap-workday (Company 100)        │ 🟢 Development Only │
  │ Apigee OAuth Endpoint                  │ https://devapis.goaaa.com/v1/oauth2                          │ 🟢 Development Only │
  │ Environment Binding                    │ E2E_ENV=dev                                                  │ 🟢 Verified         │
  │ Git / Branch State                     │ feature/fpoap-165-adhoc-payment-validations (PR #234 intact) │ 🟢 Preserved        │
  └────────────────────────────────────────┴──────────────────────────────────────────────────────────────┴─────────────────────┘
  ---

  4. Step-by-Step Execution Procedure

  Step A: Generate the Isolated Ad-Hoc Scenario Table
  Write the 4-row CSV to the git-ignored folder readyapi/reports/uat-adhoc/uat-scenarios.csv:

   1 New-Item -ItemType Directory -Path "readyapi\reports\uat-adhoc" -Force | Out-Null
   2 @'
   3 scenario_type,scenario_name,fixture_path,expected_status,expected_attachment_count
   4 adhoc,Adhoc payment with auto_complete true (UAT 7),fixtures/uat/adhoc-payment-uat-scenario-7.json,Approved,1
   5 adhoc,Special handling with Payment Request form address (UAT 8),fixtures/uat/adhoc-payment-uat-scenario-8.json,Approved,1
   6 adhoc,Adhoc reimbursement with multiple attachments including jpg (UAT 9),fixtures/uat/adhoc-payment-uat-scenario-9.json,Approved,3
   7 adhoc,Adhoc reimbursement with multiple attachments (UAT 10),fixtures/uat/adhoc-payment-uat-scenario-10.json,Approved,3
   8 '@ | Set-Content -Path "readyapi\reports\uat-adhoc\uat-scenarios.csv" -Encoding UTF8

  Step B: Execute ReadyAPI TestRunner (Scoped to UAT 7–10)

   1 & "$env:LOCALAPPDATA\SmartBear\ReadyAPI-4.2.0\bin\testrunner.bat" `
   2   -s "E2E-Reference" `
   3   -c "InvoiceToWorkday" `
   4   -P "E2E_ENV=dev" `
   5   -P "fixture_root=C:/Users/611537/accounts-payable-invoice-processing/readyapi/reports/uat-adhoc" `
   6   -f "C:\Users\611537\accounts-payable-invoice-processing\readyapi\reports" `
   7   -I `
   8   -j -r -a `
   9   "C:\Users\611537\accounts-payable-invoice-processing\readyapi\ap-e2e-soapui-project.xml"

  Step C: Post-Execution Results Reporting
   1. Extract and report individual results for UAT 7, 8, 9, and 10 from readyapi/reports/allure-results/.
   2. Confirm local database cleanup completed (processed_emails count).
   3. Delete the temporary directory readyapi/reports/uat-adhoc/.

  ---

  5. Remaining Blockers

  None.
   - No files have been modified.
   - No live tests have been run yet.

  Please confirm when you would like me to execute this plan.
