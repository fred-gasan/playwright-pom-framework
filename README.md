Thanks for implementing the validations.

Before proceeding with a commit or PR, I want to verify the new assertions against the actual framework behavior and Workday SOAP response structure.

Please perform the following review:

1. Confirm that context.getProperty(‘fixtureData’) returns the expected data structure and that all expected values are correctly populated for UAT 7, 8, 9, and 10.
2. Verify that the XML paths used for Currency, Total Amount, Payee, Invoice Lines, Spend Category, Cost Center, Location, Memo, Status, and Attachments match the actual Workday SOAP response structure.
3. Identify any assertions that could be silently skipped when expected fixture values or actual response fields are missing. Make sure required validations cannot pass without being executed.
4. Review hardcoded fallback values such as USD and Location ID 613. Prefer values sourced from existing fixtures or established project properties.
5. Review monetary comparisons and use BigDecimal where appropriate.
6. Confirm that the external Groovy assertion script is correctly loaded and executed by ReadyAPI, not just included in the project file checks.
7. Check whether the UAT 7–10 end-to-end tests can be executed safely in the available environment. Do not trigger production payments or make changes to production data.

Please report:

* Any issues discovered
* Any required corrections
* Which validations have been verified against real SOAP responses
* Whether UAT 7–10 have actually executed
* Any remaining blockers

Do not modify files yet. Do not commit, push, or create a PR. I want to review your findings before proceeding.