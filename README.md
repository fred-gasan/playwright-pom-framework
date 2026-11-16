Thanks for the comprehensive review. Let’s proceed with the implementation, with three final adjustments:

1. Memo validation:

* If a line memo is expected, fail explicitly when neither Memo nor Item_Description is present in the response.
* If a header memo is expected, fail explicitly when the response memo is missing.
* Preserve the existing run_id XPath assertion.

2. Scenario detection:

* Use the existing framework’s reliable scenario identifiers or fixture mapping to identify UAT 7–10.
* Do not rely only on string matching or the presence of optional fixture properties.
* Ensure mandatory validations cannot silently skip.

3. SOAP response compatibility:

* Verify the XML paths against available real or recorded Workday SOAP response samples.
* Clearly identify any fields that cannot be confirmed without live execution.
* Do not fabricate SOAP response data or report unverified assertions as passed.

After making these minimal corrections:

* Update VerifyAdHocPaymentDetails.groovy.
* Preserve the external script integration in ap-e2e-soapui-project.xml.
* Run Groovy compilation.
* Run the ReadyAPI Smoke suite.
* Run the relevant pytest regression tests.
* Review the final git diff and report all modified files.
* Report any failures or remaining limitations.

Do not run live E2E tests, modify production data, commit, push, or create a PR yet.

Please provide the implementation summary, actual test results, and final git status.