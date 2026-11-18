Thanks for the scoped live E2E results.

All four Ad-Hoc Payment scenarios successfully created Workday Sandbox transactions, and the core business validations passed. However, all four tests still failed because the Workday read-back response does not include a status ID.

Before changing the status assertion, please investigate how to validate the expected Approved status reliably.

1. Review the existing status validation in VerifyAdHocPaymentDetails.groovy and compare it with VerifyInvoiceDetails.groovy.
2. Inspect the sanitized Workday SOAP response structure and the existing payment submission response.
3. Check whether Approved status or equivalent completion evidence is available in any existing test step, response field, or Workday API operation.
4. Determine whether the status requirement applies to all UAT 7–10 scenarios or only scenarios with auto_complete=true.
5. Explain whether the current Get_Ad_hoc_Payments response can expose status through a supported response filter or response group.
6. If no reliable status information is available, propose the smallest safe conditional assertion change, clearly documenting that missing status means status was not verified.
7. Do not treat HTTP 200 or successful payment creation as proof of Approved status.
8. Do not modify any files, execute live tests, create new Workday transactions, commit, push, or change branches yet.

Please provide your findings, recommended approach, and exact proposed code change.