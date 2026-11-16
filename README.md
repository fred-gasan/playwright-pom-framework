I reviewed your reconciliation and implementation plan.

Please proceed with implementing the missing validations for UAT 7, 8, 9, and 10 in:

readyapi/Scripts/VerifyAdHocPaymentDetails.groovy

Follow these requirements strictly:

1. Preserve all existing scenarios, fixtures, XML structure, and assertions.
2. Use dynamic expected values from the existing fixture data.
3. Implement the confirmed Workday SOAP response validations for currency, total amount, payee, line items, financial coding, and payment status where applicable.
4. Validate existing attachment count and filenames for UAT 9 and 10.
5. Validate the existing plain-text memo against the corresponding fixture value where the response exposes it.
6. Do not add mandatory rental date regex, rental agreement, claim/order number, or reverse polarity assertions.
7. Follow the existing ReadyAPI Groovy scripting conventions.
8. Do not modify any unrelated files.
9. Do not commit or push any changes.

After implementation:

* Show the exact git diff.
* Explain each new assertion.
* Run available local validation checks.
* Report any failures or remaining blockers.

Do not create a PR yet. I want to review the changes first.