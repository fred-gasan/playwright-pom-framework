The payee validation in readyapi/Scripts/VerifyAdHocPaymentDetails.groovy has been updated and comprehensively verified through offline testing.

  ---

  1. Exact Code Changes Applied

    1 // 3. Validate Payee Name and Reference Integrity
    2 def expectedPayee = fixture.payee_name ?: context.expand('${expected_payee_name}')
    3 assert expectedPayee : "Expected payee_name missing in fixture data"
    4
    5 def payeeNode = paymentData.'**'.find { it.name() =~ /(?i)payee.*reference/ } ?: paymentNode.'**'.find { it.name() =~ /(?i)payee.*reference/ }
    6 assert payeeNode != null : "Payee_Reference node missing in Workday response"
    7
    8 // Extract descriptor attribute or name element if present in response
    9 def actualPayeeName = payeeNode.@'wd:Descriptor'?.text() ?:
   10                       payeeNode.@Descriptor?.text() ?:
   11                       payeeNode.attributes()?.find { k, v -> k =~ /(?i)descriptor/ }?.value ?:
   12                       paymentData.'**'.find { it.name() =~ /(?i)payee.*name/ }?.text() ?:
   13                       paymentData.Create_new_Ad_hoc_Payee_Name?.text() ?: ''
   14
   15 if (actualPayeeName) {
   16     assert actualPayeeName.toLowerCase().contains(expectedPayee.toLowerCase()) || expectedPayee.toLowerCase().contains(actualPayeeName.toLowerCase()) :
   17         "Payee name mismatch: expected '${expectedPayee}', but Workday returned '${actualPayeeName}'"
   18     log.info "Validated Payee Name: ${actualPayeeName}"
   19 } else {
   20     // When Workday returns only reference IDs for inline ad-hoc payees, require a non-empty Ad_hoc_Payee_ID or WID
   21     def payeeId = payeeNode.ID.find { it.@'wd:type' == 'Ad_hoc_Payee_ID' || it.@'type' == 'Ad_hoc_Payee_ID' }?.text() ?:
   22                   payeeNode.ID.find { it.@'wd:type' == 'WID' || it.@'type' == 'WID' }?.text()
   23     assert payeeId != null && !payeeId.trim().isEmpty() : "Payee_Reference has neither text descriptor nor non-empty Ad_hoc_Payee_ID/WID in Workday
      response"
   24     log.info "Validated Payee Reference integrity (Reference-only Workday response confirms ID: ${payeeId}, not expected payee name '${expectedPayee}')"
   25 }
   26
   27 // 3a. If fixture.payee_wid is provided, always validate it against the returned WID
   28 if (fixture.payee_wid) {
   29     def actWid = payeeNode.ID.find { it.@'wd:type' == 'WID' || it.@'type' == 'WID' }?.text()
   30     assert actWid : "Payee WID missing in Workday response for expected payee_wid '${fixture.payee_wid}'"
   31     assert actWid == fixture.payee_wid : "Payee WID mismatch: expected '${fixture.payee_wid}', but Workday returned '${actWid}'"
   32     log.info "Validated Payee WID matches fixture: ${actWid}"
   33 }

  Key Safeguards Implemented:
   1. Tier 1 (Descriptor Name Match): When Workday returns a text descriptor/name, it asserts a case-insensitive match against fixture.payee_name.
   2. Tier 2 (Strict Reference ID Requirement): When Workday returns only reference IDs, it strictly requires a non-empty Ad_hoc_Payee_ID or WID. It does not fall
      back to generic ID text.
   3. Unconditional payee_wid Verification: If fixture.payee_wid is defined, it is always asserted against the returned WID, regardless of whether a descriptor is
      present.
   4. Transparent Logging: Explicitly logs that reference-only responses validate Workday ID integrity rather than the expected text string.
   5. Preserved Validations: All currency, amount, line item, worktag, memo, status, and attachment assertions remain intact.

  ---

  2. Compilation Results

   - Compiler: ReadyAPI Groovy 4.0.28 FileSystemCompiler
   - Command: java -cp "...lib/*" org.codehaus.groovy.tools.FileSystemCompiler readyapi/Scripts/VerifyAdHocPaymentDetails.groovy
   - Result: 0 syntax or AST errors, Exit Code 0.
   - Line Endings: Unix LF verified clean (git diff --check output is empty).

  ---

  3. Offline Test Suite Execution Results (All 7 Cases)

  Executed against realistic and malformed XML envelopes without making any network calls:

  ┌──────┬──────────────────────────────────────────────────────────────┬─────────────────────────────────────────────┬───────────────────┐
  │ Case │ Test Description                                             │ Expected Behavior                           │ Actual Result     │
  ├──────┼──────────────────────────────────────────────────────────────┼─────────────────────────────────────────────┼───────────────────┤
  │ 1    │ Positive: Descriptor present and matches Acme Services LLC   │ PASS                                        │ 🟢 PASSED         │
  │ 2    │ Positive: Real Workday Sandbox response (Reference-only with │ PASS                                        │ 🟢 PASSED         │
  │      │ Ad_hoc_Payee_ID and WID)                                     │                                             │                   │
  │ 3    │ Positive: fixture.payee_wid matches WID in response          │ PASS                                        │ 🟢 PASSED         │
  │ 4    │ Negative: <wd:Payee_Reference> node missing entirely         │ FAIL (Payee_Reference node missing...)      │ 🟢 PASSED (Caught │
  │      │                                                              │                                             │ expectedly)       │
  │ 5    │ Negative: <wd:Payee_Reference> has no Ad_hoc_Payee_ID or WID │ FAIL (neither text descriptor nor non-empty │ 🟢 PASSED (Caught │
  │      │                                                              │ Ad_hoc_Payee_ID/WID...)                     │ expectedly)       │
  │ 6    │ Negative: Mismatched descriptor (Wrong Vendor Corp)          │ FAIL (Payee name mismatch...)               │ 🟢 PASSED (Caught │
  │      │                                                              │                                             │ expectedly)       │
  │ 7    │ Negative: Mismatched payee_wid (EXPECTED_WID_456 vs          │ FAIL (Payee WID mismatch...)                │ 🟢 PASSED (Caught │
  │      │ DIFFERENT_WID)                                               │                                             │ expectedly)       │
  └──────┴──────────────────────────────────────────────────────────────┴─────────────────────────────────────────────┴───────────────────┘
  ---

  4. Git Diff Summary

   - File Modified: Exactly one file: readyapi/Scripts/VerifyAdHocPaymentDetails.groovy.
   - Working Tree Integrity: All unrelated changes (scripts/run_local_server.py, alembic.local.ini, view_allure_report.bat) remain untouched.
   - Git State: Uncommitted and unstaged. No live E2E tests run, no new transactions created in Workday.

  Awaiting your instruction for the next step.
