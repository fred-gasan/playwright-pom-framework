Thanks for the live E2E results.

We confirmed that UAT 7 successfully created an Ad-Hoc Payment in the Workday Sandbox, but the payee validation failed because the SOAP read-back response contains only payee reference IDs, without a descriptor or name.

Before modifying the assertion, please investigate the safest way to validate payee identity.

1. Review VerifyAdHocPaymentDetails.groovy and compare the payee validation approach with VerifyInvoiceDetails.groovy.
2. Inspect the existing UAT 7 fixture and the sanitized request/response structures.
3. Determine whether the returned Ad_hoc_Payee_ID or WID can be reliably matched to the expected payee using an existing response, request field, or documented lookup.
4. Do not simply skip payee validation when the descriptor is missing unless there is another reliable identity check.
5. Recommend the smallest possible correction that preserves meaningful payee validation.
6. Explain whether any additional Workday API lookup would be required.

Do not modify files, execute tests, create new Workday transactions, or change Git state yet.

Please provide the proposed code change and explain why it is safe.