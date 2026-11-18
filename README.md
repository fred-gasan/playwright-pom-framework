 Here are the findings and proposed code change regarding Workday Ad-Hoc Payment status validation.

  ---

  1. Comparison: Supplier Invoices vs. Ad-Hoc Payments

  Invoices (09-Verify-Workday-SOAP & VerifyInvoiceDetails.groovy)
   - API Service: Workday Resource_Management (operation: Get_Supplier_Invoices).
   - Response Structure: The response explicitly returns the document status element:

   1   <wd:Document_Status_Reference>
   2      <wd:ID wd:type="Document_Status_ID">APPROVED</wd:ID>
   3   </wd:Document_Status_Reference>
   - Assertion: Line 565 of ap-e2e-soapui-project.xml checks Document_Status_ID.
   - (Note: VerifyInvoiceDetails.groovy does not validate status at all; it focuses purely on Supplier, Currency, Total Amount, and Line Worktags).

  Ad-Hoc Payments (09b-Verify-AdHocPayment-SOAP & VerifyAdHocPaymentDetails.groovy)
   - API Service: Workday Cash_Management (operation: Get_Ad_hoc_Payments).
   - Response Structure: We inspected all 32 ID elements returned across the live Workday Sandbox SOAP responses for UAT 7, 8, 9, and 10. Workday Cash Management
     does not return any status element in its default read-back envelope.
   - Original Suite Implementation: In commit 772fd3e, Lily Allen originally wrote:

   1   def statusId = xml.'**'.find { it.name() == 'ID' && (it.@'type' =~ /(?i)status/ || it.@'wd:type' =~ /(?i)status/) }?.text() ?: ''
   2   if (statusId) {
   3       assert statusId.equalsIgnoreCase('APPROVED') : "Expected Workday status to be APPROVED, but was '${statusId}'"
   4   }
    The original assertion was conditional (if (statusId)) because Workday does not emit a status element on default read-back.

  ---

  2. Inspection of Payment Submission and Read-Back Payloads

   1. Submission Response (POST /wd-adhoc-payments):

   1    {
   2      "payment_id": "AD_HOC_PAYMENT-6-990713",
   3      "workday_wid": "e13fc11a07d390015b192cb994980000",
   4      "warnings": [ ... ],
   5      "ap_id": "8f449266-...",
   6      "qurrent_workflow_id": null
   7    }
     Contains tracking and reference IDs, but no status string.
   2. Read-Back Response (Get_Ad_hoc_Payments):
     Contains Company_Reference, Bank_Account_Reference, Payee_Reference, Currency_Reference, Payment_Date, Payment_Type_Reference, Memo, Control_Total_Amount,
  Invoice_Line_Replacement_Data, and Attachment_Data. No Status ID exists.

  ---

  3. Availability of "Approved" Evidence Across Test Steps

  ┌──────────────────────────────┬────────────┬──────────────────┬────────────────────────────────────────────────────────────────────────────────────────┐
  │ Step                         │ Location   │ Status Evidence  │ Finding                                                                                │
  │                              │            │ Available?       │                                                                                        │
  ├──────────────────────────────┼────────────┼──────────────────┼────────────────────────────────────────────────────────────────────────────────────────┤
  │ 05-Submit-AdHocPayment       │ REST       │ ❌ No            │ Returns payment_id and workday_wid.                                                    │
  │                              │ Response   │                  │                                                                                        │
  │ 09b-Verify-AdHocPayment-SOAP │ SOAP       │ ❌ No            │ Default response contains financial data, but no status element.                       │
  │                              │ Response   │                  │                                                                                        │
  │ 10-Audit-StatusHistory       │ PostgreSQL │ 🟢 Yes (Pipeline │ Queries processed_emails and verifies that the lifecycle completed: sent to qurrent -> │
  │                              │ DB         │ Status)          │ processed by qurrent -> invoice processed in workday.                                  │
  └──────────────────────────────┴────────────┴──────────────────┴────────────────────────────────────────────────────────────────────────────────────────┘

  ---

  4. Scenario Scope: UAT 7 vs. UAT 8–10

  Per the QE specification (docs/qe-strategy-fpoap-121.md, line 167):
   - UAT 7: Specifically targets "Adhoc payment, AutoComplete Yes — payment Approved".
   - UAT 8: Targets special handling address presence on the payment.
   - UAT 9 & 10: Targets multi-document attachment persistence.

  However, because UAT 7, 8, 9, and 10 all set auto_complete: true in their fixtures, an unconditional assert statusId broke all four scenarios.

  ---

  5. Can Get_Ad_hoc_Payments Expose Status Through Response Filters?

  In Workday Cash Management WSDL:
   - Get_Ad_hoc_Payments_RequestType supports Response_Filter and Response_Group.
   - Unlike Resource_Management, Workday's Cash Management service does not have an Include_Document_Status toggle on Ad_hoc_Payment_Response_GroupType.
   - Ad-Hoc payment approval state in Workday is managed as a Business Process event (Ad_Hoc_Payment_Event), queryable through the Integrations or Human_Resources
     service rather than the basic Cash Management payment lookup.

  ---

  6. Recommended Minimal Correction

  In accordance with your requirement to not treat HTTP 200 as proof of Approved status and to clearly document when status cannot be verified:

   1. If Workday emits a status ID (e.g., if a tenant upgrade or future response group enables it), assert that it matches APPROVED (or fixture.expected_status).
   2. If Workday does not emit a status ID, do not fail the step, but log an explicit warning:
     "Workday Cash Management SOAP response does not expose a Status ID; approval status is NOT verified at the SOAP layer."
   3. Rely on test step 10-Audit-StatusHistory to verify the end-to-end processing disposition in the database.

  ---

  7. Proposed Code Change (readyapi/Scripts/VerifyAdHocPaymentDetails.groovy)

    1 // 6. UAT 7: auto_complete = true reads back as Approved
    2 def isAutoComplete = fixture.auto_complete != null ?
    3     Boolean.valueOf(fixture.auto_complete.toString()) :
    4     Boolean.valueOf(context.expand('${expected_auto_complete}'))
    5
    6 if (isUat7 || isAutoComplete) {
    7     def statusId = xml.'**'.find { it.name() == 'ID' && (it.@'type' =~ /(?i)status/ || it.@'wd:type' =~ /(?i)status/) }?.text() ?: ''
    8     if (statusId) {
    9         def expectedStatus = fixture.expected_status ?: context.expand('${expected_status}') ?: 'Approved'
   10         assert statusId.equalsIgnoreCase(expectedStatus) : "Expected Workday status to be '${expectedStatus}', but was '${statusId}'"
   11         log.info "Validated Workday Status: '${statusId}'"
   12     } else {
   13         // Workday Cash Management Get_Ad_hoc_Payments read-back response does not expose Document Status
   14         // without specialized Response_Filter/Response_Group configuration. Status is NOT verified via SOAP read-back.
   15         log.warn "Workday Cash Management response does not expose Status ID; Workday approval status remains unverified at SOAP layer"
   16     }
   17 }

  ---

  8. Why This Change Is Safe

   1. Empirically Proven: Matches the actual SOAP response received from the Workday Sandbox during live execution.
   2. Preserves Assertion Integrity: If a status is returned, it must be APPROVED; it will fail if Workday returns IN PROGRESS or CANCELLED.
   3. Completely Transparent: Never claims that status was verified when it was absent, and never conflates HTTP 200 with business approval.
   4. Enables 100% Suite Pass: Allows UAT 7, 8, 9, and 10 to complete their verification of Currency, Amount, Payee Reference, Line Items, Accounting Worktags,
      Address, and Multi-Document Attachments.

  No files have been modified. Please confirm if you approve applying this correction.
