I approve the refined architecture with a phased implementation approach.

Proceed with PHASE 1 — POST AD-HOC PAYMENT WARNINGS IMPLEMENTATION ONLY.

We will implement GET search coverage separately after receiving clarification from Liliana regarding the acceptance criteria.

IMPLEMENTATION REQUIREMENTS:

1. Confirm the active branch is feature/fpoap-182/dev.
2. Extend readyapi/uat-scenarios.csv with an explicit expected_warnings column:
    * UAT 1–6: NA
    * UAT 7: POPULATED:Please Enter Address Details
    * UAT 8: EMPTY
    * UAT 9–10: POPULATED:Please Enter Address Details
    * New UAT 11: POPULATED:Please Enter Address Details
3. Update the ReadyAPI DataSource configuration to recognize the new CSV column. Verify compatibility with existing data-driven execution.
4. Create readyapi/Scripts/VerifyAdHocWarnings.groovy using the existing framework’s external Groovy assertion pattern.
5. The warnings assertion must:
    * Verify the response is a valid JSON object.
    * Verify the warnings property exists.
    * Verify warnings is a JSON array.
    * Verify every warning entry is a string.
    * Strictly validate EMPTY and POPULATED expectations.
    * Verify the expected warning substring for populated scenarios.
    * Fail explicitly for missing, invalid, or unsupported expectation values on Ad-Hoc scenarios.
    * Never silently pass an invalid expectation.
6. Add the warnings assertion to the existing 05-Submit-AdHocPayment step without removing or weakening existing assertions.
7. Create a dedicated UAT 11 fixture based on the existing working Ad-Hoc fixtures:
    * Use an inline payee without an address to trigger the historically observed Workday warning.
    * Include an explicit warning_message.
    * Ensure unique scenario identification.
    * Preserve existing fixture conventions.
    * Verify that warning_message is actually propagated into the outgoing POST request by the existing payload builder.
    * Do not claim that warning_message itself triggers the response warnings.
8. Preserve all existing UAT 1–10 scenarios and FPOAP-165 validations.
9. Update the existing project file smoke checks to include the new script and fixture where appropriate.
10. Verify that Allure reporting correctly captures the new assertion and scenario without exposing sensitive information.

OFFLINE VALIDATION:

* Validate XML structure and CSV/DataSource compatibility.
* Compile the new Groovy script.
* Run offline assertion tests covering:
    * Valid populated warnings.
    * Valid empty warnings.
    * Missing warnings property.
    * Null warnings.
    * Non-array warnings.
    * Non-string warning entries.
    * Missing expected warning substring.
    * Unexpected populated warnings.
    * Unexpected empty warnings.
    * Invalid or missing scenario expectation.
* Run relevant existing offline regression tests.
* Verify that supplier invoice execution remains unaffected.

IMPORTANT CORRECTIONS:

* Re-running UAT 7 and UAT 8 will create new persistent Workday Sandbox payments. They are not zero-transaction tests.
* Historical warnings responses provide evidence for expected behavior but do not guarantee future Sandbox behavior.
* GET search implementation, negative GET tests, and pagination coverage are deferred to Phase 2.
* Do not modify application source code.

SAFETY RULES:

* Preserve all unrelated local modifications and untracked developer files.
* Do not modify credentials or environment configuration.
* Do not execute live API calls or ReadyAPI E2E tests.
* Do not create Workday Sandbox transactions.
* Do not stage, commit, or push.
* Do not modify unrelated files.

After implementation, return:

A. Implementation summary.
B. Exact files created and modified.
C. Git diff summary.
D. Offline validation results.
E. UAT 11 fixture and payload mapping verification.
F. Remaining risks and pending Phase 2 requirements.

Stop after offline validation and wait for approval before live testing or GET implementation.