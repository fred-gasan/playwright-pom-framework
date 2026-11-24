Phase 1 Finalization & Scope Root Cause Report: Report the scope violation root cause, prevention strategy, and final Phase 1 commit details.


✦ Phase 1 Finalization & Execution Scope Root Cause Report

  ---

  1. Root Cause Analysis: Execution Scope Violation

  Our detailed investigation of readyapi/ap-e2e-soapui-project.xml, the runner log, and the generated files revealed the exact root cause of why
  UAT 7, 9, and 10 (along with Supplier Invoices 1–6) were executed:

    1 <con:dataSource type="File">
    2   <con:name>uat-scenarios</con:name>
    3   <con:type>File</con:type>
    4   <con:file>${#Project#fixture_root}/uat-scenarios.csv</con:file>
    5   ...
    6   <con:configuration>
    7     <fileName>uat-scenarios.csv</fileName>
    8     <separator>,</separator>
    9   </con:configuration>
   10 </con:dataSource>

  The Mechanism:
   1. ReadyAPI Pro FileDataSource Dual-Tag Behavior:
      - In ReadyAPI / SoapUI Pro, the headless TestRunner engine reads the file path from the inner
        <con:configuration><fileName>...</fileName></con:configuration> block, not from the legacy top-level <con:file> attribute.
   2. Hardcoded Unexpanded Path:
      - Inside <con:configuration>, the tag had <fileName>uat-scenarios.csv</fileName> without property expansion.
      - ReadyAPI resolved this relative to the project directory (readyapi/uat-scenarios.csv).
   3. The Ineffectiveness of -P "fixture_root=...":
      - Passing -P "fixture_root=C:/.../uat-scoped-phase1" successfully set the Project-level property ${#Project#fixture_root}.
      - However, because the inner <fileName> tag did not reference ${#Project#fixture_root}, ReadyAPI completely ignored the isolated directory
        and loaded the full tracked readyapi/uat-scenarios.csv.
   4. Execution Result:
      - All 11 scenarios in readyapi/uat-scenarios.csv were read into the DataSource loop and executed sequentially.

  ---

  2. Reliable Prevention Strategy for Future Scoped Runs

  To permanently prevent unintended scenarios from executing during future scoped runs:

   1. DataSource Configuration Alignment:
     Update the XML configuration so <fileName> explicitly references the property:
     <fileName>${#Project#fixture_root}/uat-scenarios.csv</fileName>.
     (When fixture_root is unset, it resolves to the project root; when overridden, ReadyAPI strictly loads the scoped file).
   2. Pre-Flight Row Count Guard (00b-Load-Fixture):
     Add a fail-safe check at the start of the DataSource loop:

   1    def expectedMaxRows = context.expand('${#Project#max_expected_scenarios}')
   2    if (expectedMaxRows) {
   3        def currentRow = testRunner.testCase.testSteps['DataSource'].currentRow
   4        assert currentRow < expectedMaxRows.toInteger() :
   5            "EXECUTION SCOPE ABORT: DataSource exceeded allowed scenario count (${currentRow + 1} > ${expectedMaxRows})"
   6    }
   3. Dry-Run Inspection:
     Run a 1-second dry probe asserting the exact row count that the DataSource step resolves into the test case before dispatching live requests
  with credentials.

  ---

  3. Phase 1 Finalization & Commit Details

   - Active Git Branch: feature/fpoap-182/dev
   - Commit SHA: f0bc52ae94e12ba820b0e7b6d1891d2acd4f1d85
   - Commit Message:
    [FPOAP-182] Add ReadyAPI Ad-Hoc Payment warnings coverage
   - Committed Files (Exactly 5 files):
     1. readyapi/uat-scenarios.csv
     2. readyapi/ap-e2e-soapui-project.xml
     3. readyapi/Scripts/BuildAdHocPaymentRequest.groovy
     4. readyapi/Scripts/VerifyAdHocWarnings.groovy
     5. readyapi/fixtures/uat/adhoc-payment-uat-scenario-11.json
   - Whitespace / Hygiene Check (git diff HEAD~1..HEAD --check): Empty (0 warnings, pure Unix LF).

  ---

  4. Current Git Working Tree Status

   - Unstaged Modified Files (Preserved):
     - scripts/run_local_server.py (Local Windows SelectorEventLoop & auto-venv enhancements).
     - Checkout line-ending differences across tracked repository files (entrypoint.sh, tf/readyapi-job/main.tf, etc.).
   - Untracked Developer Files (Preserved):
     - alembic.local.ini (Local database configuration).
     - readyapi/view_allure_report.bat (Local Allure report launcher).
   - Staged Files: 0 (git diff --staged is empty).
   - Remote Push: None. No commits or branches have been pushed to origin.
   - Phase 2 Scope: Phase 2 (GET search coverage) remains deferred awaiting Liliana's clarifications.

  ---

  Stopping here as instructed. Awaiting your further instructions.
