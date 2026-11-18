I approve applying the minimal payee assertion correction to readyapi/Scripts/VerifyAdHocPaymentDetails.groovy, with two additional requirements.

1. When Workday returns a payee descriptor or name, validate it against the expected payee name.
2. When Workday returns only reference IDs, require a non-empty Ad_hoc_Payee_ID or WID.
3. If fixture.payee_wid is provided, always validate it against the returned WID, regardless of whether a descriptor is present.
4. Do not use generic ID text as a fallback.
5. Clearly log that reference-only validation confirms payee reference integrity, not the expected payee name.
6. Preserve all existing currency, amount, financial coding, memo, attachment, and payment status validations.

Please make the smallest possible change.

After editing:

* Compile the Groovy script.
* Run offline positive and negative assertion tests, including missing reference, missing IDs, incorrect descriptor, and mismatched expected WID.
* Review the Git diff to confirm only the intended file changed.
* Do not execute live E2E tests or create new Workday transactions yet.
* Do not modify unrelated local files, Git configuration, or credentials.
* Do not commit or push changes yet.

Report the exact code changes, compilation results, offline test results, and Git diff summary.